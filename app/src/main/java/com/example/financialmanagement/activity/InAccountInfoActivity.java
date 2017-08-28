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
import com.example.financialmanagement.database.InAccount;
import com.example.financialmanagement.database.InAccountCRUD;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.view.TitleBarView;

import java.util.List;

public class InAccountInfoActivity extends AppCompatActivity {

    private static final String TAG = "InAccountInfoActivity";

    public static final String FLAG = "id";// 定义一个常量，用来作为请求码
    private ListView lvinfo;// 创建ListView对象
    private String strType = "";// 创建字符串，记录管理类型

    private Context mContext;
    private TitleBarView mTitleBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_account_info);
        mContext = this;
        findView();
        initTitleView();
        init();
        DebugLog.d(TAG,"onCreate() finished");
    }

    private void findView(){
        lvinfo = (ListView) findViewById(R.id.lvinaccountinfo);// 获取布局文件中的ListView组件
        mTitleBarView=(TitleBarView) findViewById(R.id.title_bar);//获取title
        DebugLog.d(TAG,"findView() finished");
    }

    private void initTitleView(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE,View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.title_in_account);
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
        ShowInfo(R.id.btnininfo);// 调用自定义方法显示收入信息

        lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
        {
            // 覆写onItemClick方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String strInfo = String.valueOf(((TextView) view).getText());// 记录收入信息
                String strid = strInfo.substring(0, strInfo.indexOf('|'));// 从收入信息中截取收入编号
                Intent intent = new Intent(mContext, InfoManageActivity.class);// 创建Intent对象
                intent.putExtra(FLAG, new String[]{strid, strType});// 设置传递数据
                startActivity(intent);// 执行Intent操作
            }
        });
        DebugLog.d(TAG,"init() finished");
    }

    private void ShowInfo(int intType) {// 用来根据传入的管理类型，显示相应的信息
        String[] strInfos = null;// 定义字符串数组，用来存储收入信息
        ArrayAdapter<String> arrayAdapter = null;// 创建ArrayAdapter对象
        strType = OutAccountInfoActivity.IN_INFO;// 为strType变量赋值

        //获取数据库名字
        String mUserAndDbNameData = FinanceManageActivity.getUserAndDbNameData(mContext);
        DebugLog.d(TAG, "mUserAndDbNameData = " + mUserAndDbNameData);

        InAccountCRUD inaccountinfo = new InAccountCRUD(mContext, mUserAndDbNameData, FinanceManageActivity.DB_VERSION);// 创建InaccountDAO对象

        // 获取所有收入信息，并存储到List泛型集合中
        List<InAccount> listinfos = inaccountinfo.getScrollData(0,(int) inaccountinfo.getCount());
        strInfos = new String[listinfos.size()];// 设置字符串数组的长度
        int m = 0;// 定义一个开始标识
        for (InAccount mInAccount : listinfos) {// 遍历List泛型集合
            // 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置
            strInfos[m] = mInAccount.getid() + "|" + mInAccount.getType()
                    + " " + String.valueOf(mInAccount.getMoney()) + "元     "
                    + mInAccount.getTime();
            m++;// 标识加1
        }
        // 使用字符串数组初始化ArrayAdapter对象
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, strInfos);
        lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
        DebugLog.d(TAG,"ShowInfo() finished");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();// 实现基类中的方法
        ShowInfo(R.id.btnininfo);// 显示收入信息
    }
}
