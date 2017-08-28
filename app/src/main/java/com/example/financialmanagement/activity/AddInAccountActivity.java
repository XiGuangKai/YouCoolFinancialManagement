package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.financialmanagement.R;
import com.example.financialmanagement.database.InAccount;
import com.example.financialmanagement.database.InAccountCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.util.SystemTime;
import com.example.financialmanagement.view.TitleBarView;

public class AddInAccountActivity extends AppCompatActivity {

    private static final String TAG = "AddInAccountActivity";
    private EditText txtInMoney, txtTime, txtInHandler, txtInMark;// 创建4个EditText对象
    private Spinner spInType;// 创建Spinner对象
    private Button btnInSaveButton;// 创建Button对象“保存”
    private Button btnInCancelButton;// 创建Button对象“取消”
    private Context mContext;
    private TitleBarView mTitleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_in_account);
        mContext = this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);//获取title
        txtInMoney = (EditText) findViewById(R.id.txtInMoney);// 获取金额文本框
        txtTime = (EditText) findViewById(R.id.txtInTime);// 获取时间文本框
        txtInHandler = (EditText) findViewById(R.id.txtInHandler);// 获取付款方文本框
        txtInMark = (EditText) findViewById(R.id.txtInMark);// 获取备注文本框
        spInType = (Spinner) findViewById(R.id.spInType);// 获取类别下拉列表
        btnInSaveButton = (Button) findViewById(R.id.btnInSave);// 获取保存按钮
        btnInCancelButton = (Button) findViewById(R.id.btnInCancel);// 获取取消按钮
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_new_add_in_account);
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

        btnInSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                // 获取金额文本框的值
                String strInMoney = txtInMoney.getText().toString();

                //获取数据库名字
                String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
                DebugLog.d(TAG,"mUserAndDbNameData = " + mUserAndDbNameData);

                if (!strInMoney.isEmpty()) {// 判断金额不为空

                    // 创建mInAccountCRUD对象
                    InAccountCRUD mInAccountCRUD = new InAccountCRUD(mContext,mUserAndDbNameData,FinanceManageActivity.DB_VERSION);

                    // 创建mInAccount对象
                    InAccount mInAccount = new InAccount(
                            mInAccountCRUD.getMaxId() + 1, Double
                            .parseDouble(strInMoney), txtTime
                            .getText().toString(), spInType
                            .getSelectedItem().toString(),
                            txtInHandler.getText().toString(),
                            txtInMark.getText().toString());

                    // 添加收入信息
                    mInAccountCRUD.add(mInAccount);

                    DebugLog.d(TAG,"Add the inaccount success");
                    // 弹出信息提示
                    Toast.makeText(mContext, R.string.toast_add_new_inaccount_success,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, R.string.toast_input_the_inmoney,Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnInCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //获取当前时间
                SystemTime systemTime = SpecificFunctionActivity.getCurrentTime(TAG);

                txtInMoney.setText("");// 设置金额文本框为空
                txtInMoney.setHint("0.00");// 为金额文本框设置提示
                txtTime.setText("");// 设置时间文本框为空
                txtTime.setHint(systemTime.getYear() + "-" + systemTime.getMonth() + "-" + systemTime.getDay());// 为时间文本框设置提示
                txtInHandler.setText("");// 设置付款方文本框为空
                txtInMark.setText("");// 设置备注文本框为空
                spInType.setSelection(0);// 设置类别下拉列表默认选择第一项
            }
        });

        //更新时间
        SpecificFunctionActivity.updateTimeDisplay(TAG,SpecificFunctionActivity.getCurrentTime(TAG),txtTime);
        DebugLog.d(TAG,"init() finished");
    }
}
