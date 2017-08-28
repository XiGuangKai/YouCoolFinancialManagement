package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
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

public class FlagManageActivity extends AppCompatActivity {

    private static final String TAG = "FlagManageActivity";
    private EditText txtFlag;// 创建EditText对象
    private Button btnEdit, btnDel;// 创建两个Button对象
    private String strid;// 创建字符串，表示便签的id

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flag_manage);
        mContext = this;
        findView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        txtFlag = (EditText) findViewById(R.id.txtFlagManage);// 获取便签文本框
        btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);// 获取修改按钮
        btnDel = (Button) findViewById(R.id.btnFlagManageDelete);// 获取删除按钮
        DebugLog.d(TAG,"findView() finished");
    }

    private void init(){
        //获取数据库名字
        String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
        DebugLog.d(TAG, "mUserAndDbNameData = " + mUserAndDbNameData);

        Intent intent = getIntent();// 创建Intent对象
        Bundle bundle = intent.getExtras();// 获取便签id
        strid = bundle.getString(ShowInfoActivity.FLAG);// 将便签id转换为字符串

        final FlagCRUD mFlagCRUD = new FlagCRUD(mContext, mUserAndDbNameData, FinanceManageActivity.DB_VERSION);// 创建FlagDAO对象
        txtFlag.setText(mFlagCRUD.find(Integer.parseInt(strid)).getFlag());// 根据便签id查找便签信息，并显示在文本框中

        btnEdit.setOnClickListener(new OnClickListener() {// 为修改按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Flag tb_flag = new Flag();// 创建Tb_flag对象
                tb_flag.setid(Integer.parseInt(strid));// 设置便签id
                tb_flag.setFlag(txtFlag.getText().toString());// 设置便签值
                mFlagCRUD.update(tb_flag);// 修改便签信息

                DebugLog.d(TAG,"flag change success");
                // 弹出信息提示
                Toast.makeText(mContext, R.string.btn_flag_data_change_success,Toast.LENGTH_SHORT).show();
            }
        });

        btnDel.setOnClickListener(new OnClickListener() {// 为删除按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                mFlagCRUD.detele(Integer.parseInt(strid));// 根据指定的id删除便签信息
                DebugLog.d(TAG,"flag delete success");
                Toast.makeText(mContext, R.string.btn_flag_data_delete_success,Toast.LENGTH_SHORT).show();
            }
        });

        DebugLog.d(TAG,"init() finished");
    }
}
