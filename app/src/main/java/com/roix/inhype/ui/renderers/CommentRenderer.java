package com.roix.inhype.ui.renderers;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.CommentItemBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.pojo.Comment;
import com.squareup.picasso.Picasso;

/**
 * Created by roix on 06.06.2017.
 */

public class CommentRenderer extends RoixRecyclerAdapter.RoixRenderer {

    CommentItemBinding binding;

    Context context;
    RoixRecyclerAdapter.ItemEventListener listener;

    @Override
    public int getResID() {
        return R.layout.comment_item;
    }


    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        context=v.getContext();
        this.listener=listener;
    }

    @Override
    public void bind(RoixRecyclerAdapter.RoixDataItem item) {
        Comment comment= (Comment) item;
        Log.d("CommentRenderer","comment "+comment.getText()+" profile "+comment.getProfile());

        if(comment.getProfile()!=null){
            Log.d("CommentRenderer","comment "+comment.getText()+" profile "+comment.getProfile().getNickname());
            SpannableString ss1=  new SpannableString("@"+comment.getProfile().getNickname());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            binding.commentTv.setText(ss1);
            Picasso.with(binding.getRoot().getContext()).load(comment.getProfile().getAvatar()).resize(50, 50).into(binding.profileIconsElement.icAvatar);
            binding.profileIconsElement.getRoot().setOnClickListener(v -> {
                listener.OnEvent(ChooseListItemRenderer.EVENT_CLICK_PROFILE,comment.getProfile().getId());
            });

        }
        binding.commentTv.append(" ");
        binding.commentTv.append(comment.getText());

        int levelRes= DataConverter.getLevelResLevel(comment.getProfile().getRating());
        binding.profileIconsElement.icLevel.setImageResource(levelRes);
        binding.profileIconsElement.tvLevel.setText(comment.getProfile().getRating()+"");
    }

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {

    }

}
