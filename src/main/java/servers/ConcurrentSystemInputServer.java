package servers;

import java.net.Socket;

public abstract class ConcurrentSystemInputServer extends AbstractSystemInputServer {

    public ConcurrentSystemInputServer(int port){
        super(port);
    }

    @Override
    protected void processRequest(Socket client) {
        Thread thread = createWorker(client);
        thread.start();
    }

    protected abstract Thread createWorker(Socket client);
}
