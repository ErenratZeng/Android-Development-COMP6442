package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AccountCheckActivity extends AppCompatActivity {

    private Button btnYes;
    private Button btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_check);

        btnYes = (Button) findViewById(R.id.btnYes);  // 强制类型转换确保正确处理
        btnNo = (Button) findViewById(R.id.btnNo);

        if (btnYes == null || btnNo == null) {
            throw new RuntimeException("Buttons not found. Make sure the IDs are correct in the layout.");
        }

        // 设置视图以应对窗口插入，如状态栏或导航栏
        View mainLayout = findViewById(R.id.main);  // 确保你的 layout 中有一个 id 为 main 的 View
        if (mainLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainLayout, (view, insets) -> {
                int left = insets.getSystemWindowInsetLeft();
                int top = insets.getSystemWindowInsetTop();
                int right = insets.getSystemWindowInsetRight();
                int bottom = insets.getSystemWindowInsetBottom();
                view.setPadding(left, top, right, bottom);
                return insets.replaceSystemWindowInsets(0, 0, 0, 0);  // 这将处理并消耗窗口插入
            });
        } else {
            throw new RuntimeException("Main layout not found. Make sure the layout and ID are correct.");
        }

        btnYes.setOnClickListener(v -> {
            startActivity(new Intent(AccountCheckActivity.this, loginActivity.class));  // 确保 LoginActivity 是正确的类名
        });

        btnNo.setOnClickListener(v -> {
            startActivity(new Intent(AccountCheckActivity.this, registerActivity.class));  // 启动注册活动，确保 RegisterActivity 是正确的类名
        });
    }
}

