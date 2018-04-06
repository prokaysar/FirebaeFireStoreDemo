package com.prokaysar.firebaefirestoredemo;

import android.support.annotation.NonNull;

public class PostId {
    public String postId;
    public <T extends PostId> T withId(@NonNull final String id){
        this.postId = id;
        return (T)this;
    }
}
