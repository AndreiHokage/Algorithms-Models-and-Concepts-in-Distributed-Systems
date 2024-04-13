package servers.threadsServer;

import model.DistributedSystem;
import servers.SystemInputServer;

public class SystemInputServerThread extends Thread {

    private SystemInputServer systemInputServer = null;

    public SystemInputServerThread(SystemInputServer systemInputServer){
        this.systemInputServer = systemInputServer;
    }

    @Override
    public void run() {
        this.systemInputServer.start();
    }
}
