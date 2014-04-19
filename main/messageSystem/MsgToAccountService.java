package messageSystem;

import db.AccountService;

public abstract class MsgToAccountService extends Msg {
    public MsgToAccountService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService as);

}
