package com.example.riamon_v.java_epicture_2017.DatabaseManagment;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * User object in database
 */
@Entity (tableName = "users")
public class User {

        @PrimaryKey (autoGenerate = true)
        private int id;

        private String name;
        private String login;
        private String password;
        private String tokenFlickr = null;
        private String tokenImgur = null;
        private boolean connect = false;

        public User(String name, String login, String password) {
                this.name = name;
                this.login = login;
                this.password = password;

        }

        public void setUserDiconnect() {
                connect = false;
                tokenFlickr = null;
                tokenImgur = null;
        }

        public int getId() {
                return id;
        }

        public String getLogin() {
                return login;
        }

        public void setLogin(String login) {
                this.login = login;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getTokenFlickr() {
                return tokenFlickr;
        }

        public void setTokenFlickr(String tokenFlickr) {
                this.tokenFlickr = tokenFlickr;
        }

        public String getTokenImgur() {
                return tokenImgur;
        }

        public void setTokenImgur(String tokenImgur) {
                this.tokenImgur = tokenImgur;
        }

        public boolean isConnect() {
                return connect;
        }

        public void setConnect(boolean connect) {
                this.connect = connect;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }
}
