package com.uou.khackathon.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naver.maps.map.NaverMapSdk;
import com.uou.khackathon.R;
import com.uou.khackathon.fragment.ChatFragment;
import com.uou.khackathon.fragment.HomeFragment;
import com.uou.khackathon.fragment.MyPageFragment;
import com.uou.khackathon.fragment.StructureFragment;
import com.uou.khackathon.fragment.SubscribeFragment;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout headerLayout;
    private ConstraintLayout topBarLayout;
    private HomeFragment homeFragment;
    private StructureFragment structureFragment;
    private ChatFragment chatFragment;
    private SubscribeFragment subscribeFragment;
    private MyPageFragment myPageFragment;
    private TextView headerText;
    private ImageView mainHeaderMakeView;
    private int originalHeaderHeight;

    private static final double TRANSLUCENT_ALPHA = 0.81;
    private Fragment currentFragment;  // 현재 보이는 프래그먼트를 추적

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 그림자 제거
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setElevation(0);
        }
        // 뷰, 네비게이션, 맵 SDK 및 프래그먼트 초기화
        initView();
        initBottomNavigation();
        initNaverMapSdk();
        initFragments();
        showInitialFragments();
    }

    private void showInitialFragments() {
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
        currentFragment = homeFragment;
    }

    // 프래그먼트 초기화
    private void initFragments() {
        homeFragment = new HomeFragment();
        structureFragment = new StructureFragment();
        chatFragment = new ChatFragment();
        subscribeFragment = new SubscribeFragment();
        myPageFragment = new MyPageFragment();
    }

    // 뷰 초기화 및 높이 설정
    private void initView() {
        topBarLayout = findViewById(R.id.top_bar);
        headerLayout = findViewById(R.id.main_header_js);
        headerText = findViewById(R.id.main_header_text_js);
        mainHeaderMakeView = findViewById(R.id.main_header_make);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) headerLayout.getLayoutParams();
        originalHeaderHeight = headerLayout.getLayoutParams().height; // 여기서 원래 높이 저장
        params.height = originalHeaderHeight + getStatusBarHeight();
        headerLayout.setPadding(headerLayout.getPaddingLeft(), getStatusBarHeight(),
                headerLayout.getPaddingRight(), headerLayout.getPaddingBottom());
        headerLayout.setLayoutParams(params);
    }

    // 네이버 맵 SDK 초기화
    private void initNaverMapSdk() {
        String clientId = getResources().getString(R.string.NAVER_CLIENT_ID);
        if (clientId == null || clientId.isEmpty()) { // Naver Map Api 실행 오류 시
            new AlertDialog.Builder(this)
                    .setTitle("오류")
                    .setMessage("Naver Map SDK 초기화에 실패했습니다. 앱을 다시 시작해주세요.")
                    .setPositiveButton("확인", (dialog, which) -> {
                        // 앱 종료 또는 다른 액션
                        finish();
                    })
                    .show();
            return;
        }
        NaverMapSdk.getInstance(this).setClient(
                new NaverMapSdk.NaverCloudPlatformClient(clientId));
    }

    // 하단 네비게이션 초기화 및 리스너 설정
    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bnv_js);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    // 네비게이션 아이템 선택 시 프래그먼트 전환 및 헤더 속성 변경
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment targetFragment;
        switch (item.getItemId()) {
            case R.id.homeFragment:
                targetFragment = homeFragment;
                setHeaderProperties("울산광역시 무거동", Color.WHITE, View.INVISIBLE);
                setTopBarProperties(View.GONE);
                break;
            case R.id.structureFragment:
                targetFragment = structureFragment;
                setHeaderProperties("매물 목록", getTranslucentColor(), View.VISIBLE);
                setTopBarProperties(View.VISIBLE);
                break;
            case R.id.subscribeFragment:
                targetFragment = subscribeFragment;
                setHeaderProperties("찜 목록", Color.WHITE, View.INVISIBLE);
                setTopBarProperties(View.GONE);
                break;
            case R.id.chatFragment:
                targetFragment = chatFragment;
                setHeaderProperties("채팅 목록", Color.WHITE, View.INVISIBLE);
                setTopBarProperties(View.GONE);
                break;
            case R.id.myPageFragment:
                targetFragment = myPageFragment;
                setHeaderProperties("내 정보", Color.WHITE, View.INVISIBLE);
                setTopBarProperties(View.GONE);
                break;
            default:
                targetFragment = homeFragment;
        }
        switchFragment(targetFragment);
        return true;
    }

    // 활성화된 프래그먼트를 숨기고 선택된 프래그먼트를 보여줌
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.show(targetFragment).commit();
        currentFragment = targetFragment;
    }

    // 헤더의 제목, 배경색, 가시성 및 상태바에 대한 높이 조절 설정
    private void setHeaderProperties(String title, int color, int visibility) {
        headerText.setText(title);
        headerLayout.setBackgroundColor(color);
        mainHeaderMakeView.setVisibility(visibility);
        adjustHeaderForStatusBar();
    }

    private void setTopBarProperties(int visibility) {
        topBarLayout.setBackgroundColor(getTranslucentColor());
        topBarLayout.setVisibility(visibility);
    }

    // 81% 투명도의 색을 반환
    private int getTranslucentColor() {
        int currentColor;
        try {
            currentColor = getResources().getColor(R.color.white);
        } catch (Resources.NotFoundException e) {
            currentColor = Color.WHITE;  // 기본 색상으로 catch
        }
        int alphaValue = (int) (255 * TRANSLUCENT_ALPHA);
        return (alphaValue << 24) | (currentColor & 0x00FFFFFF);
    }

    // 상태바의 높이 고려해서 헤더 높이 조절
    private void adjustHeaderForStatusBar() {
        int statusBarHeight = getStatusBarHeight();
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) headerLayout.getLayoutParams();
        params.height = originalHeaderHeight + statusBarHeight;
        headerLayout.setPadding(headerLayout.getPaddingLeft(), statusBarHeight,
                headerLayout.getPaddingRight(), headerLayout.getPaddingBottom());
        headerLayout.setLayoutParams(params);
    }

    // 상태바 높이 가져옴
    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}