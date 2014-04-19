package db;

public class UserDataSet {
    private Long id;
    private String username;
    private String password;

    public UserDataSet(Long id, String username, String password) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public UserDataSet(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public Long getId() {
        return id;
    }
}