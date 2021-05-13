package com.example.grandmothercall.bdDAO;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "abon")
public class Abonent {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String image;
    private String name;
    private String phone;

    public Abonent(int id, String image, String name, String phone) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.phone = phone;
    }
    @Ignore
    public Abonent(String image, String name, String phone) {

        this.image = image;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
