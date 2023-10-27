package com.youtube.jwt.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;

    @ManyToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)//many users can have many different roles;
    @JoinTable(name="USER_ROLE",
        joinColumns = {
            @JoinColumn(name="USER_ID")
        },
            inverseJoinColumns = {
            @JoinColumn(name="ROLE_ID")
            }
    )   // 3RD table user_role table that contain user_id and role_id
    private Set<Role> role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
