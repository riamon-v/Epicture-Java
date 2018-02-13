package com.example.riamon_v.java_epicture_2017.DatabaseManagment;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface UsersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    List<User> getUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM users WHERE login = :login")
    User getUserByLogin(String login);

    @Query("SELECT * FROM users WHERE connect = :connect")
    User getConnectedUser(boolean connect);

    @Query("SELECT * FROM users WHERE tokenFlickr IS NOT NULL LIMIT 1")
    User getConnectedUserFlick();

    @Query("SELECT * FROM users WHERE tokenImgur IS NOT NULL LIMIT 1")
    User getConnectedUserImgur();
    /*@Query("SELECT * FROM User WHERE id = :id")
    User getTaskById(int id);*/
}
