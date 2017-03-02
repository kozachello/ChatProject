package home.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by Козак on 13.02.2017.
 */
public class ClientSocket {

    private static final int PORT = 19000;
    private static final String HOST = "localhost";

    public static void main() throws IOException {

        Socket socket = null;

            try {
                socket = new Socket(HOST, PORT);
                try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
                    DataInputStream din = new DataInputStream(in);
                    DataOutputStream dout = new DataOutputStream(out);
                    //dout.write(line.getBytes());
                    dout.flush();
                    byte[] data = new byte[8 * 1024];
                    int readBytes = din.read(data);
                    //System.out.printf("Server ==> %s", new String(data, 0, readBytes));

                }
            } catch (IOException e) {
                e.printStackTrace();
            } // finally ...
        }
    }

