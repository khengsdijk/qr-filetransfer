package server;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortScanner {


    public String findAvailableAddress() throws SocketException {

        List<String> addresses = getPossibleAddresses();

        return validateAddresses(addresses);
    }

    public int findFreePort(String netWorkAddress){
        //TODO implement this shit
        return 1;
    }

    /**
     * takes a list of addresses an returns the first one that works
     * if no suitable address is found throw an error exit the program
     */
    public String validateAddresses(List<String> addresses){


        return "a";
    }


    public List<String> getPossibleAddresses() throws SocketException {

        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

        HashMap<String, String> interFaceAddresses = new HashMap<>();

        Pattern p = Pattern.compile("^(veth|br|docker|lo|EHC|XHC|bridge|gif|stf|p2p|awdl|utun|tun|tap)");

        for (NetworkInterface netIf : Collections.list(nets)) {
            Matcher m = p.matcher((netIf.getName()));
            if(!m.matches())
                netIf.getInetAddresses();
        }

            return List.of("exampleAddress");
    }


    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {

        System.out.printf("Name: %s\n", netint.getName());

        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

        for (InetAddress inetAddress : Collections.list(inetAddresses)) {

            System.out.printf("InetAddress: %s\n", inetAddress);
        }
        System.out.printf("\n");
    }


}
