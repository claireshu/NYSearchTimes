package com.claireshu.nysearchtimes_;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by claireshu on 6/20/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {

//    public ArticleArrayAdapter(Context context, List<Article> articles) {
//        super(context, android.R.layout.simple_list_item_1,articles);
//    }
    Typeface font;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }


    }

    private List<Article> mArticles;

    public ArticleArrayAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesanspro.otf");
    }

    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_article_result, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder viewHolder, int position) {
        Article article = mArticles.get(position);

        TextView tvTitle = viewHolder.tvTitle;
        tvTitle.setText(article.getHeadline());
        tvTitle.setTypeface(font);

        // find the image view
        ImageView imageView = viewHolder.ivImage;
        // clear out recycled image from convertView from last time
        imageView.setImageResource(0);


        String thumbnail = article.getThumbNail();
        if (!TextUtils.isEmpty(thumbnail)) {
            Glide.with(imageView.getContext())
                    .load(thumbnail)
                    .into(imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        //get the data item for the position
//        Article article = this.getItem(position);
//
//        // check to see if the existing view is being reused
//        // not using a recycled view --> inflate the layout
//        if (convertView == null) {
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.item_article_result, parent, false);
//        }
//        // find the image view
//        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);
//       // clear out recycled image from convertView from last time
//        imageView.setImageResource(0);
//
//        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
//        tvTitle.setText(article.getHeadline());
//
//        // populate the thumbnail image
//        // remote download the image in the background
//
//        String thumbnail = article.getThumbNail();
//        if (!TextUtils.isEmpty(thumbnail)) {
//            Picasso.with(getContext()).load(thumbnail).into(imageView);
//        }
//        return convertView;
//    }
}
