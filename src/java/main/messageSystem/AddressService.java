package messageSystem;

public class AddressService {
    private Address accountService1;
    private Address accountService2;
    private int currentService = 0;

    public Address getAccountService() {

        if (currentService == 1) {
            currentService = 2;
            System.out.println("accountService1 is in use");
            return accountService1;
        } else {
            currentService = 1;
            System.out.println("accountService2 is in use");
            return accountService2;
        }
    }

    public void setAccountService(Address accountService) {
        if (currentService == 0) {
            this.accountService1 = accountService;
            currentService++;
        }

        if (currentService == 1) {
            this.accountService2 = accountService;
        }

    }
}
