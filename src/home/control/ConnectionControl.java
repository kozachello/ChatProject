package home.control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Created by Козак on 28.02.2017.
 */
public abstract class ConnectionControl  {

    private Consumer<Serializable> ifGotSendBack; // handling over objekt af type Serializable (object==>byte)

    public ConnectionControl(Consumer<Serializable> ifGotSendBack) {
        this.ifGotSendBack = ifGotSendBack;
    }

    public void startConnection() throws Exception {

    }

    public void send(Serializable data) throws Exception {

    }

    public void closeConnection() throws Exception {

    }

    abstract boolean isServer(); // behøver ik krop
    abstract String getIP();
    abstract int getPort();

    class ConnectionThread implements Runnable {

        private Socket socket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        @Override
        public void run() {
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null;
            Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) { // try med resourcer (AutoClosable)
                this.out = out;
                this.socket = socket;
                socket.setTcpNoDelay(true);

                while (true) {
                    Serializable data = (Serializable) in.readObject();
                    ifGotSendBack.accept(data);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
