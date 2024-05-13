package com.example.myapplication.view.AdapterRecycleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.helper.DateHelper;
import com.example.myapplication.model.ListMessage;
import com.example.myapplication.model.Message;
import com.example.myapplication.model.UserLogin;
import com.example.myapplication.view.Controller.OnDataChangedListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    Context context;
    ListMessage list;
    UserLogin currentUser;
    final int MESSAGE_MIND = 1;
    final int MESSAGE_ENEMY = 2;
    OnDataChangedListener onDataChangedListener;
    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void setOnDataChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    public void setData(ListMessage list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(Message message) {
        if (this.list.getMessages() == null)
            this.list.setMessages(new LinkedList<>());
        this.list.getMessages().add(message);
        notifyItemInserted(0);
    }

    public void setCurrentUser(UserLogin currentUser)
    {
        this.currentUser = currentUser;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            Message message =  list.getMessages().get(list.getMessages().size() - 1 - position);
            return message.getSenderId() == currentUser.getId() ? MESSAGE_MIND : MESSAGE_ENEMY;
        } catch (Exception e) {}
        return -1;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == MESSAGE_MIND)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_itemmessage_mind, parent, false);
        else if (viewType == MESSAGE_ENEMY)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_itemmessage_enemy, parent, false);
        if (view == null) return null;
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        try {
            Message message =  list.getMessages().get(list.getMessages().size() - 1 - position);
            holder.content.setText(message.getContent());
            if (holder.timeSent != null)
                holder.timeSent.setText(DateHelper.demThoiGian(message.getTimeSend()));
            else if (holder.timeReaded != null)
                holder.timeReaded.setText(message.getTimeRead() == null ? "Chưa đọc" : "Đã xem " +
                    DateHelper.demThoiGian(message.getTimeRead()));
            String url = list.getCouple().getEnemy().getUrlAvatar();
            if (holder.avatarEnemy != null && url != null) {
                if (!(url == null || url.isEmpty()))
                    try {
                        Picasso.get().load(url).into(holder.avatarEnemy);
                    } catch (Exception e) {
                        holder.avatarEnemy.setBackgroundResource(R.drawable.account_svgrepo_com);
                    }
            }
        } catch (Exception e) {}
    }

    @Override
    public int getItemCount() {
        if (list == null || list.getMessages() == null || list.getMessages().size() == 0)
            return 0;
        return list.getMessages().size();
    }

    class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView timeSent;
        TextView timeReaded;
        ShapeableImageView avatarEnemy;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.idPageMessageItemContent);
            timeReaded = itemView.findViewById(R.id.idPageMessageItemTimeRead);
            timeSent = itemView.findViewById(R.id.idPageMessageItemTimeSend);
            avatarEnemy = itemView.findViewById(R.id.idPageMessageItemImageAvatar);
        }
    }

}
