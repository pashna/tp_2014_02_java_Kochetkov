package frontend;

import messageSystem.Address;
import messageSystem.AddressService;

public class UserSession {
    private Address accountService;

    private String name;
    private String sessionId;
    private String action;
    private Long userId;

    public UserSession(String sessionId, String name, AddressService addressService, String action) {
        this.sessionId = sessionId;
        this.name = name;
        this.accountService = addressService.getAccountService();
        this.action = action;
    }

    public Address getAccountService() {
        return accountService;
    }

    public String getName(){
        return name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }
}
