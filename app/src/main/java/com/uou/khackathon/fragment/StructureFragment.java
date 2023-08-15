package com.uou.khackathon.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.uou.khackathon.R;

/**
 * onRequestPermissionsResult -> 이 함수 Deprecated 되었음, 쓰면 안됨.
 */
public class StructureFragment extends Fragment implements OnMapReadyCallback {
    // 상태바 설정에 사용될 플래그
    private static final int STATUS_BAR_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

    private NaverMap naverMap;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View inflate
        View rootView = inflater.inflate(R.layout.fragment_structure, container, false);

        // Naver Map Fragment
        setupMapFragment();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 권한 요청 결과를 처리하는 콜백 설정
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        setupLocationTracking(naverMap);
                    } else {
                        Toast.makeText(getContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
        // FusedLocationProviderClient 초기화
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    // 지도 프래그먼트 설정
    private void setupMapFragment() {
        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setStatusBarTransparent(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setStatusBarTransparent(false);
    }

    // 상태바 투명 설정
    private void setStatusBarTransparent(boolean transparent) {
        if (getActivity() == null) return;

        Window window = getActivity().getWindow();
        if (transparent) {
            makeStatusBarTransparent(window);
        } else {
            revertStatusBar(window);
        }
    }

    // 상태바 투명
    private void makeStatusBarTransparent(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(STATUS_BAR_FLAGS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams flags = window.getAttributes();
            flags.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            window.setAttributes(flags);
        }
    }

    // 상태바의 원래 색상으로 되돌림
    private void revertStatusBar(Window window) {
        window.setStatusBarColor(Color.WHITE);
        window.getDecorView().setSystemUiVisibility(STATUS_BAR_FLAGS);
    }

    // 지도 콜백
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        if (naverMap == null) {
            // 지도 객체가 null인 경우
            Log.e("StructureFragment", "Error: NaverMap is null");

            if (getContext() != null) {
                Toast.makeText(getContext(), "지도를 로드하는데 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }

            return;
        }
        this.naverMap = naverMap;

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            setupLocationTracking(naverMap);
            fetchCurrentLocation(naverMap);
        }
        naverMap.getUiSettings().setZoomControlEnabled(false);
    }

    private void setupLocationTracking(NaverMap naverMap) {
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }

    private void fetchCurrentLocation(NaverMap naverMap) {

        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), location -> {
                        if (location != null) {
                            com.naver.maps.geometry.LatLng currentLatLng =
                                    new com.naver.maps.geometry.LatLng(location.getLatitude(), location.getLongitude());
                            naverMap.setCameraPosition(new CameraPosition(currentLatLng, 16));
                        }
                    });
        } catch (SecurityException e) {
            Log.e("StructureFragment", "SecurityException while fetching location: " + e.getMessage());
        }
    }
}