package com.example.financialmanagement.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import com.example.financialmanagement.R;
import com.example.financialmanagement.database.OutAccount;
import com.example.financialmanagement.database.OutAccountCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

import java.util.List;

public class OutAccountInfoActivity extends AppCompatActivity {

    public static final String OUT_INFO = "btnoutinfo";
    public static final String IN_INFO = "btnininfo";
    public static final String FLAG_INFO = "btnflaginfo";

    private static final String TAG = "OutAccountInfoActivity";
    public static final String FLAG = "id";// 定义一个常量，用来作为请求码
    private ListView lvinfo;// 创建ListView对象
    private String strType = "";// 创建字符串，记录管理类型

    private Context mContext;
    private TitleBarView mTitleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_account_info);
        mContext = this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);// 获取布局文件中的ListView组件
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);//获取title
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_my_out_account);
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

        lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
        {
            // 覆写onItemClick方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String strInfo = String.valueOf(((TextView) view).getText());// 记录支出信息

                String strid = strInfo.substring(0, strInfo.indexOf('|'));// 从支出信息中截取支出编号

                Intent intent = new Intent(mContext,InfoManageActivity.class);// 创建Intent对象

                intent.putExtra(FLAG, new String[] { strid, strType });// 设置传递数据

                startActivity(intent);// 执行Intent操作
            }
        });

        DebugLog.d(TAG,"init() finished");
    }

    @Override
    public void onResume(){
        // TODO Auto-generated method stub
        super.onResume();
        ShowOutaccountInfo(R.id.btnoutinfo);// 调用自定义方法显示支出信息
        DebugLog.d(TAG,"onResume() finished");
    }

    private void ShowOutaccountInfo(int intType) {// 用来根据传入的管理类型，显示相应的信息

        String[] strInfos = null;// 定义字符串数组，用来存储支出信息
        ArrayAdapter<String> arrayAdapter = null;// 创建ArrayAdapter对象
        strType = OUT_INFO;// 为strType变量赋值
        int i = 0;// 定义一个开始标识

        //获取数据库名字
        String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
        DebugLog.d(TAG,"mUserAndDbNameData = " + mUserAndDbNameData);

        OutAccountCRUD mOutAccountCRUD = new OutAccountCRUD(mContext,mUserAndDbNameData,FinanceManageActivity.DB_VERSION);// 创建OutaccountDAO对象

        // 获取所有支出信息，并存储到List泛型集合中
        List<OutAccount> listoutinfos = mOutAccountCRUD.getScrollData(0,(int) mOutAccountCRUD.getCount());

        strInfos = new String[listoutinfos.size()];// 设置字符串数组的长度

        for (OutAccount mOutAccount : listoutinfos) {// 遍历List泛型集合
            // 将支出相关信息组合成一个字符串，存储到字符串数组的相应位置
            strInfos[i] = mOutAccount.getid() + "|" + mOutAccount.getType()
                    + " " + String.valueOf(mOutAccount.getMoney()) + "元     "
                    + mOutAccount.getTime();

            // 标识加1
            i++;
        }

        // 使用字符串数组初始化ArrayAdapter对象
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, strInfos);

        lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
        DebugLog.d(TAG,"ShowOutaccountInfo() finished");
    }
}
