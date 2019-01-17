package com.heinerthebest.heiner.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.heinerthebest.heiner.bakingapp.Activities.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppProvider extends AppWidgetProvider {

    public static void updateAppWidget(Context context,
                                AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        String tittle = Prefs.loadRecipeTitle(context);
        String ingredientes = Prefs.loadIngredients(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);

        views.setTextViewText(R.id.widget_tv_steps_description_tittle, tittle);
        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.widget_tv_steps_description_tittle, pendingIntent);

        /*views.setTextViewText(R.id.widget_tv_step,ingredientes);
        views.setOnClickPendingIntent(R.id.widget_tv_step, pendingIntent);*/


        // Initialize the list view
        Intent intent = new Intent(context, AppWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        views.setRemoteAdapter(R.id.recipe_widget_listview,intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.recipe_widget_listview);

    }


    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

