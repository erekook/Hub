package com.example.erek.hub.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.erek.hub.App;
import com.example.erek.hub.R;
import com.example.erek.hub.entity.DaoSession;
import com.example.erek.hub.entity.User;
import com.example.erek.hub.entity.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by erek on 2018/1/11
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.lo_phone_number)
    TextInputLayout mNameLayout;
    @BindView(R.id.lo_password)
    TextInputLayout mPasswordLayout;
    @BindView(R.id.loet_password)
    TextInputEditText mPasswordText;
    @BindView(R.id.register_text)
    TextView mRegisterText;
    @BindView(R.id.login_button)
    Button mLoginButton;

    private UserDao userDao;
    List<User> users;
    private boolean visible=false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DaoSession session = ((App)getApplication()).getDaoSession();
        userDao = session.getUserDao();

        mToolbar.setTitle("Login");
        final Drawable drawable = mPasswordText.getCompoundDrawables()[2];
        mPasswordText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (drawable == null){
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_UP
                        && event.getX() >= (mPasswordText.getWidth() - drawable.getBounds().width() - mPasswordText.getPaddingRight())
                        && event.getX() <= (mPasswordText.getWidth() - mPasswordText.getPaddingRight())){

                    visible = !visible;
                    int visible_password = visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD;
                    Drawable drawableRight = visible ? getResources().getDrawable(R.mipmap.ic_show_password) : getResources().getDrawable(R.mipmap.ic_not_show_password);
                    drawableRight.setBounds(0,0,drawableRight.getMinimumWidth(),drawableRight.getMinimumHeight());
                    mPasswordText.setInputType(InputType.TYPE_CLASS_TEXT | visible_password);
                    mPasswordText.setCompoundDrawables(null,null,drawableRight,null);
                    return true;
                }

                return false;
            }
        });
        mRegisterText.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);


    }

    public void login(){
        String account = mNameLayout.getEditText().getText().toString();
        String pwd = mPasswordText.getText().toString();

        mNameLayout.setErrorEnabled(false);
        mPasswordLayout.setErrorEnabled(false);

        users = userDao.queryRaw("where name = ?",account);
        if (users.isEmpty()){
            showError(mNameLayout,"用户名不存在");
        }else {
            for (User user:users){
                if(TextUtils.equals(pwd,user.getPassword())){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else {
                    showError(mPasswordLayout,"密码错误");
                }
            }
        }
    }

    public void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_text:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.login_button:
                login();
                break;
        }
    }
}
