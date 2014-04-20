package messageSystem;

import db.AccountService;
import db.UserDAO;
import exception.AccountServiceException;
import exception.EmptyDataException;
import exception.NoUserIdException;
import frontend.UserSession;

import java.sql.SQLException;

public class MsgRegister extends MsgToAccountService {
    private final String username;
    private final String pass;
    private final String sessionId;

    public MsgRegister(Address from, Address to, String username, String pass, String ssid) {
        super(from, to);
        this.username = username;
        this.pass = pass;
        this.sessionId = ssid;
    }

    @Override
    void exec(AccountService as) {
        try {
            as.regUser(username, pass);
            as.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), sessionId, as.getUserId(username)));
        } catch (SQLException| AccountServiceException| EmptyDataException| NoUserIdException e) {
            as.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), sessionId, (long)-1));
        }
    }
}
