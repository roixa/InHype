package com.roix.inhype.ui.renderers;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.roix.inhype.R;
import com.roix.inhype.RoixRecyclerAdapter;
import com.roix.inhype.databinding.ChoosePhotoItemBinding;
import com.roix.inhype.model.DataConverter;
import com.roix.inhype.model.DataRepository;
import com.roix.inhype.pojo.GetSelectionListResponse;
import com.roix.inhype.pojo.Photo;
import com.roix.inhype.pojo.inner.ListPhotoData;
import com.squareup.picasso.Picasso;

/**
 * Created by roix on 24.05.2017.
 */

public class ChooseListItemRenderer extends RoixRecyclerAdapter.RoixRenderer{

    ChoosePhotoItemBinding binding;
    String TAG="ChooseListItemRenderer";
    Context context;
    RoixRecyclerAdapter.ItemEventListener listener;

    public static final int EVENT_CLICK_LIKE=0;
    public static final int EVENT_CLICK_COMMENT=1;
    public static final int EVENT_CLICK_PROFILE=2;

    @Override
    public int getResID() {
        return R.layout.choose_photo_item;
    }


    @Override
    public void create(View v, RoixRecyclerAdapter.ItemEventListener listener) {
        binding= DataBindingUtil.bind(v);
        context=v.getContext();
        this.listener=listener;
    }

    @Override
    public void bind(final RoixRecyclerAdapter.RoixDataItem item) {
        if(!(item instanceof Photo)) return;
        Photo data= (Photo) item;
        Picasso.with(context).load(data.getPreview()).into(binding.photo);

        if(data.getDescription()!=null&&!data.getDescription().isEmpty()){
            SpannableString ss1=  new SpannableString("@"+data.getProfile().getNickname());
            ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
            binding.descrTextView.setText(ss1);
            binding.descrTextView.append(" ");
            binding.descrTextView.append(data.getDescription());
        }



        //binding.descrTextView.setText(data.getDescription());
        final ChooseListItemRenderer renderer=this;
        binding.likeFrame.setOnClickListener(v -> {
            if(!data.isLikedByMe())
                listener.OnEvent(EVENT_CLICK_LIKE,renderer);
        });

        binding.commentFrame.setOnClickListener(v -> listener.OnEvent(EVENT_CLICK_COMMENT,renderer));
        int id=data.isLikedByMe()?R.drawable.heart_pressed:R.drawable.heart;
        binding.likeButton.setImageResource(id);
        binding.likesCount.setText(data.getLikes()+"");
        binding.commentsCount.setText(data.getComments()+"");
        Pair<Integer,Integer> pair= DataConverter.getCategoryContentById(data.getFirstCategory());
        binding.categoryTv.setText(pair.second);
        binding.categoryImage.setImageResource(pair.first);
        if(data.getProfile()!=null){
            binding.profileName.setText(data.getProfile().getNickname());
            Picasso.with(binding.getRoot().getContext()).load(data.getProfile().getAvatar()).resize(50, 50).into(binding.icAvatar);
            binding.profileFrame.setOnClickListener(v -> {
                listener.OnEvent(EVENT_CLICK_PROFILE,data.getProfile().getId());
            });
            binding.icLevel.setImageResource(DataConverter.getLevelResLevel(data.getProfile().getRating()));
            binding.tvLevel.setText(data.getProfile().getRating()+"");
        }
        else binding.profileFrame.setVisibility(View.GONE);

}

    @Override
    public void change(int eventType, RoixRecyclerAdapter.RoixDataItem item) {
        Log.d(TAG,"change"+eventType);
    }


    public void handleLikeButton(boolean isLike){
        Log.d(TAG,"handleLikeButton");
        int id=isLike?R.drawable.heart_pressed:R.drawable.heart;
        binding.likeButton.setImageResource(id);
    }

}
