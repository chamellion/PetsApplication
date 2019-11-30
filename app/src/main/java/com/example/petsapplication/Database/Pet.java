package com.example.petsapplication.Database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pets")
public class Pet  {

    @ColumnInfo(name = "Name")
    private String mName;

    @ColumnInfo(name = "Weight")
    private int mWeight;

    @ColumnInfo(name = "Gender")
    private String mGender;

    @ColumnInfo(name = "Breed")
    private String mBreed;

    @PrimaryKey(autoGenerate = true)
    private int Id;

    @Ignore
    public Pet(String mName, int mWeight, String mGender, String mBreed) {
        this.mName = mName;
        this.mWeight = mWeight;
        this.mGender = mGender;
        this.mBreed = mBreed;
    }

    public Pet(int Id, String mName, int mWeight, String mGender, String mBreed) {
        this.Id = Id;
        this.mName = mName;
        this.mWeight = mWeight;
        this.mGender = mGender;
        this.mBreed = mBreed;
    }


    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getWeight() {
        return mWeight;
    }

    public void setWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getBreed() {
        return mBreed;
    }

    public void setBreed(String mBreed) {
        this.mBreed = mBreed;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }


}
