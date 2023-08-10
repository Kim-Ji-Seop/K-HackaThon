package com.uou.khackathon.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.naver.maps.map.NaverMapSdk;
import com.uou.khackathon.R;
import com.uou.khackathon.fragment.ChatFragment;
import com.uou.khackathon.fragment.HomeFragment;
import com.uou.khackathon.fragment.MyPageFragment;
import com.uou.khackathon.fragment.StructureFragment;
import com.uou.khackathon.fragment.SubscribeFragment;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout headerLayout;
    private HomeFragment homeFragment;
    private StructureFragment structureFragment;
    private ChatFragment chatFragment;
    private SubscribeFragment subscribeFragment;
    private MyPageFragment myPageFragment;
    private TextView headerText;
    private ImageView mainHeaderMakeView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setElevation(0);
        }
        initView();
        adjustHeaderForStatusBar(); // 추가된 코드
        initBottomNavigation();
        initNaverMapSdk();
        initFragments();
        // 처음 시작할 때 모든 프래그먼트를 추가하고 숨김
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frm_js, homeFragment, "homeFragment")
                .add(R.id.main_frm_js, structureFragment, "structureFragment")
                .add(R.id.main_frm_js, chatFragment, "chatFragment")
                .add(R.id.main_frm_js, subscribeFragment, "subscribeFragment")
                .add(R.id.main_frm_js, myPageFragment, "myPageFragment")
                .hide(structureFragment)
                .hide(chatFragment)
                .hide(subscribeFragment)
                .hide(myPageFragment)
                .commit();
    }

    private void initFragments() {
        homeFragment = new HomeFragment();
        structureFragment = new StructureFragment();
        chatFragment = new ChatFragment();
        subscribeFragment = new SubscribeFragment();
        myPageFragment = new MyPageFragment();
    }

    private void initView(){
        headerLayout = findViewById(R.id.main_header_js);
        headerText = findViewById(R.id.main_header_text_js);
        mainHeaderMakeView = findViewById(R.id.main_header_make);
    }

    private void initNaverMapSdk() {
        String clientId = getResources().getString(R.string.NAVER_CLIENT_ID);
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient(clientId));
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv_js); // 처음화면
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem item){
        Fragment targetFragment;
        switch (item.getItemId()) {
            case R.id.homeFragment:
                targetFragment = homeFragment;
                setHeaderProperties("울산광역시 무거동", Color.WHITE, View.INVISIBLE,false);
                break;
            case R.id.structureFragment:
                targetFragment = structureFragment;
                setHeaderProperties("매물 목록", getTranslucentColor(), View.VISIBLE,true);
                break;
            case R.id.subscribeFragment:
                targetFragment = subscribeFragment;
                setHeaderProperties("찜 목록", Color.WHITE, View.INVISIBLE,false);
                break;
            case R.id.chatFragment:
                targetFragment = chatFragment;
                setHeaderProperties("채팅 목록", Color.WHITE, View.INVISIBLE,false);
                break;
            case R.id.myPageFragment:
                targetFragment = myPageFragment;
                setHeaderProperties("내 정보", Color.WHITE, View.INVISIBLE,false);
                break;
            default:
                targetFragment = homeFragment;
        }
        switchFragment(targetFragment);
        resetHeaderPadding(item.getItemId());
        return true;
    }
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (homeFragment.isVisible()) {
            transaction.hide(homeFragment);
        }
        if (structureFragment.isVisible()) {
            transaction.hide(structureFragment);
        }
        if (chatFragment.isVisible()) {
            transaction.hide(chatFragment);
        }
        if (subscribeFragment.isVisible()) {
            transaction.hide(subscribeFragment);
        }
        if (myPageFragment.isVisible()) {
            transaction.hide(myPageFragment);
        }

        transaction.show(targetFragment).commit();
    }
    private void setHeaderProperties(String title, int color, int visibility, boolean adjustForStatusBar) {
        headerText.setText(title);
        headerLayout.setBackgroundColor(color);
        mainHeaderMakeView.setVisibility(visibility);

        if(adjustForStatusBar) {
            adjustHeaderForStatusBar();
        } else {
            resetHeaderPadding();
        }
    }

    private void resetHeaderPadding() {
        headerLayout.setPadding(headerLayout.getPaddingLeft(), 0,
                headerLayout.getPaddingRight(), headerLayout.getPaddingBottom());
    }

    private int getTranslucentColor() {
        int currentColor = getResources().getColor(R.color.white);
        int alphaValue = (int) (255 * 0.7499);
        return (alphaValue << 24) | (currentColor & 0x00FFFFFF);
    }

    private void adjustHeaderForStatusBar() {
        int statusBarHeight = getStatusBarHeight();
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) headerLayout.getLayoutParams();
        params.topMargin = statusBarHeight;
        headerLayout.setLayoutParams(params);
    }

    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    private void resetHeaderPadding(int itemId) {
        if(itemId != R.id.structureFragment) {
            headerLayout.setPadding(headerLayout.getPaddingLeft(), 0,
                    headerLayout.getPaddingRight(), headerLayout.getPaddingBottom());
        }
    }
}