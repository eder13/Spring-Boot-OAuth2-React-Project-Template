package com.springreact.template.db;

import com.sun.istack.NotNull;

import javax.persistence.*;

/* Local Database */
@Entity
@Table(name = "users")
public class Users {

    /* Model of the MySQL Table 'users'*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int userID;
    private String name;
    private String email;
    @Column(columnDefinition = "boolean default false")
    private Boolean newsletter;

    public Users() {}

    public Users(String name, String email, boolean newsletter) {
        this.name = name;
        this.email = email;
        this.newsletter = newsletter;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }
}
