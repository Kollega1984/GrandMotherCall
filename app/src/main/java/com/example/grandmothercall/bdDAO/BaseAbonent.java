package com.example.grandmothercall.bdDAO;


import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Abonent.class}, version = 1, exportSchema = false)
public abstract class BaseAbonent extends RoomDatabase {

    final private static Object syxr = new Object();
    final private static String NAME = "Phone.db";

    private static BaseAbonent mBaseAbonent;

    public static BaseAbonent getInstance(Context context){
        synchronized (syxr){
            if(mBaseAbonent == null){
                mBaseAbonent = Room.databaseBuilder(context, BaseAbonent.class, NAME).fallbackToDestructiveMigration().build();
            }
        }
        return mBaseAbonent;
    }

    public abstract AbonDAO getAbonentDao();
}
