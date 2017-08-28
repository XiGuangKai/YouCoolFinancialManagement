package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.Flag;
import com.example.financialmanagement.database.FlagCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

public class AccountFlagActivity extends AppCompatActivity {

    private static final String TAG = "AccountFlagActivity";
    private EditText txtFlag;// 创建EditText组件对象
    private Button btnflagSaveButton;// 创建Button组件对象
    private Button btnflagCancelButton;// 创建Button组件对象

    private Context mContext;
    private TitleBarView mTitleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_flag);
        mContext = this;

        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        txtFlag = (EditText) findViewById(R.id.txtFlag);// 获取便签文本框
        btnflagSaveButton = (Button) findViewById(R.id.btnflagSave);// 获取保存按钮
        btnflagCancelButton = (Button) findViewById(R.id.btnflagCancel);// 获取取消按钮
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);//获取title
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_add_new_flag);
        mTitleBarView.setBtnLeft(R.drawable.boss_unipay_icon_back, R.string.back);
        mTitleBarView.setBtnLeftOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DebugLog.d(TAG,"initTitleView() finished");
    }

    private void init(){
        btnflagSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String strFlag = txtFlag.getText().toString();// 获取便签文本框的值

                //获取数据库名字
                String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
                DebugLog.d(TAG,"mUserAndDbNameData = " + mUserAndDbNameData);

                if (!strFlag.isEmpty()) {// 判断获取的值不为空
                    FlagCRUD mFlagCRUD = new FlagCRUD(mContext,mUserAndDbNameData,FinanceManageActivity.DB_VERSION);// 创建mFlagCRUD对象

                    Flag mFlag = new Flag(mFlagCRUD.getMaxId() + 1, strFlag);// 创建mFlag对象

                    mFlagCRUD.add(mFlag);// 添加便签信息

                    DebugLog.d(TAG,"add the flag data success");
                    // 弹出信息提示
                    Toast.makeText(mContext, R.string.toast_add_flag_data_success,Toast.LENGTH_SHORT).show();
                } else {
                    DebugLog.d(TAG,"add the flag data failed");
                    Toast.makeText(mContext, R.string.toast_please_input_flag,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnflagCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                txtFlag.setText("");// 清空便签文本框
            }
        });
        DebugLog.d(TAG,"init() finished");
    }
}
