package com.heinerthebest.heiner.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class AppWidgetService extends RemoteViewsService {

    public static void updateWidget(Context context,String title, String ingredients)
    {
        Prefs.saveRecipeTitle(context,title);
        Prefs.saveRecipeIngredients(context,ingredients);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,BakingAppProvider.class));
        BakingAppProvider.updateAppWidgets(context,appWidgetManager,appWidgetIds);
    }


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return null;
    }
}
