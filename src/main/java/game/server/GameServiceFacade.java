package game.server;

import game.thrift.UserStorage;
import org.apache.thrift.TException;

/**
 * Created by yangzhao on 3/31/2016.
 */
public class GameServiceFacade implements UserStorage.Iface {

    public long generateSessionId() throws TException {
        System.out.println("fffff");
        return 1;
    }
}
