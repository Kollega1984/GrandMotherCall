package com.example.grandmothercall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import androidx.lifecycle.ViewModelProviders;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.grandmothercall.bdDAO.Abonent;
import com.example.grandmothercall.bdDAO.BaseAbonent;
import com.example.grandmothercall.bdDAO.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    AdapterOut myAdapter;
    RecyclerView out;
    LiveData<List<Abonent>> myAbonent;
    public ViewModel VM;
   // public boolean canCopy;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymeniy, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_abonent_phone:
                startActivity(new Intent(this, Delete_Abonent.class));
            break;
            case R.id.item_copy_phone:
                     getContact();
                break;
                     default: break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAdapter = new AdapterOut();
        VM = ViewModelProviders.of(this).get(ViewModel.class); // создаем ViewModel

            myAbonent = VM.getData(); // получаю лист абонентов из БД.

        myAbonent.observe(this, new Observer<List<Abonent>>() { // устанавливаем наблюдателя
            @Override
            public void onChanged(List<Abonent> abonents) { // если изменится то
                myAdapter.setMyList(abonents);
            }
        });


        myAdapter.setClickList(new AdapterOut.onClickList() {
            @Override
            public void onClick(Intent call) {
                getCall(call);

            }
        });
        myAdapter.setOnClickImage(new AdapterOut.onClickImage() {
            @Override
            public void onClick(int position) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, position);
            }

        });

        out = findViewById(R.id.recycler);
        out.setLayoutManager(new LinearLayoutManager(this));
        out.setAdapter(myAdapter);


    }


    public void getCall(Intent call){
        if(checkSelfPermission(Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            // запрашиваем разрешение
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2);
        } else {
            startActivity(call);
        }
    }
    public void getContact(){
        //проверяем разрешение работы с read_contacts
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            // запрашиваем разрешение
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            new GetContact().execute();
        }


    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
        if(requestCode == 2){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    public void AddNewAbonent(View view) {
        startActivity(new Intent(this, Add_new_abonent.class));
    }


    // получаем контакты из телефонной книги и добавляем их в базу данных d в отдельном потоке
    public class GetContact extends AsyncTask<Void, Void, Void>{
     @Override
      protected Void doInBackground(Void... voids) {

          String last = "aa";
           Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                  , null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

           while (cursor.moveToNext()){

                 if(last != null && !last.equalsIgnoreCase(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)))) {

                // int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                last = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                VM.setAbonent(new Abonent(photo, name, phone));
                   }
              }
            cursor.close();

           return null;
      }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
                if(resultCode==RESULT_OK){
                    myAdapter.someChange(data.getData().toString(), requestCode);
                }
                else{

                    Toast.makeText(this, "bad", Toast.LENGTH_SHORT).show();
                }


    }



}
