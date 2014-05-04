package kz.trei.acs.user;


import kz.trei.acs.office.structure.Account1C;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = -8916590958085449790L;
    private long id;
    private String username;
    private String password;
    private String email;
    private RoleType role;
    private Account1C account1C;

    public User() {
        this.id = 1L;
        this.username = "stranger";
        this.password = "123";
        this.email = "user@example.com";
        this.account1C = Account1C.createRandomId();
        this.role = RoleType.UNREGISTERED;
    }

    private User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.role = builder.role;
        this.account1C = builder.tableId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RoleType getRole() { return role;}

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

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

    public Account1C getAccount1C() {
        return account1C;
    }

    public void setAccount1C(Account1C account1C) {
        this.account1C = account1C;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        if (id != user.id) return false;
        if (!account1C.equals(user.account1C)) return false;
        if (!email.equals(user.email)) return false;
        if (!password.equals(user.password)) return false;
        if (role != user.role) return false;
        if (!username.equals(user.username)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + account1C.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "username='" + username + '\'' +
                '}';
    }

    public static class Builder {
        private long id;
        private String username;
        private String password;
        private String email;
        private RoleType role;
        private Account1C tableId;

        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder role(RoleType role) {
            this.role = role;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder tableId(Account1C tableId) {
            this.tableId = tableId;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
