package com.hust.datn.service.dto;

import java.util.List;

public class UserSearchDTO {
    private String authorities;
    private String keyword;

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
