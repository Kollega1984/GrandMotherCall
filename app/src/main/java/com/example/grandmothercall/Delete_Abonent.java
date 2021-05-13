package com.example.grandmothercall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.grandmothercall.bdDAO.Abonent;
import com.example.grandmothercall.bdDAO.ViewModel;

import java.util.List;

public class Delete_Abonent extends AppCompatActivity {

    AdapterDelete mAdapterDelete;
    RecyclerView myRecycler;
    ViewModel VM;
    LiveData<List<Abonent>> myAbonent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete__abonent);
        setTitle(R.string.app_delete);
        myRecycler = findViewById(R.id.recyclerDelete);
        VM = ViewModelProviders.of(this).get(ViewModel.class); // создаем ViewModel
        mAdapterDelete = new AdapterDelete();

        myAbonent = VM.getData(); // получаю лист абонентов из БД.
        myAbonent.observe(this, new Observer<List<Abonent>>() { // устанавливаем наблюдателя
            @Override
            public void onChanged(List<Abonent> abonents) { // если изменится то
                mAdapterDelete.setMyList(abonents);
            }
        });

        ItemTouchHelper mTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                VM.DeleteAbonent(mAdapterDelete.getMyList().get(viewHolder.getAdapterPosition()));
            }
        });
        mAdapterDelete.setDeleteAbonent(new AdapterDelete.deleteAbonent() {
            @Override
            public void onClickDelete(int pos) {
                VM.DeleteAbonent(mAdapterDelete.getMyList().get(pos));
            }
        });
        mTouchHelper.attachToRecyclerView(myRecycler);
        myRecycler.setAdapter(mAdapterDelete);
        myRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}
