package com.andrea.lettherebelife.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.andrea.lettherebelife.R;
import com.andrea.lettherebelife.features.common.domain.Plant;
import com.andrea.lettherebelife.features.main.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.MODE_PRIVATE;
import static com.andrea.lettherebelife.features.common.ActivityConstants.SHARED_PREFERENCES;
import static com.andrea.lettherebelife.features.common.ActivityConstants.WIDGET;

/**
 * Implementation of App Widget functionality.
 */
public class PlantWidget extends AppWidgetProvider {

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, List<Plant> plantList) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.plant_widget);

        Intent appIntent;

        if (plantList == null) {
            views.setTextViewText(R.id.plantNameTextView, "Sorry, no plants have been loaded.");
        } else {
            views.removeAllViews(R.id.widgetPlantListLinearLayout);

            for (Plant plant : plantList) {
                RemoteViews plantView = new RemoteViews(context.getPackageName(), R.layout.plant_widget_list_item);
                plantView.setTextViewText(R.id.plantNameTextView, plant.getName());
                plantView.setTextViewText(R.id.plantSeedDateTextView, plant.getSeedDate());
                views.addView(R.id.widgetPlantListLinearLayout, plantView);
            }
        }

        appIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.plantNameTextView, pendingIntent);
        views.setOnClickPendingIntent(R.id.widgetPlantListLinearLayout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Taken from https://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        List<Plant> plantList = new Gson().fromJson(sharedPreferences.getString(WIDGET, null), new TypeToken<ArrayList<Plant>>(){}.getType());

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, plantList);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

