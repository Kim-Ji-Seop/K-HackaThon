package com.uou.khackathon.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.uou.khackathon.R;
import android.os.Bundle;
import android.view.View;

public class StructureRegisterActivity extends AppCompatActivity {
    private AppCompatButton[] innerOptions;
    private AppCompatButton[] outerOptions;
    private AppCompatButton registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure_register);
        initOptionButton();
        initView();
    }
    private void initView(){
        registerButton = findViewById(R.id.structure_register_btn_js);
        // 처음 상태는 불가능한 상태로 설정
        setButtonEnabled(false);

    }

    private void setButtonEnabled(boolean isEnabled) {
        registerButton.setEnabled(isEnabled);
        if (isEnabled) {
            // 등록 가능한 상태의 스타일 적용
            registerButton.setTextAppearance(this, R.style.structure_register_btn_enabled_js);
        } else {
            // 등록 불가능한 상태의 스타일 적용
            registerButton.setTextAppearance(this, R.style.structure_register_btn_disabled_js);
        }
    }

    private void initOptionButton(){
        innerOptions = new AppCompatButton[14];
        outerOptions = new AppCompatButton[14];

        for (int i = 0; i < innerOptions.length; i++) {
            String buttonID = "option_text_" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            innerOptions[i] = findViewById(resID);
            // 처음 상태 설정
            setInitialBackground(innerOptions[i]);

            // 클릭 리스너 설정
            innerOptions[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButtonState((AppCompatButton) v);
                }
            });
        }

        for (int i = 0; i < outerOptions.length; i++) {
            String buttonID = "option_text_outer_" + (i + 1);
            int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
            outerOptions[i] = findViewById(resID);
            // 처음 상태 설정
            setInitialBackground(outerOptions[i]);

            // 클릭 리스너 설정
            outerOptions[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleButtonState((AppCompatButton) v);
                }
            });
        }
    }
    private void setInitialBackground(AppCompatButton button) {
        if (button.getText().length() > 2) {
            button.setBackgroundResource(R.drawable.option_background_no_selected);
        } else {
            button.setBackgroundResource(R.drawable.option_background_no_selected_small);
        }
    }

    private void toggleButtonState(AppCompatButton button) {
        boolean isSelected = button.isSelected();

        if (button.getText().length() > 2) {
            if (isSelected) {
                button.setBackgroundResource(R.drawable.option_background_no_selected);
            } else {
                button.setBackgroundResource(R.drawable.option_background_selected);
            }
        } else {
            if (isSelected) {
                button.setBackgroundResource(R.drawable.option_background_no_selected_small);
            } else {
                button.setBackgroundResource(R.drawable.option_background_selected_small);
            }
        }

        button.setSelected(!isSelected);
    }

}