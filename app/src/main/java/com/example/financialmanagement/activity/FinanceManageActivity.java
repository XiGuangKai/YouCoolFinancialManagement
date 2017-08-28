package com.example.financialmanagement.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.financialmanagement.database.DBUserData;
import com.example.financialmanagement.database.UsernamePwdCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TextURLView;
import com.example.financialmanagement.R;

public class FinanceManageActivity extends AppCompatActivity {

    private static final String TAG = "FinanceManageActivity";
    private Context mContext;
    private RelativeLayout rl_user;
    private Button mLogin;
    private Button register;
    private TextURLView mTextViewURL;
    private EditText mUserAccount;
    private EditText mUserPassword;
    private UsernamePwdCRUD mUsernamePwdCRUD;
    public static AlertDialog.Builder builderAlertDialogSingleNegativeButton;
    private AlertDialog.Builder builderAlertDialogDoubleButton;

    public static int DB_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_manage);
        mContext=this;
        findView();
        initTvUrl();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        rl_user=(RelativeLayout) findViewById(R.id.rl_user);
        mLogin=(Button) findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);
        mTextViewURL=(TextURLView) findViewById(R.id.tv_forget_password);
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTvUrl(){
        mTextViewURL.setText(R.string.forget_password);
        DebugLog.d(TAG,"initTvUrl() finished");
    }

    private void init(){
        Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
        anim.setFillAfter(true);
        rl_user.startAnimation(anim);

        mUserAccount = (EditText)findViewById(R.id.account);
        mUserPassword = (EditText)findViewById(R.id.password);

        mLogin.setOnClickListener(loginOnClickListener);
        register.setOnClickListener(registerOnClickListener);
        mTextViewURL.setOnClickListener(forgetPasswordOnClickListener);

        DebugLog.d(TAG,"init() finished");
    }

    private View.OnClickListener loginOnClickListener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String inputUserName = mUserAccount.getText().toString();
            String inputPassword = mUserPassword.getText().toString();
            DebugLog.d(TAG," user name = " + inputUserName + " user password = " + inputPassword);

            if(inputUserName.isEmpty() || inputPassword.isEmpty()){
                //判断账户或者密码是否为空，有一个为空，则提示用户重新输入
                DebugLog.d(TAG,"The user name and password was empty");
                Toast.makeText(mContext,R.string.toast_empty_account_password,Toast.LENGTH_SHORT).show();
                return;
            }else{
                //判断账户或者密码是否为空，不为空，则根据用户名查找对应的密码
                //然后判断存储的密码和用户输入的密码是否match，如果match则进行登录
                mUsernamePwdCRUD = new UsernamePwdCRUD(mContext,inputUserName,FinanceManageActivity.DB_VERSION);

                //如果该用户并未被注册，将会返回null,引起空指针异常。
                //此处首先查找该用户，如果该用户并未被注册过，则提醒用户先注册再使用
                if (mUsernamePwdCRUD == null || mUsernamePwdCRUD.find() == null){
                    //弹出提示框提示用户需要输入正确信息
                    showAlertDialogSingleNegativeButton(TAG,mContext,
                            getResources().getString(R.string.dialog_login_redmine),
                            getResources().getString(R.string.toast_not_exist_account_password));

                    DebugLog.d(TAG,"The input account not exit");
                    if (DBUserData.deleteDatabase(mContext,inputUserName)){
                        DebugLog.d(TAG,"Delete the not exit username database success");
                    }else{
                        DebugLog.e(TAG,"Delete the not exit username database failed");
                        showAlertDialogSingleNegativeButton(TAG,mContext,
                                getResources().getString(R.string.dialog_info_redmine),
                                getResources().getString(R.string.toast_delete_not_register_database_failed));
                    }
                    return;
                }

                //将用户名保存，以便后续使用
                setUserAndDbNameData(inputUserName);

                DebugLog.d(TAG,"The DataBase Password = " + mUsernamePwdCRUD.findPassword());

                if (inputPassword.equals(mUsernamePwdCRUD.findPassword())){

                    //密码匹配正确，开启详细功能界面
                    DebugLog.d(TAG,"User name and password match start to the main activity");
                    Intent mSpecificFunctionActivityIntent = new Intent(mContext,SpecificFunctionActivity.class);
                    startActivity(mSpecificFunctionActivityIntent);

                    //为了防止密码一直被默认记住。所以将界面记住的密码清除
                    mUserPassword.setText("");

                }else{
                    DebugLog.d(TAG,"Password not match");
                    showAlertDialogSingleNegativeButton(TAG,mContext,
                            getResources().getString(R.string.dialog_login_redmine),
                            getResources().getString(R.string.toast_empty_account_password));
                    return;
                }
            }
        }
    };

    private View.OnClickListener registerOnClickListener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //监听注册按钮，启动注册功能
            DebugLog.d(TAG,"Start the RegisterNewUserActivity");
            Intent mRegisterNewUserActivityIntent=new Intent(mContext, RegisterNewUserActivity.class);
            startActivity(mRegisterNewUserActivityIntent);
        }
    };

    private View.OnClickListener forgetPasswordOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DebugLog.d(TAG,"Start the forgetPasswordOnClickListener");
            //显示提醒对话框
            showAlertDialogDoubleButton(v);
        }
    };

    //显示基本Dialog
    private void showAlertDialogDoubleButton(View view) {
        builderAlertDialogDoubleButton=new AlertDialog.Builder(this);
        builderAlertDialogDoubleButton.setIcon(R.mipmap.ic_launcher);
        builderAlertDialogDoubleButton.setTitle(R.string.title_forget_password_appeals);
        builderAlertDialogDoubleButton.setMessage(R.string.dialog_forget_password_appeals);

        DebugLog.d(TAG,"Start the showForgetPasswordDialog");

        //监听下方button点击事件
        builderAlertDialogDoubleButton.setPositiveButton(R.string.btn_certain, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DebugLog.d(TAG,"forget password dialog start the confirm password activity");
                Intent intent = new Intent();
                intent.setClass(mContext,GetPasswordActivity.class);
                startActivity(intent);
            }
        });
        builderAlertDialogDoubleButton.setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DebugLog.d(TAG,"cancel forget password dialog");
            }
        });

        //设置对话框是可取消的
        builderAlertDialogDoubleButton.setCancelable(true);

        //创建dialog
        AlertDialog dialog=builderAlertDialogDoubleButton.create();
        dialog.show();
    }

    private void setUserAndDbNameData(String mUserAndDbName){
        DebugLog.d(TAG,"USER_NAME_DB_NAME = " + mUserAndDbName);
        //获取SharedPreferences对象
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences("FinanceManageSharedPreferences", MODE_PRIVATE);
        //存入数据
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("USER_NAME_DB_NAME", mUserAndDbName);
        editor.apply();
    }

    public static String getUserAndDbNameData(Context context){
        //获取SharedPreferences对象
        SharedPreferences mSharedPreferences = context.getSharedPreferences("FinanceManageSharedPreferences", MODE_PRIVATE);

        //读取数据
        String mUserAndDbName =  mSharedPreferences.getString("USER_NAME_DB_NAME", "Unknow");
        return mUserAndDbName;
    }

    public static void showAlertDialogSingleNegativeButton(final String mTag,Context context,String title,String message){
        DebugLog.d(mTag,"Start the Alert Single Negative Button dialog");
        builderAlertDialogSingleNegativeButton=new AlertDialog.Builder(context);
        builderAlertDialogSingleNegativeButton.setIcon(R.mipmap.ic_launcher);
        builderAlertDialogSingleNegativeButton.setTitle(title);
        builderAlertDialogSingleNegativeButton.setMessage(message);

        //监听下方button点击事件
        builderAlertDialogSingleNegativeButton.setNegativeButton(R.string.btn_certain, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DebugLog.d(mTag,"cancel dialog");
            }
        });

        //设置对话框是可取消的
        builderAlertDialogSingleNegativeButton.setCancelable(true);

        //创建dialog
        AlertDialog dialog=builderAlertDialogSingleNegativeButton.create();
        dialog.show();
        DebugLog.d(mTag,"Start the Alert Single Negative Button dialog Success");
    }
}
