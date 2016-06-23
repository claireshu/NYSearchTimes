package com.claireshu.nysearchtimes_;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by claireshu on 6/23/16.
 */
public class SpinnerArrayAdapter extends ArrayAdapter {

    Typeface font;

    public SpinnerArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);

        font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesanspro.otf");
    }

    //@Override
    public TextView getView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView
                ) super.getView(position, convertView, parent);
        v.setTypeface(font);
        return v;
    }

    //@Override
    public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView v = (TextView) super.getView(position, convertView, parent);
        v.setTypeface(font);
        return v;
    }


}
