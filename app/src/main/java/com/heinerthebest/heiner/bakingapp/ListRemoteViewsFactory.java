package com.heinerthebest.heiner.bakingapp;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private String[] ingredients;

    public ListRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredients = Prefs.loadIngredientsList(mContext);

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.length;
    }

    @Override
    public RemoteViews getViewAt(int position) {
            RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            row.setTextViewText(R.id.widget_tv_step, ingredients[position]);

            return row;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



}
