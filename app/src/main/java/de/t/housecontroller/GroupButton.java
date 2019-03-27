package de.t.housecontroller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class GroupButton extends View {


    public GroupButton(Context context) {
        super(context);
    }

    public GroupButton(Context context, ViewGroup vg){
        super(context);
        inflate(context, R.layout.group_button, vg);
    }
}
