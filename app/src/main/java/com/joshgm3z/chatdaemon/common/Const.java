package com.joshgm3z.chatdaemon.common;

import android.support.annotation.IntDef;

public class Const {

    @IntDef({ChatType.SENT, ChatType.RECEIVED})
    public @interface ChatType {
        int SENT = 1;
        int RECEIVED = 2;
    }

}
