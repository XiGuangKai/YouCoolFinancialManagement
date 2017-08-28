package com.example.financialmanagement.database;

/**
 * Created by wangdy11 on 2017/7/14.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financialmanagement.util.DebugLog;

public class UsernamePwdCRUD {
    private DBUserData dbUserDataHelper;// 创建DBOpenHelper对象
    private SQLiteDatabase db;// 创建SQLiteDatabase对象

    private static final String TAG = "UsernamePwdCRUD";

    public UsernamePwdCRUD(Context context,String DBNAME,int VERSION)// 定义构造函数
    {
        dbUserDataHelper = new DBUserData(context,DBNAME,VERSION);// 初始化DBOpenHelper对象
    }

    /**
     * 添加密码信息
     *
     * @param mUsernamePwd
     */
    public void registerUsernamePwd(UsernamePwd mUsernamePwd) {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象

        DebugLog.d(TAG," Username = " + mUsernamePwd.getUsername() + " Password = " + mUsernamePwd.getPassword());
        // 执行添加用户名操作
        db.execSQL("insert into tb_username_pwd (username,password) values(?,?)", new Object[] { mUsernamePwd.getPassword(), mUsernamePwd.getPassword()});
    }

    /**
     * 设置密码信息
     *
     * @param mUsernamePwd
     */
    public void update(UsernamePwd mUsernamePwd) {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象

        DebugLog.d(TAG,Thread.currentThread().getStackTrace()[1].getMethodName() + " Username = " + mUsernamePwd.getUsername() + " Password = " + mUsernamePwd.getPassword());
        // 执行修改密码操作
        db.execSQL("update tb_username_pwd set password=? where username=?", new Object[] { mUsernamePwd.getPassword(),mUsernamePwd.getUsername()});
    }

    /**
     * 查找密码信息
     *
     * @return
     */

    public UsernamePwd find() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.rawQuery("select * from tb_username_pwd", null);
        //Cursor cursor = db.query("tb_username_pwd", null, null, null, null, null, null);
        //Cursor cursor = db.rawQuery("select password from tb_username_pwd where username = ?",new String[]{username});

        if (cursor == null){
            return null;
        }

        if (cursor.moveToNext())// 遍历查找到的密码信息
        {
            // 将密码存储到Tb_pwd类中
            return new UsernamePwd(cursor.getString(cursor
                    .getColumnIndex("username")),cursor.getString(cursor
                    .getColumnIndex("password")));
        }
        return null;// 如果没有信息，则返回null
    }

    public String findPassword() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.query("tb_username_pwd", null, null, null, null, null, null);

        if (cursor == null){
            return null;
        }
        if (cursor.moveToNext())// 遍历查找到的密码信息
        {
            // 将密码直接返回
            return cursor.getString(cursor.getColumnIndex("password"));
        }
        return null;// 如果没有信息，则返回null
    }

    public String findUserName() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.query("tb_username_pwd", null, null, null, null, null, null);

        if (cursor == null){
            return null;
        }
        if (cursor.moveToNext())// 遍历查找到的密码信息
        {
            // 将密码直接返回
            return cursor.getString(cursor.getColumnIndex("username"));
        }
        return null;// 如果没有信息，则返回null
    }

    public long getCount() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select count(password) from tb_pwd", null);// 获取密码信息的记录数
        if (cursor.moveToNext())// 判断Cursor中是否有数据
        {
            return cursor.getLong(0);// 返回总记录数
        }
        return 0;// 如果没有数据，则返回0
    }

    public static boolean checkDataBase(String DB_NAME){

        return DBUserData.checkDataBase(DB_NAME);
    }
}
