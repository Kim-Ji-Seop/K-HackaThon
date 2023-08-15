package com.uou.khackathon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uou.khackathon.R;
import com.uou.khackathon.model.ChatList;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder>{
    private ArrayList<ChatList> result;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfile, validMark, buildingProfile, countCoverImage;
        TextView name, time, chatContents,chatCount;
        ViewHolder(View itemView) {
            super(itemView) ;
            // 뷰 객체에 대한 참조.
            userProfile = itemView.findViewById(R.id.profile); // 왼쪽 유저 프로필
            buildingProfile = itemView.findViewById(R.id.chat_building_image); // 오른쪽 매물사진
            countCoverImage = itemView.findViewById(R.id.chat_receive_count); // 수신 톡 갯수 커버이미지
            validMark = itemView.findViewById(R.id.is_valid_user); // 인증 마크
            name = itemView.findViewById(R.id.user_name);
            time = itemView.findViewById(R.id.chat_receive_time);
            chatContents = itemView.findViewById(R.id.user_receive_chat);
            chatCount = itemView.findViewById(R.id.chat_receive_count_text);
        }
    }
    public ChatListAdapter(ArrayList<ChatList> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, int position) {
        ChatList currentItem = result.get(position);

        holder.name.setText(currentItem.getName());
        holder.time.setText(currentItem.getTime());
        holder.chatContents.setText(currentItem.getChatContents());

        if (!currentItem.isValid()) {
            holder.validMark.setVisibility(View.INVISIBLE);
        }
        setChatCountDisplay(holder, currentItem);
    }

    private void setChatCountDisplay(ViewHolder holder, ChatList chatList) {
        String number = chatList.getChatCount();
        int chatCount = (number.isEmpty()) ? 0 : Integer.parseInt(number);

        if (chatCount == 0) {
            holder.countCoverImage.setVisibility(View.GONE);
            return;
        }

        if (chatCount < 10) {
            holder.chatCount.setText(number);
            holder.countCoverImage.setImageResource(R.drawable.one_digit);
        } else if (chatCount < 100) {
            holder.chatCount.setText(number);
            holder.countCoverImage.setImageResource(R.drawable.two_digits);
        } else {
            holder.chatCount.setText("99+");
            holder.countCoverImage.setImageResource(R.drawable.three_digits);
        }
    }
    @Override
    public int getItemCount() {
        return result.size();
    }
}
