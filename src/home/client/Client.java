package home.client;

import home.control.ConnectionControl;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Created by Козак on 28.02.2017.
 */
public class Client extends ConnectionControl {

    public Client(Consumer<Serializable> ifGotSendBack) {
        super(ifGotSendBack);
    }

    @Override
    protected boolean isServer() {
        return false;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return 0;
    }
}
