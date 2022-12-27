package com.example.takvimproje;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import android.sax.RootElement;
@Database(entities = {Not.class},version = 1)
public abstract class veritabanı extends RoomDatabase {
    public abstract dao dao();
}
