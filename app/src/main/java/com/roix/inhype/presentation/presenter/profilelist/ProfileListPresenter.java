package com.roix.inhype.presentation.presenter.profilelist;


import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.pojo.GetSubscribersResponse;
import com.roix.inhype.pojo.ProfileShort;
import com.roix.inhype.presentation.view.profilelist.ProfileListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.roix.inhype.ui.activity.profilelist.ProfileListActivity;

@InjectViewState
public class ProfileListPresenter extends BasePresenter<ProfileListView> {
    public void init(int type,int profileId){
        if(rep==null)rep= MyApplication.getContentRepository();
        if(type== ProfileListActivity.TYPE_SUBSCRIBERS){
            sub(rep.getSubscribers(1,10)
                    .flattenAsObservable(GetSubscribersResponse::getSubscribers)
                    .map(profileShort -> {
                        profileShort.setSubscriber(true);
                        return profileShort;
                    })
                    .toList(), response -> {getViewState().onLoadProfiles(response);});
        }
        else sub(rep.getSubscriptions(1,10),response -> {getViewState().onLoadProfiles(response.getSubscriptions());});
    }

    public void handleSubscribeClick(ProfileShort profileShort,int pos){
        if(profileShort.isSubscribes()){
            sub(rep.unsubscribe(profileShort.getId()),() -> getViewState().updateItemUi(false,profileShort.isHasReciprocity(),pos));
        }else {
            sub(rep.subscribe(profileShort.getId()),() -> getViewState().updateItemUi(true,profileShort.isHasReciprocity(),pos));
        }
    }
}
