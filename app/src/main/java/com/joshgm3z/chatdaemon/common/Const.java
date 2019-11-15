package com.joshgm3z.chatdaemon.common;

import androidx.annotation.IntDef;

public class Const {

    @IntDef({ChatType.SENT, ChatType.RECEIVED})
    public @interface ChatType {
        int SENT = 1;
        int RECEIVED = 2;
    }

}
