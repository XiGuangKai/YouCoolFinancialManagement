package com.example.financialmanagement.activity;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.UsernamePwd;
import com.example.financialmanagement.database.UsernamePwdCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TextURLView;
import com.example.financialmanagement.view.TitleBarView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterNewUserActivity extends AppCompatActivity {

    private static final String TAG = "RegisterNewUserActivity";
    private Context mContext;
    private TitleBarView mTitleBarView;
    private TextURLView mTextViewURL;
    private TextView mRedmineInfo;
    private Button mNextStep;
    private CheckBox mCheckboxAgreen;
    private EditText mRegisterUserName;
    private EditText mRegisterUserPassword;
    private EditText mConfirmRegisterUserPassword;
    private UsernamePwd mUsernamePwd;
    private UsernamePwdCRUD mUsernamePwdCRUD;
    private boolean userNameFlag = false;
    private boolean passwordFlag = false;
    private boolean passwordConfirmFlag = false;
    private boolean checkBoxFlag = true;
    private boolean isPasswordSame = false;
    private String savePassword1 = "";
    private String savePassword2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_newuser);
        mContext = this;
        findView();
        initTitleView();
        initTvUrl();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView() {
        mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
        mTextViewURL = (TextURLView) findViewById(R.id.tv_url);
        mNextStep = (Button) findViewById(R.id.btn_next);
        mCheckboxAgreen = (CheckBox)findViewById(R.id.checkbox_agreen);
        mRegisterUserName = (EditText)findViewById(R.id.et_register_username);
        mRegisterUserPassword = (EditText)findViewById(R.id.et_register_userpassword);
        mConfirmRegisterUserPassword = (EditText)findViewById(R.id.et_confirm_register_userpassword);
        mRedmineInfo = (TextView)findViewById(R.id.tv_redmine_info);
        DebugLog.d(TAG,"findView() finished");
    }

    private void init() {
        mCheckboxAgreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果不同意协议内容，不允许进行创建用户
                if (mCheckboxAgreen.isChecked()){
                    checkBoxFlag = true;
                    DebugLog.d(TAG,"agree the protocol to set the next step button as true");
                    setNextStepEnable(true);
                }else{
                    DebugLog.d(TAG,"don't agree the protocol to set the next step button as false");
                    checkBoxFlag = false;
                    setNextStepEnable(false);
                }
            }
        });

        mRegisterUserName.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() <= 18){
                    userNameFlag = true;
                    mRegisterUserName.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                    DebugLog.d(TAG,"onTextChanged set mNextStep true");
                    setNextStepEnable(true);
                }else{
                    userNameFlag = false;
                    mRegisterUserName.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
                    DebugLog.d(TAG,"onTextChanged set mNextStep false");
                    setNextStepEnable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { //屏蔽回车 中英文空格
            }
        });

        mRegisterUserPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() <= 18){
                    passwordFlag = true;
                    mRegisterUserPassword.setTextColor(ContextCompat.getColor(mContext,R.color.green));
                    savePassword1 = s.toString();
                    DebugLog.d(TAG,"onTextChanged set mNextStep true, savePassword1 = " + savePassword1);
                    setNextStepEnable(true);
                }else{
                    passwordFlag = false;
                    mRegisterUserPassword.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
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
                    mRedmineInfo.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
                    mRedmineInfo.setVisibility(View.VISIBLE);
                    mRedmineInfo.setText(R.string.toast_diff_confirm_register_userpassword);
                    DebugLog.d(TAG,"The password is different");
                    isPasswordSame = false;
                    setNextStepEnable(false);
                }
            }
        });

        mConfirmRegisterUserPassword.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 6 && s.length() <= 18){
                    passwordConfirmFlag = true;
                    savePassword2 = s.toString();
                    DebugLog.d(TAG,"onTextChanged set mNextStep true, savePassword2 = " + savePassword2);
                    mConfirmRegisterUserPassword.setTextColor(ContextCompat.getColor(mContext,R.color.green));
                    setNextStepEnable(true);
                }else{
                    passwordConfirmFlag = false;
                    mConfirmRegisterUserPassword.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
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
                    mRedmineInfo.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
                    mRedmineInfo.setVisibility(View.VISIBLE);
                    mRedmineInfo.setText(R.string.toast_diff_confirm_register_userpassword);
                    isPasswordSame = false;
                    setNextStepEnable(false);
                }
            }
        });

        mNextStep.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String newUserName = mRegisterUserName.getText().toString();
                String newUserPassword = mRegisterUserPassword.getText().toString();
                String newConfirmUserPassword = mConfirmRegisterUserPassword.getText().toString();
                //判断用户名、密码的合法性
                if (newUserName.isEmpty() && newUserPassword.isEmpty() && newConfirmUserPassword.isEmpty()){
                    Toast.makeText(mContext,R.string.title_registerInformation,Toast.LENGTH_SHORT).show();
                    DebugLog.d(TAG,"Please the register information empty");
                    return;
                }
                if (newUserName.isEmpty()){
                    Toast.makeText(mContext,R.string.et_register_username,Toast.LENGTH_SHORT).show();
                    DebugLog.d(TAG,"The register user name is empty");
                    return;
                }else if (newUserPassword.isEmpty()){
                    Toast.makeText(mContext,R.string.et_register_userpassword,Toast.LENGTH_SHORT).show();
                    DebugLog.d(TAG,"The register user password is empty");
                    return;
                }else if (newConfirmUserPassword.isEmpty()){
                    Toast.makeText(mContext,R.string.et_confirm_register_userpassword,Toast.LENGTH_SHORT).show();
                    DebugLog.d(TAG,"The register confirm user password is empty");
                    return;
                }
                if (!newUserPassword.equals(newConfirmUserPassword)){
                    Toast.makeText(mContext,R.string.toast_diff_confirm_register_userpassword,Toast.LENGTH_SHORT).show();
                    mConfirmRegisterUserPassword.setText("");
                    mRegisterUserPassword.setText("");
                    DebugLog.d(TAG,"The register confirm user password and user password is different");
                    return;
                }

                if (UsernamePwdCRUD.checkDataBase(newUserName)){
                    Toast.makeText(mContext,R.string.toast_db_been_builded,Toast.LENGTH_SHORT).show();
                    DebugLog.d(TAG,"The " + newUserName + ".db has been created");
                    return;
                }

                DebugLog.d(TAG,"Register the new user and create the "+ newUserName + ".db");
                //1.创建数据库存储上述内容
                mUsernamePwdCRUD = new UsernamePwdCRUD(mContext,newUserName,FinanceManageActivity.DB_VERSION);
                mUsernamePwd = new UsernamePwd(newUserPassword,newUserName);
                mUsernamePwdCRUD.registerUsernamePwd(mUsernamePwd);

                //2.开启密码保护问题页面
                Intent mPasswordSecurityActivityIntent = new Intent(mContext,PasswordSecurityActivity.class);
                mPasswordSecurityActivityIntent.putExtra("dbname",newUserName);
                startActivity(mPasswordSecurityActivityIntent);
                DebugLog.d(TAG,"Start PasswordSecurityActivity finished");
            }
        });
    }

    private void initTitleView() {
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
        mTitleBarView.setTitleText(R.string.title_registerInformation);
        mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DebugLog.d(TAG,"initTitleView() finished");
    }

    private void initTvUrl() {
        mTextViewURL.setText(R.string.tv_xieyi_url);
        DebugLog.d(TAG,"initTvUrl() finished");
    }

    private void setNextStepEnable(boolean NextStepFlag){
        StringBuilder sb = new StringBuilder()
                .append("NextStepFlag = ").append(NextStepFlag)
                .append(", userNameFlag = ").append(userNameFlag)
                .append(", passwordFlag = ").append(passwordFlag)
                .append(", passwordConfirmFlag = ").append(passwordConfirmFlag)
                .append(", checkBoxFlag = ").append(checkBoxFlag)
                .append(", isPasswordSame = ").append(isPasswordSame);

        DebugLog.d(TAG,sb.toString());

        if (NextStepFlag && userNameFlag && passwordFlag && passwordConfirmFlag && checkBoxFlag && isPasswordSame){
            DebugLog.d(TAG,"setNextStepEnable true");
            mNextStep.setEnabled(true);
        }else{
            DebugLog.d(TAG,"setNextStepEnable false");
            mNextStep.setEnabled(false);
        }
    }
}
