package com.example.financialmanagement.activity;

import com.example.financialmanagement.database.PasswordSecurityCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TextURLView;
import com.example.financialmanagement.view.TitleBarView;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.financialmanagement.R;

public class RegisterResultActivity extends AppCompatActivity {

    private static final String TAG = "RegisterResultActivity";
    private Context mContext;
    private TitleBarView mTitleBarView;
    private TextURLView url;
    private Button complete;
    private TextView mRegisterOkUsernameShow;
    private CheckBox mCkRegisterOkAgreen;
    private PasswordSecurityCRUD mPasswordSecurityCRUD;
    private String dbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_result);

        mContext=this;
        findView();
        initTitleView();
        initTvUrl();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
        url=(TextURLView) findViewById(R.id.tv_tiaokuan);
        complete=(Button) findViewById(R.id.register_success);
        mRegisterOkUsernameShow = (TextView)findViewById(R.id.tv_register_ok_username_show);
        mCkRegisterOkAgreen = (CheckBox)findViewById(R.id.ck_register_ok_agreen);
        DebugLog.d(TAG,"findView() finished");
    }

    private void init(){

        complete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugLog.d(TAG,"start FinancialManagementMainActivity");
                Intent intent=new Intent(mContext, FinanceManageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mCkRegisterOkAgreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCkRegisterOkAgreen.isChecked()){
                    DebugLog.d(TAG,"CheckBox is Checked set register_success to enable");
                    complete.setEnabled(true);
                }else{
                    DebugLog.d(TAG,"CheckBox is not Checked set register_success to disable");
                    complete.setEnabled(false);
                }
            }
        });
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.tv_register_success);
        mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
        showRegisterUserName();
        mTitleBarView.setBtnLeftOnclickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 当点击返回按钮时，为了防止密保问题多条增加，所以删除第一条，
                * 删除第一条相当于删除了表中所有的数据。
                * 确保密保问题表中只有一条数据
                */
                mPasswordSecurityCRUD = new PasswordSecurityCRUD(mContext,dbName,FinanceManageActivity.DB_VERSION);
                mPasswordSecurityCRUD.deletePasswordSecurity();
                DebugLog.d(TAG,"delete the Security question,dbname = " + dbName);
                finish();

            }
        });
        DebugLog.d(TAG,"initTitleView() finished");
    }

    private void initTvUrl(){
        url.setText(R.string.tv_tiaokuan);
        url.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        DebugLog.d(TAG,"initTvUrl() finished");
    }

    private void showRegisterUserName(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dbName = bundle.getString("username");
        mRegisterOkUsernameShow.setText(dbName);
        DebugLog.d(TAG,"showRegisterUserName() finished, dbname = " + dbName);
    }
}
