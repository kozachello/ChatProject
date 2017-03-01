package home.client;

import home.control.ConnectionControl;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Created by Козак on 22.02.2017.
 * <h1>View MainApp</h1>
 * GUI til {@link home.client.Client}
 * - client
 *
 * @author Козак
 */
public class Client extends ConnectionControl {

    private String ipadress;
    private int port;

    public Client(String ipadress, int port, Consumer<Serializable> ifGotSendBack) {
        super(ifGotSendBack);
        this.port = port;
        this.ipadress = ipadress;
    }

    @Override
    protected boolean isServer() {
        return false; // det er en klient
    }

    @Override
    protected String getIP() {
        return ipadress;
    }

    @Override
    protected int getPort() {
        return port;
    }
}
