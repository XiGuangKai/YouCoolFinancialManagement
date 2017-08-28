package com.example.financialmanagement.activity;
/*
* 需求：当用户忘记密码时，能够帮助找回密码
*
* 分析：需要填写密码保护问题，用来确认用户身份的真实性，
*       1. 需要用户填写密保问题，
*       2. 根据填写的内容比对数据库中的密保问题
*       3. 成功比对则返回该用户的密码
*
* */
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.DBUserData;
import com.example.financialmanagement.database.PasswordSecurity;
import com.example.financialmanagement.database.PasswordSecurityCRUD;
import com.example.financialmanagement.database.UsernamePwdCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

public class GetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "GetPasswordActivity";
    private Context mContext;
    private Button btn_complete;
    private TitleBarView mTitleBarView;
    private EditText mLikeNumber;
    private EditText mGoodFirendName;
    private EditText mLikeFood;
    private EditText mGetPasswordUserName;
    private PasswordSecurity mPasswordSecurity;
    private PasswordSecurityCRUD mPasswordSecurityCRUD;
    private UsernamePwdCRUD mUsernamePwdCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password);
        mContext=this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
        btn_complete=(Button) findViewById(R.id.btn_get_password_confirm);
        mGetPasswordUserName = (EditText)findViewById(R.id.et_get_password_username);
        mLikeNumber = (EditText)findViewById(R.id.et_get_password_like_number);
        mGoodFirendName = (EditText)findViewById(R.id.et_get_password_good_friend);
        mLikeFood = (EditText)findViewById(R.id.et_get_password_good_food);
        DebugLog.d(TAG,"findView() finished");
    }

    private void init(){
        btn_complete.setOnClickListener(completeOnClickListener);
        DebugLog.d(TAG,"init() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_get_password_info);
        mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
        mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DebugLog.d(TAG,"initTitleView() finished");
    }

    private View.OnClickListener completeOnClickListener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //获取输入的信息
            String mETGetPasswordName = mGetPasswordUserName.getText().toString();
            String mETLikeNumber = mLikeNumber.getText().toString();
            String mETGoodFirendName = mGoodFirendName.getText().toString();
            String mETLikeFood = mLikeFood.getText().toString();
            //获取或者创建数据库
            mUsernamePwdCRUD = new UsernamePwdCRUD(mContext,mETGetPasswordName,FinanceManageActivity.DB_VERSION);
            mPasswordSecurityCRUD = new PasswordSecurityCRUD(mContext,mETGetPasswordName,FinanceManageActivity.DB_VERSION);


            if (mETGetPasswordName.isEmpty()){
                DebugLog.e(TAG,"the user name is empty");

                //显示提醒对话框
                FinanceManageActivity.showAlertDialogSingleNegativeButton(TAG,mContext,
                        getResources().getString(R.string.title_forget_password_appeals),
                        getResources().getString(R.string.content_get_password_user_name_null));

                return;
            } else {

                //首先在数据库中查找用户密码表，如果存在记录，表示之前该用户已经注册过。可以申请找回密码。
                // 如果表中不存在记录，说明该用户名的数据库是由上条信息新创建的数据库，所以该用户是不存在的，不能申请找回密码
                if (mUsernamePwdCRUD == null
                        || mUsernamePwdCRUD.find() == null
                        || (!(mUsernamePwdCRUD.findUserName().equals(mETGetPasswordName)))){

                    DebugLog.e(TAG,"The " + mETGetPasswordName + " not exit");

                    //显示提醒对话框
                    FinanceManageActivity.showAlertDialogSingleNegativeButton(TAG,mContext,
                            getResources().getString(R.string.title_forget_password_appeals),
                            getResources().getString(R.string.content_get_password_user_name_not_exist));

                    //删除多注册的database
                    if (DBUserData.deleteDatabase(mContext,mETGetPasswordName)){
                        DebugLog.d(TAG,"Delete the not exit username database success");
                    }else{
                        DebugLog.e(TAG,"Delete the not exit username database failed");
                        Toast.makeText(mContext,R.string.toast_delete_not_register_database_failed,Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }

            if (mETLikeNumber.isEmpty() && mETGoodFirendName.isEmpty() && mETLikeFood.isEmpty()){
                DebugLog.e(TAG,"All the password question is empty");

                //显示提醒对话框
                FinanceManageActivity.showAlertDialogSingleNegativeButton(TAG,mContext,
                        getResources().getString(R.string.title_forget_password_appeals),
                        getResources().getString(R.string.content_get_password_question_empty));

                return;
            }else{

                if (mETLikeNumber.equals(mPasswordSecurityCRUD.findBestLikeNumber())
                        && mETGoodFirendName.equals(mPasswordSecurityCRUD.findBestLikeFirendName())
                        && mETLikeFood.equals(mPasswordSecurityCRUD.findBestLikeFoodName())){

                    String passwordIs = mUsernamePwdCRUD.findPassword().toString();

                    DebugLog.e(TAG,"All the password question is right.show the password = " + passwordIs);
                    //显示提醒对话框
                    FinanceManageActivity.showAlertDialogSingleNegativeButton(TAG,mContext,
                            getResources().getString(R.string.title_forget_password_appeals),
                            getResources().getString(R.string.content_get_password_question_right_before) + "  " +
                                    mUsernamePwdCRUD.findPassword().toString() + "  " +
                                    getResources().getString(R.string.content_get_password_question_right_after));

                    mGetPasswordUserName.setText("");
                    mLikeNumber.setText("");
                    mGoodFirendName.setText("");
                    mLikeFood.setText("");
                }
            }
        }
    };
}
