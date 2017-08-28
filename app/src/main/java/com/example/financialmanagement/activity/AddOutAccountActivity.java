package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.OutAccount;
import com.example.financialmanagement.database.OutAccountCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.util.SystemTime;
import com.example.financialmanagement.view.TitleBarView;

public class AddOutAccountActivity extends AppCompatActivity {


    private static final String TAG = "AddOutAccountActivity";

    protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
    private EditText txtMoney, txtTime, txtAddress, txtMark;// 创建4个EditText对象
    private Spinner spType;// 创建Spinner对象
    private Button btnSaveButton;// 创建Button对象“保存”
    private Button btnCancelButton;// 创建Button对象“取消”

    private TitleBarView mTitleBarView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_out_account);
        mContext = this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);
        txtMoney = (EditText) findViewById(R.id.txtMoney);// 获取金额文本框
        txtTime = (EditText) findViewById(R.id.txtTime);// 获取时间文本框
        txtAddress = (EditText) findViewById(R.id.txtAddress);// 获取地点文本框
        txtMark = (EditText) findViewById(R.id.txtMark);// 获取备注文本框
        spType = (Spinner) findViewById(R.id.spType);// 获取类别下拉列表
        btnSaveButton = (Button) findViewById(R.id.btnSave);// 获取保存按钮
        btnCancelButton = (Button) findViewById(R.id.btnCancel);// 获取取消按钮
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_new_add_out_account);
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

        btnSaveButton.setOnClickListener(new View.OnClickListener() {// 为保存按钮设置监听事件

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                DebugLog.d(TAG,"Save the add out account");
                // 获取金额文本框的值
                String strMoney = txtMoney.getText().toString();

                // 判断金额不为空
                if (!strMoney.isEmpty()) {

                    //获取数据库名字
                    String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
                    DebugLog.d(TAG,"mUserAndDbNameData = " + mUserAndDbNameData);

                    // 创建mOutAccountCRUD对象
                    OutAccountCRUD mOutAccountCRUD = new OutAccountCRUD(mContext,mUserAndDbNameData,FinanceManageActivity.DB_VERSION);

                    // 创建mOutAccount对象
                    OutAccount mOutAccount = new OutAccount(
                            mOutAccountCRUD.getMaxId() + 1, Double
                            .parseDouble(strMoney), txtTime
                            .getText().toString(), spType
                            .getSelectedItem().toString(),
                            txtAddress.getText().toString(), txtMark
                            .getText().toString());

                    // 添加支出信息
                    mOutAccountCRUD.add(mOutAccount);

                    DebugLog.d(TAG,"Add the outaccount success");
                    // 弹出信息提示
                    Toast.makeText(mContext,R.string.toast_add_new_outaccount_success, Toast.LENGTH_SHORT).show();

                } else {
                    DebugLog.d(TAG,"Input the outaccount money");
                    Toast.makeText(mContext,R.string.toast_input_the_outmoney,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelButton.setOnClickListener(new View.OnClickListener() {// 为取消按钮设置监听事件

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //获取当前时间
                SystemTime systemTime = SpecificFunctionActivity.getCurrentTime(TAG);

                DebugLog.d(TAG,"Cancel the add out account");
                txtMoney.setText("");// 设置金额文本框为空

                txtMoney.setHint("0.00");// 为金额文本框设置提示

                txtTime.setText("");// 设置时间文本框为空

                txtTime.setHint(systemTime.getYear() + "-" + systemTime.getMonth() + "-" + systemTime.getDay());// 为时间文本框设置提示

                txtAddress.setText("");// 设置地点文本框为空

                txtMark.setText("");// 设置备注文本框为空

                spType.setSelection(0);// 设置类别下拉列表默认选择第一项
            }
        });

        //更新时间
        SpecificFunctionActivity.updateTimeDisplay(TAG,SpecificFunctionActivity.getCurrentTime(TAG),txtTime);

        DebugLog.d(TAG,"init() finished");
    }
}
