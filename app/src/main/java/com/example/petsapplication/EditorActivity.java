package com.example.petsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.petsapplication.Database.Pet;
import com.example.petsapplication.Database.PetsDatabase;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = "EditorActivity";
    private EditText mNameButton, mBreedButton, mWeight;
    private Spinner spinner;
    private PetsDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNameButton = findViewById(R.id.edit_pet_name);

        mBreedButton = findViewById(R.id.edit_pet_breed);

        mWeight = findViewById(R.id.edit_pet_weight);

        mDb = PetsDatabase.getInstance(getApplicationContext());
        setUpSpinner();
    }

    private void setUpSpinner() {
        Log.d(TAG, "setUpSpinner: setting up spinner");
        spinner = findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.array_gender_options, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID){
            case R.id.save_pet:
            saveNewPet();
            break;
            case R.id.delete_pet:
                deletePet();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_menu, menu);
        return true;
    }

    private void saveNewPet() {
        String petName = mNameButton.getText().toString().trim();
        String petBreed = mBreedButton.getText().toString().trim();
        String petWeight = mWeight.getText().toString().trim();
        String petGender = spinner.getSelectedItem().toString().trim();
        if (petName.isEmpty() || petBreed.isEmpty() || petWeight.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        int weight = Integer.parseInt(petWeight);
       final Pet pet = new Pet(petName, weight, petGender, petBreed);
        PetExecutor.getInstance().DiskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: ID of saved pet is " + pet.getId());
                mDb.petsDAO().addNewPet(pet);
            }
        });

        Toast.makeText(this, "Pet saved", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void deletePet(){
        mNameButton.setText("");
        mWeight.setText("");
        mBreedButton.setText("");
        spinner.setSelection(0);
    }
}
