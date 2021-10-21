package com.joshgm3z.chatdaemon.common;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

public class Const {

    @IntDef({ChatType.SENT, ChatType.RECEIVED})
    public @interface ChatType {
        int SENT = 1;
        int RECEIVED = 2;
    }

    @StringDef({DbCollections.CHATS, DbCollections.USERS})
    public @interface DbCollections {
        String USERS = "users";
        String CHATS = "chats";
    }

    public @interface DbFields {
        String ID = "id";
        interface Chat {
            String DATE_TIME = "datetime";
            String MESSAGE = "message";
            String FROM_USER = "fromuser";
            String TO_USER = "touser";
            String STATUS = "status";
        }
        interface User {
            String USERNAME = "username";
            String PASSWORD = "passsword";
        }
    }

}
