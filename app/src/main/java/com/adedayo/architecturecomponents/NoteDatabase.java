package com.adedayo.architecturecomponents;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract  class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao mNoteDao ();

    public static synchronized  NoteDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;

        
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };

    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao mNoteDao;

        private populateDbAsyncTask(NoteDatabase db){
            mNoteDao = db.mNoteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.insert(new Note("Title 1", "Description 1", 1));
            mNoteDao.insert(new Note("Title 2", "Description 2", 2));
            mNoteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }



}
