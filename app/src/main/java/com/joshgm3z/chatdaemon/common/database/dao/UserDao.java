package com.joshgm3z.chatdaemon.common.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.joshgm3z.chatdaemon.common.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void addUser(User user);

    @Query("SELECT * FROM User WHERE id = :id")
    User getUser(String id);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

}
