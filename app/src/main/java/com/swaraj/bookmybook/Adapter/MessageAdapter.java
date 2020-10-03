package com.swaraj.bookmybook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imageuploader.MessageActivity;
import com.example.imageuploader.Model.Chat;
import com.example.imageuploader.Model.User;
import com.example.imageuploader.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_Right = 1;

    private Context mContext;
    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mchat,String imageurl) {
        this.mContext = mContext;
        this.mchat = mchat;
        this.imageurl = imageurl;
    }

    private List<Chat> mchat;
    private String imageurl;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if( viewType == MSG_TYPE_Right) {
            View view = LayoutInflater.from(mContext).inflate((R.layout.chat_item_right), parent, false);
            return new ViewHolder(view);
        }
        else
        {
            View view = LayoutInflater.from(mContext).inflate((R.layout.chat_item_left), parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chat chat = mchat.get(position);
        holder.show_message.setText(chat.getMessage());

        if("default".equals(imageurl)){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else
        {
            Glide.with(mContext).load(imageurl).into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mchat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView show_message;
        public ImageView profile_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message  = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);

        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if(mchat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_Right;
        }
        else
            return MSG_TYPE_LEFT;
    }
}
