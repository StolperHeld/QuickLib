package com.example.ro_en.quicklib.model;

import android.support.annotation.NonNull;

/**
 * Created by RO_EN on 24.03.2018.
 */

public class ListId {
    public String listId;

    public <T extends ListId> T withId(@NonNull final String id){
        this.listId = id;
        return (T) this;
    }
}
