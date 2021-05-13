package com.example.grandmothercall.bdDAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AbonDAO {

    //Добавляем в БД abonenta
    @Insert
    void insert(Abonent abonent);

    @Insert
    void insertALL(List<Abonent> abonents);

    //Удаляем Абонента
    @Delete
    void delete(Abonent abonent);

    // Получение всех Абонентов из бд
    @Query("SELECT * FROM abon")
    LiveData<List<Abonent>> getAllAbonent();

}
