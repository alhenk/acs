package kaz.trei.acs.user;


public class User {
    private static final long serialVersionUID = -8916590958085449790L;
    private int id;
    private String username;
    private String password;
    private String tableId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(){
        this.id = 1;

        this.username = "admin";
        this.password = "123";
        this.tableId = "KK0000001";
    }

    public User(String username, String password, String tableId) {
        this.username = username;
        this.password = password;
        this.tableId = tableId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!password.equals(user.password)) return false;
        if (!tableId.equals(user.tableId)) return false;
        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + tableId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}