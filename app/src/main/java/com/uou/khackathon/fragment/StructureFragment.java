package com.uou.khackathon.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.uou.khackathon.R;

public class StructureFragment extends Fragment implements OnMapReadyCallback {
    private NaverMap naverMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_structure, container, false);

        androidx.fragment.app.FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarTransparent(true); // 원래 상태로 되돌리기 위해 false 값을 전달
        //int currentColor = getResources().getColor(R.color.white); // 현재 색상 값 가져오기
        //int alphaValue = (int) (255 * 0.8); // 80% 투명도
        //int translucentColor = (alphaValue << 24) | (currentColor & 0x00FFFFFF); // 새로운 색상 값 계산

        //Window window = getActivity().getWindow();
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //    window.setStatusBarColor(0xCCFFFFFF);  // transparent
        //} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        //    int flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //    window.addFlags(flags);
        //}
        //window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


    }

    @Override
    public void onPause() {
        super.onPause();
        setStatusBarTransparent(false); // 원래 상태로 되돌리기 위해 false 값을 전달
    }

    private void setStatusBarTransparent(boolean transparent) {
        if (getActivity() == null) return;

        Window window = getActivity().getWindow();
        int currentColor = getResources().getColor(R.color.white); // 현재 색상 값 가져오기
        int alphaValue = (int) (255 * 0.50); // 80% 투명도
        int translucentColor = (alphaValue << 24) | (currentColor & 0x00FFFFFF); // 새로운 색상 값 계산
        if (transparent) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(translucentColor);

                // 글자 및 아이콘 색상을 어둡게 만듭니다.
                int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                window.getDecorView().setSystemUiVisibility(flags);

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams flags = window.getAttributes();
                flags.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                window.setAttributes(flags);
            }
        } else {
            // 원래의 상태바 색상 복원
            window.setStatusBarColor(Color.WHITE);


            // 원래의 글자 및 아이콘 색상 복원
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.getDecorView().setSystemUiVisibility(flags);
        }
    }
    // 색상이 어두운지 밝은지 판별하는 유틸리티 함수
    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
    }
}