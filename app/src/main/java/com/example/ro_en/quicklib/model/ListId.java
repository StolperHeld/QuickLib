package com.example.ro_en.quicklib.model;

import android.support.annotation.NonNull;

/**
 * Extend for List Class to Implement Firebase-List-Id
 */

public class ListId {
    public String listId;

    public <T extends ListId> T withId(@NonNull final String id){
        this.listId = id;
        return (T) this;
    }
}
