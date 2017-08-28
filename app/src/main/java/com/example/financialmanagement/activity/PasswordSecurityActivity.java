package com.example.financialmanagement.activity;

import android.content.Context;
import android.content.Intent;
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
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

public class PasswordSecurityActivity extends AppCompatActivity {

    private static final String TAG = "PasswordSecuritActivity";
    private Context mContext;
    private Button btn_complete;
    private TitleBarView mTitleBarView;
    private EditText mLikeNumber;
    private EditText mGoodFirendName;
    private EditText mLikeFood;
    private PasswordSecurity mPasswordSecurity;
    private PasswordSecurityCRUD mPasswordSecurityCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_security);
        mContext=this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }
    private void findView(){
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
        btn_complete=(Button) findViewById(R.id.register_complete);
        mLikeNumber = (EditText)findViewById(R.id.et_likenumber);
        mGoodFirendName = (EditText)findViewById(R.id.et_goodFirendName);
        mLikeFood = (EditText)findViewById(R.id.et_likeFood);
        DebugLog.d(TAG,"findView() finished");
    }

    private void init(){
        btn_complete.setOnClickListener(completeOnClickListener);
        DebugLog.d(TAG,"init() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_register_info);
        mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
        mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 当用户返回的时候，用户可能会修改之前填写的用户名或者密码。
                * 为了避免出现问题，将本次未完成注册的数据库删除
                * */
                Intent intent =  new Intent();
                intent = getIntent();
                Bundle bundle = intent.getExtras();
                String dbname =  bundle.getString("dbname");
                DebugLog.d(TAG,"delete the dbname = " + dbname);
                DBUserData.deleteDatabase(mContext,dbname);

                //退出当前Activity
                finish();

            }
        });
        DebugLog.d(TAG,"initTitleView() finished");
    }

    private View.OnClickListener completeOnClickListener=new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            //获取输入的信息
            String mETLikeNumber = mLikeNumber.getText().toString();
            String mETGoodFirendName = mGoodFirendName.getText().toString();
            String mETLikeFood = mLikeFood.getText().toString();

            if (mETLikeNumber.isEmpty() && mETGoodFirendName.isEmpty() && mETLikeFood.isEmpty()){
                Toast.makeText(mContext,R.string.toast_password_security_not_all_empty,Toast.LENGTH_SHORT).show();
                DebugLog.d(TAG,"All the password security is empty");
                return;
            }else{
                //将信息插入到数据库中
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                mPasswordSecurity = new PasswordSecurity(mETLikeNumber,mETGoodFirendName,mETLikeFood);
                mPasswordSecurityCRUD = new PasswordSecurityCRUD(mContext,bundle.getString("dbname"),FinanceManageActivity.DB_VERSION);
                mPasswordSecurityCRUD.insertPasswordSecurity(mPasswordSecurity);

                //启动完成注册Activity
                Intent mRegisterResultActivityIntent=new Intent(mContext, RegisterResultActivity.class);
                mRegisterResultActivityIntent.putExtra("username",bundle.getString("dbname"));
                startActivity(mRegisterResultActivityIntent);
                mLikeNumber.setText("");
                mGoodFirendName.setText("");
                mLikeFood.setText("");
            }
        }
    };
}
