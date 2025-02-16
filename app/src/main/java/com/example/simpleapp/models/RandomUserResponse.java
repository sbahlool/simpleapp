package com.example.simpleapp.models;

import java.util.List;

public class RandomUserResponse {
    private List<User> results;

    public List<User> getResults() {
        return results;
    }

    public static class User {
        private String email;
        private Login login;

        public String getEmail() {
            return email;
        }

        public Login getLogin() {
            return login;
        }

        public static class Login {
            private String password;

            public String getPassword() {
                return password;
            }
        }
    }
}
