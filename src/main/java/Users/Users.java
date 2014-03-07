package Users;

import java.util.HashMap;
import java.util.Map;

public class Users {

    private Map<String, String> usersBase = new HashMap<>();

    public Users () {
        usersBase.put("pasha","123");
        usersBase.put("vitaly", "SkyForge");
    }

    public boolean verifyUser(String login, String pass) {
        if (usersBase.containsKey(login) && usersBase.get(login).equals(pass)){
            return true;
        }
        return false;
    }
}
