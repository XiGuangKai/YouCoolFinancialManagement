package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.InAccount;
import com.example.financialmanagement.database.InAccountCRUD;
import com.example.financialmanagement.database.OutAccount;
import com.example.financialmanagement.database.OutAccountCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

public class InfoManageActivity extends AppCompatActivity {

    private static final String TAG = "InfoManageActivity";
    protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
    private TextView textView;// 创建两个TextView对象
    private EditText txtMoney, txtTime, txtHA, txtMark;// 创建4个EditText对象
    private Spinner spType;// 创建Spinner对象
    private Button btnEdit, btnDel;// 创建两个Button对象
    private String[] strInfos;// 定义字符串数组
    private String strid, strType;// 定义两个字符串变量，分别用来记录信息编号和管理类型

    private Context mContext;
    private TitleBarView mTitleBarView;

    private OutAccountCRUD mOutAccountCRUD;
    private InAccountCRUD mInAccountCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_manage);
        mContext = this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        textView = (TextView) findViewById(R.id.tvInOut);// 获取地点/付款方标签对象
        txtMoney = (EditText) findViewById(R.id.txtInOutMoney);// 获取金额文本框
        txtTime = (EditText) findViewById(R.id.txtInOutTime);// 获取时间文本框
        spType = (Spinner) findViewById(R.id.spInOutType);// 获取类别下拉列表
        txtHA = (EditText) findViewById(R.id.txtInOut);// 获取地点/付款方文本框
        txtMark = (EditText) findViewById(R.id.txtInOutMark);// 获取备注文本框
        btnEdit = (Button) findViewById(R.id.btnInOutEdit);// 获取修改按钮
        btnDel = (Button) findViewById(R.id.btnInOutDelete);// 获取删除按钮
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);//获取title
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
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
        //获取数据库名字
        String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
        DebugLog.d(TAG, "mUserAndDbNameData = " + mUserAndDbNameData);

        mOutAccountCRUD = new OutAccountCRUD(mContext, mUserAndDbNameData, FinanceManageActivity.DB_VERSION);// 创建OutaccountDAO对象
        mInAccountCRUD = new InAccountCRUD(mContext, mUserAndDbNameData, FinanceManageActivity.DB_VERSION);// 创建InaccountDAO对象

        Intent intent = getIntent();// 创建Intent对象
        Bundle bundle = intent.getExtras();// 获取传入的数据，并使用Bundle记录
        strInfos = bundle.getStringArray(ShowInfoActivity.FLAG);// 获取Bundle中记录的信息

        strid = strInfos[0];// 记录id
        strType = strInfos[1];// 记录类型
        if (strType.equals(OutAccountInfoActivity.OUT_INFO))// 如果类型是outinfo
        {
            DebugLog.d(TAG,"strType is out info start");
            mTitleBarView.setTitleText(R.string.title_out_account_manage);// 设置标题为“支出管理”
            textView.setText(R.string.tv_address);// 设置“地点/付款方”标签文本为“地 点：”
            // 根据编号查找支出信息，并存储到mOutAccountCRUD对象中
            OutAccount mOutAccount = mOutAccountCRUD.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(mOutAccount.getMoney()));// 显示金额
            txtTime.setText(mOutAccount.getTime());// 显示时间
            spType.setPrompt(mOutAccount.getType());// 显示类别
            txtHA.setText(mOutAccount.getAddress());// 显示地点
            txtMark.setText(mOutAccount.getMark());// 显示备注

        } else if (strType.equals(OutAccountInfoActivity.IN_INFO)){// 如果类型是ininfo

            DebugLog.d(TAG,"strType is in info start");
            mTitleBarView.setTitleText(R.string.title_in_account_manage);
            textView.setText(R.string.tv_InHandler);// 设置“地点/付款方”标签文本为“付款方：”

            // 根据编号查找收入信息，并存储到mInAccountCRUD对象中
            InAccount mInAccount = mInAccountCRUD.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(mInAccount.getMoney()));// 显示金额
            txtTime.setText(mInAccount.getTime());// 显示时间
            spType.setPrompt(mInAccount.getType());// 显示类别
            txtHA.setText(mInAccount.getHandler());// 显示付款方
            txtMark.setText(mInAccount.getMark());// 显示备注
        }

        txtTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    DebugLog.d(TAG,"show DatePicker Dialog");
                    SpecificFunctionActivity.showDatePickerDialog(TAG,mContext,txtTime);
                    return true;
                }
                return false;
            }
        });

        btnEdit.setOnClickListener(new OnClickListener() {// 为修改按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                DebugLog.d(TAG,"strType = " + strType);
                if (strType.equals(OutAccountInfoActivity.OUT_INFO))// 判断类型如果是btnoutinfo
                {
                    OutAccount mOutAccount = new OutAccount();// 创建Tb_outaccount对象
                    mOutAccount.setid(Integer.parseInt(strid));// 设置编号
                    mOutAccount.setMoney(Double.parseDouble(txtMoney.getText().toString()));// 设置金额
                    mOutAccount.setTime(txtTime.getText().toString());// 设置时间
                    mOutAccount.setType(spType.getSelectedItem().toString());// 设置类别
                    mOutAccount.setAddress(txtHA.getText().toString());// 设置地点
                    mOutAccount.setMark(txtMark.getText().toString());// 设置备注

                    mOutAccountCRUD.update(mOutAccount);// 更新支出信息

                    // 弹出信息提示
                    Toast.makeText(mContext,R.string.toast_the_data_change_success, Toast.LENGTH_SHORT).show();

                } else if (strType.equals(OutAccountInfoActivity.IN_INFO))// 判断类型如果是btnininfo
                {
                    InAccount mInAccount = new InAccount();// 创建Tb_inaccount对象
                    mInAccount.setid(Integer.parseInt(strid));// 设置编号
                    mInAccount.setMoney(Double.parseDouble(txtMoney.getText()
                            .toString()));// 设置金额
                    mInAccount.setTime(txtTime.getText().toString());// 设置时间
                    mInAccount.setType(spType.getSelectedItem().toString());// 设置类别
                    mInAccount.setHandler(txtHA.getText().toString());// 设置付款方
                    mInAccount.setMark(txtMark.getText().toString());// 设置备注
                    mInAccountCRUD.update(mInAccount);// 更新收入信息

                    // 弹出信息提示
                    Toast.makeText(mContext,R.string.toast_the_data_change_success, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDel.setOnClickListener(new OnClickListener() {// 为删除按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                DebugLog.d(TAG,"strType = " + strType);
                if (strType.equals(OutAccountInfoActivity.OUT_INFO)){// 判断类型如果是outinfo

                    mOutAccountCRUD.detele(Integer.parseInt(strid));// 根据编号删除支出信息
                    Toast.makeText(mContext,R.string.toast_the_data_delete_success, Toast.LENGTH_SHORT).show();

                } else if (strType.equals(OutAccountInfoActivity.IN_INFO)){// 判断类型如果是ininfo

                    mInAccountCRUD.detele(Integer.parseInt(strid));// 根据编号删除收入信息
                    Toast.makeText(mContext,R.string.toast_the_data_delete_success, Toast.LENGTH_SHORT).show();
                }
            }
        });

        DebugLog.d(TAG,"init() finished");
    }
}
