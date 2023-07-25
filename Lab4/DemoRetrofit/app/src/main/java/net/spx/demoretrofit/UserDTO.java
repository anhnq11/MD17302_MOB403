package net.spx.demoretrofit;

public class UserDTO {
    String id;
    String username;
    String passwd;
    String email;

    public UserDTO() {
    }

    public UserDTO(String username, String passwd, String email) {
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }

    public String toString(){
        return  id + " - " + username + "\n" + email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
