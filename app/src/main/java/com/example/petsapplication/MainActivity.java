package com.example.petsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.petsapplication.Database.Pet;
import com.example.petsapplication.Database.PetsDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PetsRecyclerViewAdapter.OnListItemClicked {

   private RecyclerView recyclerView;

   private PetsRecyclerViewAdapter mAdapter;

   private PetsDatabase mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new PetsRecyclerViewAdapter(getApplicationContext(), this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mdb = PetsDatabase.getInstance(getApplicationContext());

        retrievePets();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                PetExecutor.getInstance().DiskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Pet> pets = mAdapter.getPetList();
                        mdb.petsDAO().deletePet(pets.get(position));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);

    }
    private void retrievePets(){
       LiveData<List<Pet>> mPets =  mdb.petsDAO().loadAllPets();

       mPets.observe(this, new Observer<List<Pet>>() {
           @Override
           public void onChanged(List<Pet> pets) {
               mAdapter.setPetList(pets);
           }
       });

    }

    @Override
    public void OnClickedItem(int clickedItem) {

    }
}
