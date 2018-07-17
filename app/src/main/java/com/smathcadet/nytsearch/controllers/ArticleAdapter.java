package com.smathcadet.nytsearch.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smathcadet.nytsearch.R;
import com.smathcadet.nytsearch.models.Article;
import com.smathcadet.nytsearch.views.ArticleActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo de Marcus on 7/15/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {

    private ArrayList<Article> mArticleArrayList;
    private Context mContext;

    public ArticleAdapter(ArrayList<Article> articleArrayList, Context context) {
        this.mArticleArrayList = articleArrayList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new ArticleHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ArticleHolder holder, final int position) {
        holder.tvHeadline.setText(mArticleArrayList.get(position).getHeadline());
        holder.ivThumbnail.setImageResource(0);
        
        String imageThumbnail = mArticleArrayList.get(position).getThumbnail();
        if (!TextUtils.isEmpty(imageThumbnail)) {
            Picasso.with(mContext).load(imageThumbnail).fit().centerInside().placeholder(R.mipmap.ic_launcher).into(holder.ivThumbnail);
        }else {
            Picasso.with(mContext).load(R.drawable.ic_placeholder).fit().centerInside().into(holder.ivThumbnail);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ArticleActivity.newIntent(mContext, mArticleArrayList.get(position));
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mArticleArrayList.size();
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {
        ImageView ivThumbnail;
        TextView tvHeadline;

        public ArticleHolder(View itemView) {
            super(itemView);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvHeadline = itemView.findViewById(R.id.tvHeadline);

        }
    }
}
