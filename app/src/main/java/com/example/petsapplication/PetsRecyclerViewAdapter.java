package com.example.petsapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsapplication.Database.Pet;

import java.util.List;

public class PetsRecyclerViewAdapter extends RecyclerView.Adapter<PetsRecyclerViewAdapter.PetViewHolder>{


    private List<Pet> mPetList;

    private OnListItemClicked listItemClicked;

    private Context mContext;

    private static final String TAG = "PetsRecyclerViewAdapter";



    public PetsRecyclerViewAdapter(Context mContext, OnListItemClicked itemClicked) {
        this.mContext = mContext;
        this.listItemClicked = itemClicked;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
       Pet pet = mPetList.get(position);
       String pName = pet.getName();
       String pBreed = pet.getBreed();

       holder.name.setText(pName);
       holder.summary.setText(pBreed);
    }

    @Override
    public int getItemCount() {
        if (mPetList == null){
            return 0;
        }
        return mPetList.size();
    }

    public List<Pet> getPetList() {
        return mPetList;
    }

    public void setPetList(List<Pet> mPetList) {
        this.mPetList = mPetList;
        notifyDataSetChanged();
    }
    public interface OnListItemClicked{
        void OnClickedItem(int clickedItem);
    }

    public class PetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         TextView name;
         TextView summary;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
             name = itemView.findViewById(R.id.name);
             summary = itemView.findViewById(R.id.summary);
             itemView.setOnClickListener(this);

            Log.d(TAG, "PetViewHolder: ViewHolder called");
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClicked.OnClickedItem(clickedPosition);
        }
    }
}
