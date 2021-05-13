package com.example.grandmothercall.bdDAO;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private static BaseAbonent mBaseAbonent;
    private LiveData<List<Abonent>> mData;


    public ViewModel(@NonNull Application application) {
        super(application);
        mBaseAbonent = BaseAbonent.getInstance(getApplication());
        mData = mBaseAbonent.getAbonentDao().getAllAbonent();
    }


    public void setAbonent(Abonent abonent) {
        new InsertTask().execute(abonent);
    }

    private class InsertTask extends AsyncTask<Abonent, Void, Void>{

        @Override
        protected Void doInBackground(Abonent... abonents) {
            if(abonents != null && abonents.length > 0){
                mBaseAbonent.getAbonentDao().insert(abonents[0]);
            }

            return null;
        }
    }

    public void setAbonentAll(Abonent abonents) {
        new InsertTaskAll().execute(abonents);
    }

    private class InsertTaskAll extends AsyncTask<Abonent, Void, Void>{

        @Override
        protected Void doInBackground(Abonent... abonents) {
            if(abonents != null && abonents.length > 0){
                for(int i = 0; i< abonents.length; i++){
                mBaseAbonent.getAbonentDao().insert(abonents[i]);
                }
            }

            return null;
        }
    }

    public void DeleteAbonent(Abonent abonent){
        new DeleteAbonen().execute(abonent);
    }
    private class DeleteAbonen extends AsyncTask<Abonent, Void, Void>{

        @Override
        protected Void doInBackground(Abonent... abonents) {
            if(abonents != null && abonents.length > 0){
                mBaseAbonent.getAbonentDao().delete(abonents[0]);
            }

            return null;
        }
    }

    public LiveData<List<Abonent>> getData() {
        return mData;
    }
}
