package home.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.NoConnectionPendingException;

/**
 * Created by Козак on 13.02.2017.
 */
public class ServSocket {

    private static final int PORT = 19000;
    private static ClientSocket client;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Started! Waiting for connection");
            Socket socket = serverSocket.accept();
            boolean connected = socket.isConnected();
                System.out.println("Accepted! " + socket.getInetAddress());
                try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
                    DataInputStream din = new DataInputStream(in);
                    DataOutputStream dout = new DataOutputStream(out);
                    //while(true) {
                        byte[] buf = new byte[8 * 1024];
                        int readBytes = din.read(buf);
                        //String line = new String(buf, 0, readBytes);
                        //System.out.printf("%s", line);
                        //dout.write(line.getBytes());
                        dout.flush();
                        //serverSocket.accept();
                        //System.out.println("\nwaiting for the next line...");
                    //}
                } catch (NoConnectionPendingException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
            /*finally {
                serverSocket.close();
            }*/
    }

}
