package com.example.grandmothercall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.grandmothercall.bdDAO.Abonent;
import com.example.grandmothercall.bdDAO.ViewModel;

public class Add_new_abonent extends AppCompatActivity {

    ImageView Image;
    EditText Name;
    EditText phone;
    ViewModel VM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_abonent);
        setTitle(R.string.app_add);
        Image = findViewById(R.id.imageView2);
        Name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        VM = ViewModelProviders.of(this).get(ViewModel .class);

    }

    public void AddNewAbonent(View view) {
        VM.setAbonent(new Abonent(null, Name.getText().toString(), phone.getText().toString()));
        startActivity(new Intent(this, MainActivity.class));
    }
}
