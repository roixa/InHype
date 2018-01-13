package com.roix.inhype.presentation.view.root;

import com.arellomobile.mvp.MvpView;
import com.roix.inhype.BaseView;
import com.roix.inhype.pojo.Comment;
import com.roix.inhype.pojo.Profile;

import java.util.List;

public interface CommentsView extends BaseView {

    void onLoadComments(List<Comment> comments);
    void onLoadProfile(Profile profile);
    void onSentComment();
    void showToast(String s);
    void handleLikeButton(boolean isLiked);
}
