package com.example.petsapplication.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PetsDAO {

    @Query("SELECT * FROM Pets ORDER BY Id")
    LiveData<List<Pet>> loadAllPets();

    @Insert
    void addNewPet(Pet pet);

    @Delete
    void deletePet(Pet pet);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePet(Pet pet);
}
