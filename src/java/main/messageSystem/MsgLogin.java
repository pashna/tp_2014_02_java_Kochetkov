package messageSystem;

import db.AccountService;
import exception.AccountServiceException;
import exception.EmptyDataException;
import exception.NoUserIdException;

import java.sql.SQLException;

/**
 * Created by p.Kochetkov on 20.04.14.
 */
public class MsgLogin extends MsgToAccountService{

    private final String username;
    private final String pass;
    private final String sessionId;

    public MsgLogin(Address from, Address to, String username, String pass, String ssid) {
        super(from, to);
        this.username = username;
        this.pass = pass;
        this.sessionId = ssid;
    }

    @Override
    void exec(AccountService as) {
        try {
            as.logUser(username, pass);
            as.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), sessionId, as.getUserId(username)));
        } catch (SQLException | AccountServiceException | EmptyDataException | NoUserIdException e) {
            as.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), sessionId, (long)-1));
        }
    }
}
