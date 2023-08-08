package com.uou.khackathon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uou.khackathon.R;
import com.uou.khackathon.adapter.ChatListAdapter;
import com.uou.khackathon.model.ChatList;

import java.util.ArrayList;


public class ChatFragment extends Fragment {
    ArrayList<ChatList> chatLists;
    RecyclerView recyclerView;
    ChatListAdapter adapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        initChatListDummy();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView(chatLists);
    }

    private void initChatListDummy(){
        chatLists = new ArrayList<>();
        chatLists.add(new ChatList("김철수","오후 9:36","안녕하세요 원룸 관련 문의드립니다.....","100",true));
        chatLists.add(new ChatList("최여진","오후 9:21","안녕하세요 원룸 관련 문의드립니다. 월세와 보증금..","4",false));
        chatLists.add(new ChatList("팜하니","오전 8:21","안녕하세요 원룸 관련 문의드립니다.","12",true));
        chatLists.add(new ChatList("김제니","12.30","안녕하세요 원룸 관련 문의드립니다. 월세와 보증금..","",true));
        chatLists.add(new ChatList("박보영","12.30","안녕하세요 원룸 관련 문의드립니다. 월세와 보증금..","",false));
        chatLists.add(new ChatList("김민지","12.30","안녕하세요 원룸 관련 문의드립니다. 월세와 보증금..","",false));
        chatLists.add(new ChatList("이정재","12.30","안녕하세요 원룸 관련 문의드립니다. 월세와 보증금..","",false));
        chatLists.add(new ChatList("최예나","12.30","안녕하세요 원룸 관련 문의드립니다. 월세와 보증금..","",false));
    }
    private void initRecyclerView(ArrayList<ChatList> result){
        recyclerView = view.findViewById(R.id.chat_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ChatListAdapter(result,getActivity());
        recyclerView.setAdapter(adapter);
    }
}