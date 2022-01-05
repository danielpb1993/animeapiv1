package com.example.anime.domain.dto;

import com.example.anime.domain.model.User;

public class UserResult {
    public String userid;
    public String username;

    public UserResult(User user) {
        this.userid = user.userid.toString();
        this.username = user.username;
    }


    public static UserResult user(User user) {
        return new UserResult(user);
    }
}
