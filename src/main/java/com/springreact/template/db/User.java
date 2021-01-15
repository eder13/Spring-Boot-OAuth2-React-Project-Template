package com.springreact.template.db;

import com.sun.istack.NotNull;

import javax.persistence.*;

/* Local Database */
@Entity
@Table(name = "user")
public class User {

    /* Model of the MySQL Table 'users'*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    private String name;
    private String email;
    @Column(columnDefinition = "boolean default false")
    private Boolean newsletter;

    public User() {
    }

    public User(String name, String email, boolean newsletter) {
        this.name = name;
        this.email = email;
        this.newsletter = newsletter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
