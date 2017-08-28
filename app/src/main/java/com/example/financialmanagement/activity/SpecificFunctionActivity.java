package com.example.financialmanagement.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.financialmanagement.R;
import com.example.financialmanagement.util.DebugLog;
import com.example.financialmanagement.util.SystemTime;

public class SpecificFunctionActivity extends AppCompatActivity {

    private static final String TAG = "SpecificFunctioActivity";

    private static final int ITEM_CLICK_0 = 0;
    private static final int ITEM_CLICK_1 = 1;
    private static final int ITEM_CLICK_2 = 2;
    private static final int ITEM_CLICK_3 = 3;
    private static final int ITEM_CLICK_4 = 4;
    private static final int ITEM_CLICK_5 = 5;

    GridView gvInfo;// 创建GridView对象
    // 定义字符串数组，存储系统功能
    String[] titles = new String[] { "新增支出", "新增收入", "我的支出", "我的收入", "数据管理", "收支便签" };
    // 定义int数组，存储功能对应的图标
    int[] images = new int[] { R.drawable.addoutaccount,
            R.drawable.addinaccount, R.drawable.outaccountinfo,
            R.drawable.inaccountinfo, R.drawable.showinfo,
            R.drawable.accountflag };

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_function);

        gvInfo = (GridView) findViewById(R.id.gvInfo);// 获取布局文件中的gvInfo组件
        pictureAdapter adapter = new pictureAdapter(titles, images, this);// 创建pictureAdapter对象
        gvInfo.setAdapter(adapter);// 为GridView设置数据源
        gvInfo.setOnItemClickListener(new OnItemClickListener() {// 为GridView设置项单击事件
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = null;// 创建Intent对象
                switch (arg2) {
                    case ITEM_CLICK_0:
                        DebugLog.d(TAG,"Start AddOutAccount Activity");
                        intent = new Intent(SpecificFunctionActivity.this, AddOutAccountActivity.class);// 使用AddOutAccountActivity窗口初始化Intent
                        startActivity(intent);// 打开AddOutAccountActivity
                        break;
                    case ITEM_CLICK_1:
                        DebugLog.d(TAG,"Start AddInAccount Activity");
                        intent = new Intent(SpecificFunctionActivity.this, AddInAccountActivity.class);// 使用AddInAccountActivity窗口初始化Intent
                        startActivity(intent);// 打开AddInAccountActivity
                        break;
                    case ITEM_CLICK_2:
                        DebugLog.d(TAG,"Start OutAccountInfoActivity Activity");
                        intent = new Intent(SpecificFunctionActivity.this, OutAccountInfoActivity.class);// 使用OutAccountInfoActivity窗口初始化Intent
                        startActivity(intent);// 打开OutAccountInfoActivity
                        break;
                    case ITEM_CLICK_3:
                        DebugLog.d(TAG,"Start InAccountInfoActivity Activity");
                        intent = new Intent(SpecificFunctionActivity.this, InAccountInfoActivity.class);// 使用InAccountInfoActivity窗口初始化Intent
                        startActivity(intent);// 打开InAccountInfoActivity
                        break;
                    case ITEM_CLICK_4:
                        DebugLog.d(TAG,"Start ShowInfoActivity Activity");
                        intent = new Intent(SpecificFunctionActivity.this, ShowInfoActivity.class);// 使用ShowInfoActivity窗口初始化Intent
                        startActivity(intent);// 打开ShowInfoActivity
                        break;
                    case ITEM_CLICK_5:
                        DebugLog.d(TAG,"Start AccountFlagActivity Activity");
                        intent = new Intent(SpecificFunctionActivity.this, AccountFlagActivity.class);// 使用AccountFlagActivity窗口初始化Intent
                        startActivity(intent);// 打开AccountFlagActivity
                        break;
                    default:
                        DebugLog.d(TAG,"Unrecognizable function");
                        break;
                }
            }
        });
    }


    class pictureAdapter extends BaseAdapter// 创建基于BaseAdapter的子类
    {
        private LayoutInflater inflater;// 创建LayoutInflater对象
        private List<Picture> pictures;// 创建List泛型集合

        // 为类创建构造函数
        public pictureAdapter(String[] titles, int[] images, Context context) {
            super();
            pictures = new ArrayList<Picture>();// 初始化泛型集合对象
            inflater = LayoutInflater.from(context);// 初始化LayoutInflater对象
            for (int i = 0; i < images.length; i++)// 遍历图像数组
            {
                Picture picture = new Picture(titles[i], images[i]);// 使用标题和图像生成Picture对象
                pictures.add(picture);// 将Picture对象添加到泛型集合中
            }
        }

        @Override
        public int getCount() {// 获取泛型集合的长度
            if (null != pictures) {// 如果泛型集合不为空
                return pictures.size();// 返回泛型长度
            } else {
                return 0;// 返回0
            }
        }

        @Override
        public Object getItem(int arg0) {
            return pictures.get(arg0);// 获取泛型集合指定索引处的项
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;// 返回泛型集合的索引
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            ViewHolder viewHolder;// 创建ViewHolder对象
            if (arg1 == null)// 判断图像标识是否为空
            {
                arg1 = inflater.inflate(R.layout.gvitem, null);// 设置图像标识
                viewHolder = new ViewHolder();// 初始化ViewHolder对象
                viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);// 设置图像标题
                viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);// 设置图像的二进制值
                arg1.setTag(viewHolder);// 设置提示
            } else {
                viewHolder = (ViewHolder) arg1.getTag();// 设置提示
            }
            viewHolder.title.setText(pictures.get(arg0).getTitle());// 设置图像标题
            viewHolder.image.setImageResource(pictures.get(arg0).getImageId());// 设置图像的二进制值
            return arg1;// 返回图像标识
        }
    }

    class ViewHolder// 创建ViewHolder类
    {
        public TextView title;// 创建TextView对象
        public ImageView image;// 创建ImageView对象
    }

    class Picture// 创建Picture类
    {
        private String title;// 定义字符串，表示图像标题
        private int imageId;// 定义int变量，表示图像的二进制值

        public Picture()// 默认构造函数
        {
            super();
        }

        public Picture(String title, int imageId)// 定义有参构造函数
        {
            super();
            this.title = title;// 为图像标题赋值
            this.imageId = imageId;// 为图像的二进制值赋值
        }

        public String getTitle() {// 定义图像标题的可读属性
            return title;
        }

        public void setTitle(String title) {// 定义图像标题的可写属性
            this.title = title;
        }

        public int getImageId() {// 定义图像二进制值的可读属性
            return imageId;
        }

        public void setimageId(int imageId) {// 定义图像二进制值的可写属性
            this.imageId = imageId;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int item_id = item.getItemId();
        switch (item_id) {
            case R.id.item_menu_option_system_setting:
                DebugLog.d(TAG,"Start SystemSetActivity Activity");
                Intent intent = new Intent();
                intent = new Intent(SpecificFunctionActivity.this, SystemSetActivity.class);// 使用SystemSetActivity窗口初始化Intent
                startActivity(intent);// 打开SystemSetActivity
                break;
            case R.id.item_menu_option_retreat_safely:
                DebugLog.d(TAG,"exit function");
                finish();// 关闭当前Activity
                break;
            default:
                return false;
        }
        return true;

    }

    public static SystemTime getCurrentTime(String mTAG){
        int mYear;// 年
        int mMonth;// 月
        int mDay;// 日

        // 获取当前系统日期
        Calendar calendar = Calendar.getInstance();

        // 获取年份
        mYear = calendar.get(Calendar.YEAR);

        // 获取月份，因为罗马计数从0开始，所以我们需要对应的月份增加1
        mMonth = calendar.get(Calendar.MONTH) + 1;

        // 获取天数
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DebugLog.d(mTAG,"updateDisplay() Year = " + mYear + " Month = " + mMonth + " Day = " + mDay);

        return new SystemTime(mYear,mMonth,mDay);
    }

    public static void showDatePickerDialog(String mTag ,Context context,final EditText mChangeTimeEditText) {//显示日期选择对话框
        DebugLog.d(mTag,"show DatePicker Dialog");

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                //因为罗马计数从0开始，所以我们需要对应的月份增加1
                monthOfYear = monthOfYear + 1;

                mChangeTimeEditText.setText(year + "-" + monthOfYear + "-" + dayOfMonth); //设置时间
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //将对话框显示
        datePickerDialog.show();

        DebugLog.d(mTag,"show DatePicker Dialog finished");
    }

    public static void updateTimeDisplay(String mTag, SystemTime systemTime, EditText mEditText) {
        DebugLog.d(mTag,"updateDisplay() systemTime Year = " + systemTime.getYear() + " systemTime Month = " + systemTime.getMonth() + " systemTime Day = " + systemTime.getDay());

        // 显示设置的时间
        mEditText.setText(new StringBuilder().append(systemTime.getYear()).append("-").append(systemTime.getMonth()).append("-").append(systemTime.getDay()));
    }
}
