package home.control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Created by Козак on 22.02.2017.
 */
public abstract class ConnectionControl  {

    private Consumer<Serializable> ifGotSendBack; // handling over objekt af type Serializable (object==>byte)
    private ConnectionThread connThread = new ConnectionThread();

    public ConnectionControl(Consumer<Serializable> ifGotSendBack) {
        this.ifGotSendBack = ifGotSendBack;
        connThread.setDaemon(true);
    }

    public void startConnection() throws Exception {
        connThread.start();
    }

    public void send(Serializable data) throws Exception {
        connThread.out.writeObject(data);
    }

    public void closeConnection() throws Exception {
        connThread.socket.close();
    }

    protected abstract boolean isServer(); // metoder behøver ik krop
    protected abstract String getIP();
    protected abstract int getPort();

    class ConnectionThread extends Thread {

        private Socket socket;
        private ObjectInputStream in; // tråd ind
        private ObjectOutputStream out; // tråd ud

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
                ifGotSendBack.accept("connection is closed!");
            }
        }

    }

}
