package com.swaraj.bookmybook;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {


    private Context mContext;
    private List<Book> mUploads;
    private List<Book> mUploadsFiltered;
    String imgurl;

    public ImageAdapter(Context context, List<Book> uploads) {
        this.mContext = context;
        this.mUploads = uploads;
        this.mUploadsFiltered = uploads;

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardveiw_item_book, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        holder.img_book_thumbnail.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition_animation));
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));

        Book uploadCurrent = mUploadsFiltered.get(position);

        imgurl = uploadCurrent.getmImageUrl();

        System.out.println(imgurl);

        holder.tv_book_title.setText(mUploadsFiltered.get(position).getTitle());
        Glide.with(mContext).load(uploadCurrent.getmImageUrl()).into(holder.img_book_thumbnail);
        /*Picasso.with(mContext)
                .load(imgurl)
                //.placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.img_book_thumbnail);*/
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, Book_Activity.class);

                // passing data to the book activity
                intent.putExtra("Title", mUploads.get(position).getTitle());
                intent.putExtra("Description", mUploads.get(position).getDescription());
                intent.putExtra("Thumbnail", mUploads.get(position).getmImageUrl());
                intent.putExtra("BOOK_ID", mUploads.get(position).getBook_id());
                intent.putExtra("book_status", mUploads.get(position).isRequested());
                intent.putExtra("accept_status", mUploads.get(position).isAccepted());
                intent.putExtra("Requester_id", mUploads.get(position).getRequester_id());

               // intent.putExtra("Thumbnail", mUploads.get(position).get);
                //code to show book image and its info
                // start the activity
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUploadsFiltered.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView tv_book_title;
        ImageView img_book_thumbnail;
        CardView cardView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            tv_book_title = (TextView) itemView.findViewById(R.id.book_title_id);
            img_book_thumbnail = (ImageView) itemView.findViewById(R.id.book_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mUploadsFiltered = mUploads;

                } else {
                    List<Book> lstFiltered = new ArrayList<>();
                    for (Book row : mUploads) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())) {
                            lstFiltered.add(row);
                        }

                    }

                    mUploadsFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = mUploadsFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               mUploadsFiltered = (List<Book>) results.values;
                notifyDataSetChanged();
            }


        };
    }
}




/*import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.images_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        Picasso.get()
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}*/
