package Util;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkingUtils {

    /**
     * finds a random free port on the given network interface
     */
    public static int findFreePort(String networkInterface) {

        int port = 0;

        try {
            ServerSocket serverSocket = new ServerSocket(0, 0, InetAddress.getByName(networkInterface));
            port = serverSocket.getLocalPort();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return port;
    }

    /**
     * find the available wireless interfaces and their host address in ip4 format
     */
    public static List<String> getPossibleAddresses() {

        //  regex with interfaces that are definitely not wireless interfaces
        Pattern p = Pattern.compile("^(veth|br|docker|lo|EHC|XHC|bridge|gif|stf|p2p|awdl|utun|tun|tun0|tap)");

        List<String> validAddresses = new ArrayList<>();

        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();

            while (netInterfaces.hasMoreElements()) {
                NetworkInterface net = netInterfaces.nextElement();
                Matcher m = p.matcher((net.getName()));

                // if interface does not match an invalid interface check its address
                if (!m.matches()) {
                    // check if the interface is up
                    if (!net.isUp())
                        continue;
                    // get the network addresses from the interface
                    Enumeration<InetAddress> a = net.getInetAddresses();
                    // retrieve the ip4 address
                    String ip4 = findIp4Address(a);
                    if (ip4 != null)
                        validAddresses.add(ip4);
                }
            }

        } catch (SocketException e) {
            System.out.println("error trying to read network interfaces");
            System.exit(1);
        }

        if (validAddresses.size() == 0) {
            System.out.println("no valid interfaces found!");
            System.exit(0);
        }

        return validAddresses;
    }

    /**
     * finds the ip4 formatted address of the interface
     */
    public static String findIp4Address(Enumeration<InetAddress> addresses) {

        Pattern p = Pattern.compile("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}");

        while (addresses.hasMoreElements()) {

            String hostAddress = addresses.nextElement().getHostAddress();
            Matcher m = p.matcher(hostAddress);

            if (m.matches())
                return hostAddress;
        }

        return null;
    }

    /**
     * convert a query string to a key value map for easy access to the parameters
     */
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if(query == null)
            return result;
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }


}
