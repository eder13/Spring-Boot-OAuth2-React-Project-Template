package com.springreact.template.security;

import java.util.Date;

/// TODO: Create any other OAuth2 Provider like here (see returned JSON for attributes -
///       can get that JSON in successHandler with user.getAttributes()) -
///       example Github
public class GithubUser {

    private String login;
    private int id;
    private String node_id;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private boolean site_admin;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String twitter_username;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;
    private Date created_at;
    private Date updated_at;
    private int private_gists;
    private int total_private_repos;
    private int owned_private_repos;
    private int disk_usage;
    private int collaborators;
    private boolean two_factor_authentication;

    private static class plan {
        private String name;
        private String space;
        private int collaborators;
        private int private_repos;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}


