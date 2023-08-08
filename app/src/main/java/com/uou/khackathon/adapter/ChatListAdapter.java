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
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        ChatListAdapter.ViewHolder vh = new ChatListAdapter.ViewHolder(view) ;

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.ViewHolder holder, int position) {
        String number = result.get(position).getChatCount();
        int chatCount;
        holder.name.setText(result.get(position).getName());
        holder.time.setText(result.get(position).getTime());
        holder.chatContents.setText(result.get(position).getChatContents());


        if(!result.get(position).isValid()){ // 인증된 유저가 아닐때
            holder.validMark.setVisibility(View.INVISIBLE);
        }
        if(number.equals("")){
            chatCount = 0;
        }else{
            chatCount = Integer.parseInt(number);
        }
        if(chatCount == 0){
            holder.countCoverImage.setVisibility(View.GONE);
        }
        else if (chatCount < 10) {
            holder.chatCount.setText(result.get(position).getChatCount());
            holder.countCoverImage.setImageResource(R.drawable.one_digit);
        } else if (chatCount < 100) {
            holder.chatCount.setText(result.get(position).getChatCount());
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
