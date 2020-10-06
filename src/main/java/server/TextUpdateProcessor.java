package server;

import model.TextUpdate;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.ArrayBlockingQueue;

public class TextUpdateProcessor {

    private final int MAX_QUEUE_SIZE  = 100;

    private Hashtable<String, ArrayBlockingQueue<TextUpdate>> clientUpdates;
    private ArrayBlockingQueue<TextUpdate> receivedUpdates;
    private StringBuffer data;
    private Thread processingThread;



    public TextUpdateProcessor(){
        data = new StringBuffer();
        clientUpdates = new Hashtable<>();
        receivedUpdates = new ArrayBlockingQueue<>(MAX_QUEUE_SIZE);
        processingThread = new Thread(new ProcessUpdates());
        processingThread.start();
    }

    public String getData(){
        return this.data.toString();
    }

    public void AddUpdate(TextUpdate update){
        try {
            receivedUpdates.put(update);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Gets the latest update for the specific client
     * If it is a new client then make a queue for this client and return all of the data so that the user gets the
     * latest version of the text
     */
    public TextUpdate getUpdate(String client){
        System.out.println(data.toString());
        if (!clientUpdates.containsKey(client)){
            clientUpdates.put(client, new ArrayBlockingQueue<>(MAX_QUEUE_SIZE));
            System.out.println("added a client");
            return new TextUpdate(1, client, data.toString());
        }

        TextUpdate result = clientUpdates.get(client).poll();
        System.out.println("found data for client: " + result);
        // if no data found return empty data
        if(result == null)
            return new TextUpdate(0, client, "");
        return result;
    }

    class ProcessUpdates implements Runnable {

        @Override
        public void run() {
            while(true) {
                process();
                if (Thread.interrupted()) {
                    return;
                }
            }
        }

        /**
         * update data to latest version and push the updates to the queue for the other clients
         */
        public void process() {

            TextUpdate  update = null;
            try {
                update = receivedUpdates.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("failed to process update");
                return;
            }
            data.replace(0, data.length(), update.getContent());
            addToQueues(update);
        }

        /**
         * add the update to the client queue except for the one that send the update
         */
        private void addToQueues(TextUpdate textUpdate){
            Enumeration<String> clients = clientUpdates.keys();
            clients.asIterator().forEachRemaining(client -> {
                    if(!client.equals(textUpdate.getClientID())){
                        System.out.println("Adding to update queue: " + textUpdate.getContent());
                        clientUpdates.get(client).add(textUpdate);
                    }
                });
        }
    }

}
