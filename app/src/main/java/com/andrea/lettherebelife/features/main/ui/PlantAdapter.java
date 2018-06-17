package com.andrea.lettherebelife.features.main.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.features.common.domain.Plant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.lettherebelife.util.DecodeImageFromFirebase.decodeImageFromFirebaseBase64;
import static com.andrea.lettherebelife.util.GlideUtil.displayImage;
import static com.andrea.lettherebelife.util.GlideUtil.displayPlantImage;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private ListItemClickListener listItemClickListener;
    private List<Plant> plantList;

    public interface ListItemClickListener {
        void onListItemClicked(@NonNull Plant plant);
    }

    PlantAdapter(@NonNull ListItemClickListener listItemClickListener, @NonNull List<Plant> plantList) {
        this.listItemClickListener = listItemClickListener;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_list_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return plantList != null && plantList.size() > 0 ? plantList.size() : 0;
    }

    class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.plant_name) TextView plantNameTextView;
        @BindView(R.id.plant_seed_date) TextView plantSeedDateTextView;
        @BindView(R.id.plant_image) ImageView plantImageView;

        PlantViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int listItem) {
            plantNameTextView.setText(plantList.get(listItem).getName());
            plantSeedDateTextView.setText(plantList.get(listItem).getSeedDate());

            String photoUrl = plantList.get(listItem).getPhotoUrl();
            if (photoUrl != null) {
                if (!photoUrl.contains("http")) {
                    plantImageView.setImageBitmap(decodeImageFromFirebaseBase64(photoUrl));
                } else {
                    displayPlantImage(photoUrl, plantImageView);
                }
            } else {
                displayImage("", plantImageView);
            }
        }

        @Override
        public void onClick(View view) {
            listItemClickListener.onListItemClicked(plantList.get(getAdapterPosition()));
        }
    }
}
