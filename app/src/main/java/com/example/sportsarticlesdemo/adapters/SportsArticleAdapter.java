package com.example.sportsarticlesdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sportsarticlesdemo.POJO.Article;
import com.example.sportsarticlesdemo.R;
import com.example.sportsarticlesdemo.util.ItemClickListener;

import java.util.ArrayList;

public class SportsArticleAdapter extends RecyclerView.Adapter<SportsArticleAdapter.SportsArticleViewHolder>{
    private ArrayList<Article> mList = new ArrayList<>();
    private ItemClickListener listener;
    private Context mContext;
    boolean isDark;

    public SportsArticleAdapter(Context mContext, ItemClickListener listener, boolean isDark) {
        this.mContext = mContext;
        this.listener = listener;
        this.isDark = isDark;
    }

    public SportsArticleAdapter(ArrayList<Article> mList, ItemClickListener listener, Context mContext, boolean isDark) {
        this.mList = mList;
        this.listener = listener;
        this.mContext = mContext;
        this.isDark = isDark;
    }

    @NonNull
    @Override
    public SportsArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportsArticleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsArticleViewHolder holder, int position) {



        // add animation
        holder.articleImage.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

        // bind data here
        Glide.with(mContext)
                .load(mList.get(position).getUrlToImage())
                .circleCrop()
                .placeholder(R.drawable.ic_baseline_supervised_user_circle_24)
                .into(holder.articleImage);
        holder.titleTextView.setText(mList.get(position).getTitle());
        holder.authorTextView.setText(mList.get(position).getAuthor());



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<Article> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public class SportsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout container;
        private ImageView articleImage;
        private TextView titleTextView;
        private TextView authorTextView;
        ItemClickListener listener;

        public SportsArticleViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            this.listener = listener;

            container = itemView.findViewById(R.id.container);
            articleImage = itemView.findViewById(R.id.img);
            titleTextView = itemView.findViewById(R.id.title);
            authorTextView = itemView.findViewById(R.id.author);
            if (isDark) {
                setDarkTheme();
            }
            itemView.setOnClickListener(this);

        }


        private void setDarkTheme() { //apply dark theme
            container.setBackgroundResource(R.drawable.card_bg_dark);
        }

        @Override
        public void onClick(View v) { //Item ClickListener
            listener.onItemClick(v, getAdapterPosition(), false);

        }
    }


}
