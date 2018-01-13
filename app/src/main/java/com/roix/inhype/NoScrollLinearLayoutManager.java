package com.roix.inhype;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by roix on 28.06.2017.
 */

public class NoScrollLinearLayoutManager extends LinearLayoutManager {
    public NoScrollLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }
}
