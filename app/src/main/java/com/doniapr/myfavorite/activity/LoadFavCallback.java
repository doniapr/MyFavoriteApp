package com.doniapr.myfavorite.activity;

import android.database.Cursor;

public interface LoadFavCallback {
    void postExecute(Cursor favorite);
}
