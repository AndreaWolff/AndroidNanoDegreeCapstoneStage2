package com.andrea.lettherebelife.features.details.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.dagger.component.DaggerPlantDetailsComponent;
import com.andrea.lettherebelife.features.plantinfo.ui.PlantInfoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.andrea.lettherebelife.application.PlantApplication.getDagger;

public class PlantDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_details);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        DaggerPlantDetailsComponent.builder()
                .appComponent(getDagger())
                .build()
                .inject(this);
    }

    // region Plant Information menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plant_info_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_plant_information).setVisible(true).setTitle("About Plant");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_plant_information:
                Intent intent = new Intent(this, PlantInfoActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    // endregion
}
