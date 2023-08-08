package com.uou.khackathon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.uou.khackathon.R;
import com.uou.khackathon.fragment.ChatFragment;
import com.uou.khackathon.fragment.HomeFragment;
import com.uou.khackathon.fragment.MyPageFragment;
import com.uou.khackathon.fragment.StructureFragment;
import com.uou.khackathon.fragment.SubscribeFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private StructureFragment structureFragment;
    private ChatFragment chatFragment;
    private SubscribeFragment subscribeFragment;
    private MyPageFragment myPageFragment;
    private TextView headerText;
    private ImageView mainHeaderMakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initBottomNavigation();
    }
    private void initView(){
        headerText = findViewById(R.id.main_header_text_js);
        mainHeaderMakeView = findViewById(R.id.main_header_make);
    }
    private void initBottomNavigation() {
        homeFragment = new HomeFragment();
        structureFragment = new StructureFragment();
        chatFragment = new ChatFragment();
        subscribeFragment = new SubscribeFragment();
        myPageFragment = new MyPageFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv_js); // 처음화면
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        getSupportFragmentManager().beginTransaction().add(R.id.main_frm_js, homeFragment).commitAllowingStateLoss(); // FrameLayout에 fragment_home.xml 띄우기

        //바텀 네비게이션 뷰 안의 아이템 설정
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){ // item 클릭 시 id값을 가져와서 FrameLayout에 fragment_xxx.xml띄우기
                    case R.id.homeFragment:
                        headerText.setText("울산광역시 무거동");
                        mainHeaderMakeView.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.structureFragment:
                        headerText.setText("매물 목록");
                        mainHeaderMakeView.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,structureFragment).commitAllowingStateLoss();
                        break;
                    case R.id.subscribeFragment:
                        headerText.setText("찜 목록");
                        mainHeaderMakeView.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,subscribeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.chatFragment:
                        headerText.setText("채팅 목록");
                        mainHeaderMakeView.setVisibility(View.INVISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,chatFragment).commitAllowingStateLoss();
                        break;
                    case R.id.myPageFragment:
                        headerText.setText("내 정보");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,myPageFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }
}