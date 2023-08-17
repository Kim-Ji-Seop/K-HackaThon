package com.uou.khackathon.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.uou.khackathon.R;
import com.uou.khackathon.activity.MainActivity;


public class StructureListFragment extends Fragment {

    private static final int STATUS_BAR_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_structure_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarWhite();
        // MainActivity의 메서드를 호출
        if (getActivity() instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.setHeaderProperties("매물 목록", Color.WHITE, View.VISIBLE);
            mainActivity.setTopBarProperties(Color.WHITE,View.VISIBLE);
        }
    }

    // 상태바 투명 설정
    private void setStatusBarWhite() {
        if (getActivity() == null) return;

        Window window = getActivity().getWindow();
        revertStatusBar(window);
    }

    // 상태바의 흰색으로 되돌림
    private void revertStatusBar(Window window) {
        window.setStatusBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(STATUS_BAR_FLAGS);
    }
}