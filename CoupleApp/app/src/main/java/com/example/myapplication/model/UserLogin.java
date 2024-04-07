package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class UserLogin {
        @SerializedName("id")
        private int id;
        @SerializedName("username")
        private String username;
        @SerializedName("token")
        private String token;
        @SerializedName("alias")
        private String alias;
        @SerializedName("urlAvatar")
        private String avatar;
        @SerializedName("fullName")
        private String fullName;
        // Constructor
        public UserLogin(int id, String username, String token, String alias, String avatar, String fullName) {
                this.id = id;
                this.username = username;
                this.token = token;
                this.alias = alias;
                this.avatar = avatar;
                this.fullName = fullName;
        }
        // Getters
        public int getId() {
                return id;
        }
        public String getUsername() {
                return username;
        }
        public String getToken() {
                return token;
        }
        public String getAlias() {
                return alias;
        }
        public String getAvatar() {
                return avatar;
        }
        public String getFullName() {
                return fullName;
        }
        // Setters
        public void setId(int id) {
                this.id = id;
        }
        public void setUsername(String username) {
                this.username = username;
        }
        public void setToken(String token) {
                this.token = token;
        }
        public void setAlias(String alias) {
                this.alias = alias;
        }
        public void setAvatar(String avatar) {
                this.avatar = avatar;
        }
        public void setFullName(String fullName) {
                this.fullName = fullName;
        }
}
