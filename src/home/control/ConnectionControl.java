package home.control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Created by Козак on 22.02.2017.
 *
 * <h1>View MainApp</h1>
 * GUI til {@link home.control.ConnectionControl}
 * - kontrol af forbindelser
 *
 * @author Козак
 */
public abstract class ConnectionControl  {

    private Consumer<Serializable> ifGotSendBack; // handling over objekt af type Serializable (object==>byte)
    private ConnectionThread connThread = new ConnectionThread();

    // konstruktor...
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
    protected abstract String getIP(); // adgang for subklasser (@Override)
    protected abstract int getPort();
    protected abstract String getUser(); // måske unødvendigt...

    class ConnectionThread extends Thread {

        private Socket socket;
        private ObjectInputStream in; // tråd ind
        private ObjectOutputStream out; // tråd ud

        @Override
        public void run() { // baggrunds tråd
            try (ServerSocket server = isServer() ? new ServerSocket(getPort()) : null; // try med resourcer (AutoClosable)
                Socket socket = isServer() ? server.accept() : new Socket(getIP(), getPort()); // kontrolere om det er
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); // server eller ej
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                    this.out = out;
                    this.socket = socket;
                    socket.setTcpNoDelay(true); // slå Nagle fra)

                while (true) {
                    Serializable data = (Serializable) in.readObject(); // læser data
                    ifGotSendBack.accept(data); // action efter læsning
                }

            } catch (Exception e) {
                ifGotSendBack.accept("connection is closed!"); // hvis der er ikke forbindelse
            }
        }

    }

}
