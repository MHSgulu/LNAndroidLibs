package com.lnkj.android.mapchooseaddress;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBehavior extends CoordinatorLayout.Behavior<NestedScrollView> {

    public RecyclerViewBehavior() {
    }

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        return dependency instanceof ImageView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        //计算列表y坐标，最小为0
        float y = dependency.getHeight() + dependency.getTranslationY();



        Log.e("TAG", y+"");
        if (y < 1000) {
            y = 1000;
        }
        child.setY(y);
        return true;
    }
}