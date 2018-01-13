package com.roix.inhype;

import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.roix.inhype.model.ContentRepository;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by roix on 13.06.2017.
 */

public  abstract class  BasePresenter<T extends BaseView>  extends MvpPresenter<T> {

    protected ContentRepository rep;


    @Override
    protected void onFirstViewAttach() {
        rep=MyApplication.getContentRepository();
        super.onFirstViewAttach();
    }

    public void handleError(Throwable throwable){
        getViewState().handleError(throwable);
        getViewState().handleProgress(false);
    }


    public <T> Disposable sub(Single<T> single,Consumer<T> consumer){
        Log.d("BasePresenter","handleProgress(false)");
        getViewState().handleProgress(true);
        return single.doAfterSuccess(o -> {
            Log.d("BasePresenter","handleProgress(true)");
            getViewState().handleProgress(false);
        }).subscribe(consumer,this::handleError);
    }

    public <T> Disposable sub(Completable completable,Action action){
        getViewState().handleProgress(true);
        Log.d("BasePresenter","handleProgress(false)");
        return completable.doOnComplete(() -> {
            getViewState().handleProgress(false);
            Log.d("BasePresenter","handleProgress(true)");
        }).subscribe(action,this::handleError);
    }

}
