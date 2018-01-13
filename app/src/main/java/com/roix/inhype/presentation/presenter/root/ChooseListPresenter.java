package com.roix.inhype.presentation.presenter.root;



import com.roix.inhype.BasePresenter;
import com.roix.inhype.MyApplication;
import com.roix.inhype.pojo.LoginResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.ListPhotoDataList;
import com.roix.inhype.presentation.view.root.ChooseListView;
import com.arellomobile.mvp.InjectViewState;

@InjectViewState
public class ChooseListPresenter extends BasePresenter<ChooseListView> {
    private int currCategory=0;

    public void init(){
        if(rep==null)rep=MyApplication.getContentRepository();

        //sub(rep.getSelection(0,  1, 10).flatMap(response -> rep.getProfilesInfo(response.getPhotos())), listPhotoDatas ->
        sub(rep.getTop().flatMap(response -> rep.getProfilesInfo(response.getPhotos())),photos -> {
            getViewState().setupTopList(photos);
            sub(rep.getBest(0,  1, 10).flatMap(response -> rep.getProfilesInfo(response.getPhotos())), listPhotoDatas ->
                    {
                        getViewState().onLoadItems(listPhotoDatas);
                    }
            );

        });

    }

    public void loadMore(int page){
        loadContent(page,currCategory);
    }

    public void likePhoto(Photo photo, final int pos){
        sub(rep.likePhoto(photo.getId(), photo.getConfirmed()), likePhotoResponse ->
                getViewState().handleLikeButtonInList(true,likePhotoResponse.getLikes(),likePhotoResponse.getComments(),pos)
        );
    }

    public void commentsButtonClicked(Photo photo){
        getViewState().prepareCommentsState(photo);
    }

    public LoginResponse getLoginInfo(){
        return rep.getLoginInfo();
    }

    public void changeCategory(int categoryId){
        currCategory=categoryId;
        getViewState().clearCategoryContent();
        loadContent(0,categoryId);
        //sub(rep.getSelection(categoryId, 1, 10), response -> getViewState().updateItems(response));
    }
    private void loadContent(int page,int category){
        //sub(rep.getSelection(category, page + 1, 10).flatMap(response -> rep.getProfilesInfo(response.getPhotos())), listPhotoDatas -> getViewState().onLoadItems(listPhotoDatas));
        sub(rep.getBest(category, page + 1, 10).flatMap(response -> rep.getProfilesInfo(response.getPhotos())), listPhotoDatas -> getViewState().onLoadItems(listPhotoDatas));
    }

}
