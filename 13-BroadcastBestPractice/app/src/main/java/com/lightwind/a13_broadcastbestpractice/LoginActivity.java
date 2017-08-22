package com.lightwind.a13_broadcastbestpractice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登陆界面
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.account)
    EditText mAccount;
    @BindView(R.id.password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login)
    public void onViewClicked() {
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        if (account.equals("admin") && password.equals("123456")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "account or password is invalid", Toast.LENGTH_SHORT).show();
        }
    }
}
