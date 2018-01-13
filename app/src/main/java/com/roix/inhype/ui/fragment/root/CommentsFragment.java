package com.roix.inhype.ui.fragment.root;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.roix.inhype.MVPDialogFragment;
import com.roix.inhype.MyApplication;
import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.SoftKeyboard;
import com.roix.inhype.databinding.FragmentCommentsBinding;
import com.roix.inhype.databinding.FragmentCreatePhotoBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Comment;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.Profile;
import com.roix.inhype.presentation.view.root.CommentsView;
import com.roix.inhype.presentation.presenter.root.CommentsPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roix.inhype.ui.activity.profile.ProfileActivity;
import com.roix.inhype.ui.renderers.ChooseListItemRenderer;
import com.roix.inhype.ui.renderers.CommentRenderer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends MVPDialogFragment implements CommentsView,RoixRecyclerAdapter.EventListener {

    public static final String TAG = "CommentsFragment";
    @InjectPresenter
    CommentsPresenter mCommentsPresenter;
    FragmentCommentsBinding binding;
    private RoixRecyclerAdapter adapter;

    public static CommentsFragment newInstance(Photo photo) {
        CommentsFragment fragment = new CommentsFragment();
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        Bundle args = new Bundle();
        args.putParcelable("photo",photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        hideKeyboard();

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_comments, container, false);
        binding.setPresenter(mCommentsPresenter);
        listenKeyboard();
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Photo item=getArguments().getParcelable("photo");

        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new RoixRecyclerAdapter(getActivity(),binding.rv,this);
        binding.rv.setAdapter(adapter);
        //List<Photo> photos=new ArrayList<>();
        //photos.add(item);
        //adapter.addItems(photos,ChooseListItemRenderer.class);
        fillContent(item);
        mCommentsPresenter.loadProfile(item.getOwnerUser());
        mCommentsPresenter.init(item);

    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public  void hideKeyboard() {
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onEvent(int type, Object content, RoixRecyclerAdapter.RoixDataItem item, int pos) {

    }

    @Override
    public void onLoadComments(List<Comment> comments) {
        //adapter.addItems(comments, CommentRenderer.class);
        adapter.updateAndAddItems(comments,CommentRenderer.class);
    }

    @Override
    public void onLoadProfile(Profile profile) {
        if(profile==null){
            return;
        }

        binding.content.profileName.setText(profile.getNickName());
        Picasso.with(binding.getRoot().getContext()).load(profile.getAvatar()).resize(50, 50).into(binding.content.icAvatar);
        binding.content.icLevel.setImageResource(DataConverter.getLevelResLevel(profile.getRating()));
        binding.content.tvLevel.setText(profile.getRating()+"");

    }

    @Override
    public void onSentComment() {
        binding.commentEditText.setText("");
        mCommentsPresenter.getLastComments();
        hideKeyboard();

    }


    @Override
    public void showToast(String s) {

        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleLikeButton(boolean isLiked) {
        int id=isLiked?R.drawable.heart_pressed:R.drawable.heart;
        binding.content.likeButton.setImageResource(id);
    }

    @Override
    public void handleError(Throwable throwable) {
        showToast(DataConverter.handleErrorMessage(throwable));
    }

    @Override
    public void handleProgress(boolean isProgress) {

    }

    private void handleKeyboardEvent(boolean isShow){
        Log.d(getClass().toString(),"handleKeyboardEvent "+isShow);
        binding.content.photoContent.setVisibility(isShow?View.GONE:View.VISIBLE);
        binding.sendCommentButton.setVisibility(isShow?View.VISIBLE:View.GONE);
    }

    private void fillContent(Photo data){
        binding.content.moreMenu.setVisibility(View.VISIBLE);
        Picasso.with(getActivity()).load(data.getPreview()).into(binding.content.photo);
        //binding.content.descrTextView.setText(data.getDescription());
        if(data.getProfile()!=null){
            SpannableString ss1=  new SpannableString("@"+data.getProfile().getNickname());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            binding.content.descrTextView.setText(ss1);
            binding.content.descrTextView.append(" ");

        }
        binding.content.descrTextView.append(data.getDescription());

        binding.content.likeFrame.setOnClickListener(v -> {
        });
        binding.content.commentFrame.setOnClickListener(v -> {});
        int id=data.isLikedByMe()?R.drawable.heart_pressed:R.drawable.heart;
        binding.content.likeButton.setImageResource(id);
        binding.content.likesCount.setText(data.getLikes()+"");
        binding.content.commentsCount.setText(data.getComments()+"");
        Pair<Integer,Integer> pair= DataConverter.getCategoryContentById(data.getFirstCategory());
        binding.content.categoryTv.setText(pair.second);
        binding.content.categoryImage.setImageResource(pair.first);
        binding.timeText.setText(data.getDate());
        Picasso.with(getActivity()).load(mCommentsPresenter.getOwnerAva()).resize(50, 50).into(binding.userAva);
        binding.content.moreMenu.setOnClickListener(v -> showActionDialog(data));
        binding.content.likeFrame.setOnClickListener(v -> mCommentsPresenter.likePhoto(data));
        binding.content.profileFrame.setOnClickListener(v -> startActivity(ProfileActivity.getIntent(getActivity(),data.getOwnerUser())));

    }

    private void showActionDialog(Photo photo){
        new AlertDialog.Builder(getActivity()).setPositiveButton("add to favorites",(dialog, which) -> {
            mCommentsPresenter.handleFavoritesButton(photo);
        }).setNegativeButton("report",(dialog, which) -> {
            mCommentsPresenter.handleClaimButton(photo);
        }).create().show();
    }

    private void listenKeyboard(){
        InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Service.INPUT_METHOD_SERVICE);
        SoftKeyboard softKeyboard;
        softKeyboard = new SoftKeyboard(binding.dialogRoot, im);
        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged()
        {
            @Override
            public void onSoftKeyboardHide()
            {
                new Handler(Looper.getMainLooper()).post(() -> {
                    handleKeyboardEvent(false);
                });
            }

            @Override
            public void onSoftKeyboardShow()
            {
                new Handler(Looper.getMainLooper()).post(() -> {
                    handleKeyboardEvent(true);
                });
            }
        });

    }
}
