package com.example.petsapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Pet.class}, version = 1, exportSchema = false)
public abstract class PetsDatabase extends RoomDatabase  {

    private static final String TAG = "PetsDatabase";
   private static final Object LOCK = new Object();
   private static final String DATABASE_NAME= "PetsDatabase";
   private static PetsDatabase sInstance;

   public static PetsDatabase getInstance(Context context){
       if (sInstance == null){
           synchronized (LOCK){
               Log.d(TAG, "getInstance: Creating a new instance of the database");
               sInstance = Room.databaseBuilder(context.getApplicationContext(), PetsDatabase.class, PetsDatabase.DATABASE_NAME)
                       .build();
           }
       }
       Log.d(TAG, "getInstance: getting database");
       return sInstance;
   }
        public abstract PetsDAO petsDAO();
}
