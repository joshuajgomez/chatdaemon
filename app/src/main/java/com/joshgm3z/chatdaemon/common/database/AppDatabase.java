package com.joshgm3z.chatdaemon.common.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.joshgm3z.chatdaemon.common.database.dao.UserDao;
import com.joshgm3z.chatdaemon.common.database.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static String DB_NAME = "chat-app-database";

    public static AppDatabase getInstance(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
        return db;
    }

    public abstract UserDao mUserDao();
}
