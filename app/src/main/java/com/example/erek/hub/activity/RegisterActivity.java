package com.example.erek.hub.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.erek.hub.App;
import com.example.erek.hub.R;
import com.example.erek.hub.entity.DaoSession;
import com.example.erek.hub.entity.User;
import com.example.erek.hub.entity.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.text_phone_number)
    TextInputLayout mUserNameLayout;
    @BindView(R.id.text_password)
    TextInputLayout mPwdLayout;
    @BindView(R.id.text_password_confirm)
    TextInputLayout mPwdConfirmLayout;
    @BindView(R.id.register_button)
    Button mRegisterButton;

    private UserDao userDao;
    private List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setTitle("Register");

        DaoSession daoSession = ((App)getApplication()).getDaoSession();
        userDao = daoSession.getUserDao();



        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mUserNameLayout.getEditText().getText().toString();
                String pwd = mPwdLayout.getEditText().getText().toString();
                String pwdConfirm = mPwdConfirmLayout.getEditText().getText().toString();

                mUserNameLayout.setErrorEnabled(false);
                mPwdConfirmLayout.setErrorEnabled(false);
                mPwdLayout.setErrorEnabled(false);

                //检查用户名是否重复
                if (validateAccount(account) && validatePassword(pwd) && confirmPassword(pwd,pwdConfirm)){

                    User user = new User();
                    user.setName(account);
                    user.setPassword(pwd);
                    userDao.insert(user);
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }
            }
        });

    }


    public boolean confirmPassword(String password,String confirmPassword){
        if (!TextUtils.equals(password,confirmPassword)){
            showError(mPwdConfirmLayout,"密码不一致");
            return false;
        }
        return true;
    }

    //显示错误提示信息
    public void showError(TextInputLayout textInputLayout, String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();

    }

    //用户名验证
    public boolean validateAccount(String account){
        if (TextUtils.isEmpty(account)){
            showError(mUserNameLayout,"用户名不能为空");
            return false;
        }
        //检查用户名是否存在
        users = userDao.queryRaw("where name = ?",account);
        if (!users.isEmpty()){
            showError(mUserNameLayout,"用户名已存在");
            return false;
        }

        return true;
    }

    //密码验证
    public boolean validatePassword(String password){
        if (TextUtils.isEmpty(password)){
            showError(mPwdLayout,"密码不能为空");
            return false;
        }
        if(password.length()<6 || password.length()>18){
            showError(mPwdLayout,"密码需要6-18位");
            return false;
        }
        return true;
    }


}
