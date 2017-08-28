package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.UsernamePwd;
import com.example.financialmanagement.database.UsernamePwdCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

public class SystemSetActivity extends AppCompatActivity {

    private static final String TAG = "SystemSetActivity";
    private EditText oldPassword, newPassword,confirmNewPassword;// 创建EditText对象
    private TextView mRedmineInfo;
    private Button btnChangePasword;// 创建两个Button对象

    private Context mContext;
    private TitleBarView mTitleBarView;
    private UsernamePwdCRUD mUsernamePwdCRUD;
    private boolean passwordFlag = false;
    private boolean passwordConfirmFlag = false;
    private boolean isPasswordSame = false;
    private boolean isOldNewPasswordSame = false;
    private String savePassword1 = "";
    private String savePassword2 = "";
    private String saveOldPassword = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_set);
        mContext = this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        oldPassword = (EditText) findViewById(R.id.et_old_password);// 获取密码文本框
        newPassword = (EditText) findViewById(R.id.et_new_password);// 获取密码文本框
        confirmNewPassword = (EditText) findViewById(R.id.et_confirm_new_password);// 获取密码文本框
        btnChangePasword = (Button) findViewById(R.id.btn_change_password);// 获取取消按钮
        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mRedmineInfo = (TextView)findViewById(R.id.tv_change_password_redmine_info);
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTitleView() {
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
        mTitleBarView.setTitleText(R.string.title_change_password);
        mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清空密码文本框
                oldPassword.setText("");
                // 为密码文本框设置提示
                oldPassword.setHint(R.string.et_old_pasword);

                // 清空密码文本框
                newPassword.setText("");
                // 为密码文本框设置提示
                newPassword.setHint(R.string.et_new_password);

                // 清空密码文本框
                confirmNewPassword.setText("");
                // 为密码文本框设置提示
                confirmNewPassword.setHint(R.string.et_confirm_new_password);

                finish();
                DebugLog.d(TAG,"cancel change the password");
            }
        });
        DebugLog.d(TAG,"initTitleView() finished");
    }

    private void init(){
        oldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                saveOldPassword = s.toString();
                DebugLog.d(TAG,"The old password = " + saveOldPassword);
            }
        });

        newPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() <= 18){
                    passwordFlag = true;
                    newPassword.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    savePassword1 = s.toString();
                    DebugLog.d(TAG,"onTextChanged set mNextStep true, savePassword1 = " + savePassword1);
                    setNextStepEnable(true);
                }else{
                    passwordFlag = false;
                    newPassword.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    DebugLog.d(TAG,"onTextChanged set mNextStep false");
                    setNextStepEnable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { //屏蔽回车 中英文空格
                if (passwordConfirmFlag && passwordFlag && savePassword1.equals(savePassword2)){
                    DebugLog.d(TAG,"afterTextChanged password is same");
                    isPasswordSame = true;
                    mRedmineInfo.setVisibility(View.GONE);
                    setNextStepEnable(true);
                }else if (passwordConfirmFlag && passwordFlag && !(savePassword1.equals(savePassword2))){
                    mRedmineInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    mRedmineInfo.setVisibility(View.VISIBLE);
                    mRedmineInfo.setText(R.string.toast_diff_confirm_register_userpassword);
                    DebugLog.d(TAG,"The password is different");
                    isPasswordSame = false;
                    setNextStepEnable(false);
                    return;
                }

                if (passwordFlag && saveOldPassword.equals(savePassword1)){
                    DebugLog.d(TAG,"The old and new password is same");
                    mRedmineInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    mRedmineInfo.setVisibility(View.VISIBLE);
                    mRedmineInfo.setText(R.string.tv_old_new_password_same);
                    isOldNewPasswordSame = false;
                    setNextStepEnable(false);
                }else if (passwordFlag && !(saveOldPassword.equals(savePassword1))){
                    DebugLog.d(TAG,"The old and new password is not same");
                    mRedmineInfo.setVisibility(View.GONE);
                    isOldNewPasswordSame = true;
                    setNextStepEnable(true);
                }
            }
        });

        confirmNewPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() <= 18){
                    passwordConfirmFlag = true;
                    savePassword2 = s.toString();
                    DebugLog.d(TAG,"onTextChanged set mNextStep true, savePassword2 = " + savePassword2);
                    confirmNewPassword.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    setNextStepEnable(true);
                }else{
                    passwordConfirmFlag = false;
                    confirmNewPassword.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    DebugLog.d(TAG,"onTextChanged set mNextStep false");
                    setNextStepEnable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { //屏蔽回车 中英文空格
                if (passwordConfirmFlag && passwordFlag && savePassword1.equals(savePassword2)){
                    DebugLog.d(TAG,"afterTextChanged password is same");
                    isPasswordSame = true;
                    mRedmineInfo.setVisibility(View.GONE);
                    setNextStepEnable(true);
                }else if (passwordConfirmFlag && passwordFlag && !(savePassword1.equals(savePassword2))){
                    DebugLog.d(TAG,"The password is different");
                    mRedmineInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    mRedmineInfo.setVisibility(View.VISIBLE);
                    mRedmineInfo.setText(R.string.toast_diff_confirm_register_userpassword);
                    isPasswordSame = false;
                    setNextStepEnable(false);
                    return;
                }

                if (passwordConfirmFlag && saveOldPassword.equals(savePassword2)){
                    DebugLog.d(TAG,"The old and new password is same");
                    mRedmineInfo.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    mRedmineInfo.setVisibility(View.VISIBLE);
                    mRedmineInfo.setText(R.string.tv_old_new_password_same);
                    isOldNewPasswordSame = false;
                    setNextStepEnable(false);
                }else if (passwordConfirmFlag && !(saveOldPassword.equals(savePassword2))){
                    DebugLog.d(TAG,"The old and new password is not same");
                    mRedmineInfo.setVisibility(View.GONE);
                    isOldNewPasswordSame = true;
                    setNextStepEnable(true);
                }
            }
        });

        btnChangePasword.setOnClickListener(new OnClickListener() {// 为设置按钮添加监听事件
            @Override
            public void onClick(View arg0) {

                String mOldPassword = oldPassword.getText().toString();
                String mNewPassword = newPassword.getText().toString();
                String mConfirmNewPassword = confirmNewPassword.getText().toString();
                DebugLog.d(TAG, "old password = " + mOldPassword + " new password = " + mNewPassword + " confirm new password = " + mConfirmNewPassword);

                if (mOldPassword.isEmpty() || mNewPassword.isEmpty() || mConfirmNewPassword.isEmpty()){//新旧密码都不能为空
                    DebugLog.d(TAG,"password can not null");
                    Toast.makeText(mContext, R.string.toast_password_can_not_null, Toast.LENGTH_SHORT).show();
                    return;
                }else if (!mNewPassword.equals(mConfirmNewPassword)){//两次输入的新密码不一致，不能就行修改
                    DebugLog.d(TAG,"two new password can not same");
                    Toast.makeText(mContext, R.string.toast_diff_confirm_register_userpassword, Toast.LENGTH_SHORT).show();
                    return;
                }

                //获取数据库名字
                String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);

                // 创建mUsernamePwdCRUD对象
                mUsernamePwdCRUD = new UsernamePwdCRUD(mContext, mUserAndDbNameData, FinanceManageActivity.DB_VERSION);

                DebugLog.d(TAG, "mUserAndDbNameData = " + mUserAndDbNameData + " Database password = " + mUsernamePwdCRUD.findPassword() + " Input the old password = " + mOldPassword);
                if (!mUsernamePwdCRUD.findPassword().equals(mOldPassword)){//输入的旧密码与数据库中存储的旧密码不一致
                    DebugLog.d(TAG,"The old password is error");
                    Toast.makeText(mContext, R.string.toast_old_password_error, Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    DebugLog.d(TAG,"Change the password User = " + mUserAndDbNameData + " Password = " + mNewPassword);

                    // 根据输入的密码创建mUsernamePwd对象
                    UsernamePwd mUsernamePwd = new UsernamePwd(mNewPassword,mUserAndDbNameData);

                    // 修改用户密码
                    mUsernamePwdCRUD.update(mUsernamePwd);

                    DebugLog.d(TAG, "Change the password success");
                    FinanceManageActivity.showAlertDialogSingleNegativeButton(TAG,mContext,
                            getResources().getString(R.string.title_change_password),
                            getResources().getString(R.string.toast_set_password_success));
                }
           }
        });
        DebugLog.d(TAG,"init() finished");
    }

    private void setNextStepEnable(boolean NextStepFlag){
        StringBuilder sb = new StringBuilder()
                .append("NextStepFlag = ").append(NextStepFlag)
                .append(", passwordFlag = ").append(passwordFlag)
                .append(", passwordConfirmFlag = ").append(passwordConfirmFlag)
                .append(", isPasswordSame = ").append(isPasswordSame)
                .append(", isOldNewPasswordSame = ").append(isOldNewPasswordSame);

        DebugLog.d(TAG,sb.toString());

        if (NextStepFlag && passwordFlag && passwordConfirmFlag && isPasswordSame && isOldNewPasswordSame){
            DebugLog.d(TAG,"setNextStepEnable true");
            btnChangePasword.setEnabled(true);
        }else{
            DebugLog.d(TAG,"setNextStepEnable false");
            btnChangePasword.setEnabled(false);
        }
    }
}