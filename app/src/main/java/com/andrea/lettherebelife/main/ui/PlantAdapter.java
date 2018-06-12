package com.andrea.lettherebelife.main.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrea.lettherebelife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private ListItemClickListener listItemClickListener;

    public interface ListItemClickListener {
        void onListItemClicked();
    }

    PlantAdapter(@NonNull ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
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
        return 5;
    }

    class PlantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.plant_image) ImageView plantImageView;
        @BindView(R.id.plant_name) TextView plantNameTextView;
        @BindView(R.id.plant_seed_date) TextView plantSeedDateTextView;

        PlantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int listItem) {
            plantNameTextView.setText("Plant Name");
            plantSeedDateTextView.setText("Seed Date");
        }

        @Override
        public void onClick(View view) {
            listItemClickListener.onListItemClicked();
        }
    }
}
