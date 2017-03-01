package home.server;

import home.control.ConnectionControl;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Created by Козак on 22.02.2017.
 * <h1>View MainApp</h1>
 * GUI til {@link home.server.Server}
 * - server
 *
 * @author Козак
 */
public class Server extends ConnectionControl {

    private int port;

    public Server(int port, Consumer<Serializable> ifGotSendBack) {
        super(ifGotSendBack);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
        return true; // ok fordi det er en server
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }

}
