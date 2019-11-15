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

    @StringDef({DbFields.NAME, DbFields.PHONE_NUMBER})
    public @interface DbFields {
        String NAME = "name";
        String PHONE_NUMBER = "phonenumber";
    }

}
