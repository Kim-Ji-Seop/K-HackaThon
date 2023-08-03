package com.uou.khackathon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigation();
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,homeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.structureFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,structureFragment).commitAllowingStateLoss();
                        break;
                    case R.id.subscribeFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,subscribeFragment).commitAllowingStateLoss();
                        break;
                    case R.id.chatFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,chatFragment).commitAllowingStateLoss();
                        break;
                    case R.id.myPageFragment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js,myPageFragment).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }
}