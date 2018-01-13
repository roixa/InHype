package com.roix.inhype;

import com.arellomobile.mvp.MvpView;

/**
 * Created by roix on 13.06.2017.
 */

public interface BaseView extends MvpView {
    void handleError(Throwable throwable);
    void handleProgress(boolean isProgress);
}
