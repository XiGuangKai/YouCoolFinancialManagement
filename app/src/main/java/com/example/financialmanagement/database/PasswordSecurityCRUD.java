package com.example.financialmanagement.database;

/**
 * Created by wangdy11 on 2017/7/14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PasswordSecurityCRUD {

    private DBUserData dbUserDataHelper;// 创建DBOpenHelper对象
    private SQLiteDatabase db;// 创建SQLiteDatabase对象

    public PasswordSecurityCRUD(Context context,String DBNAME,int VERSION)// 定义构造函数
    {
        dbUserDataHelper = new DBUserData(context,DBNAME,VERSION);// 初始化DBOpenHelper对象
    }

    /*
    * 向密码表中插入数据
    */
    public void insertPasswordSecurity(PasswordSecurity passwordSecurity){
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        ContentValues values = new ContentValues();
        values.put("likeNumber",passwordSecurity.getLikeNumber());
        values.put("goodfriendname",passwordSecurity.getGoodFriendName());
        values.put("likefood",passwordSecurity.getLikeFood());
        db.insert("tb_password_security",null,values);
        values.clear();
    }

    /*
    * 向密码表中更新数据
    */
    public void updatePasswordSecurity(PasswordSecurity passwordSecurity){
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        ContentValues values = new ContentValues();
        values.put("likeNumber",passwordSecurity.getLikeNumber());
        values.put("goodfriendname",passwordSecurity.getGoodFriendName());
        values.put("likefood",passwordSecurity.getLikeFood());
        db.insert("tb_password_security",null,values);
        values.clear();
    }

    public void deletePasswordSecurity(){
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        //db.execSQL("delete from tb_password_security where _id=1");
        db.delete("tb_password_security","_id = ?",new String[]{"1"});
    }
    /*
    * 查找最喜欢的数字
    */
    public String findBestLikeNumber() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.query("tb_password_security", null, null, null, null, null, null);

        //如果获取到的为空，直接返回
        if (cursor == null){
            return null;
        }

        // 遍历查找到的信息
        if (cursor.moveToNext())
        {
            // 将信息直接返回
            return cursor.getString(cursor.getColumnIndex("likeNumber"));
        }
        return null;// 如果没有信息，则返回null
    }

    /*
    * 查找最好的朋友名字
    */
    public String findBestLikeFirendName() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.query("tb_password_security", null, null, null, null, null, null);

        //如果获取到的为空，直接返回
        if (cursor == null){
            return null;
        }

        // 遍历查找到的信息
        if (cursor.moveToNext())
        {
            // 将信息直接返回
            return cursor.getString(cursor.getColumnIndex("goodfriendname"));
        }
        return null;// 如果没有信息，则返回null
    }

    /*
    * 查找最喜欢的食物的名字
    */
    public String findBestLikeFoodName() {
        db = dbUserDataHelper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 查找密码并存储到Cursor类中
        Cursor cursor = db.query("tb_password_security", null, null, null, null, null, null);

        //如果获取到的为空，直接返回
        if (cursor == null){
            return null;
        }

        // 遍历查找到的信息
        if (cursor.moveToNext())
        {
            // 将信息直接返回
            return cursor.getString(cursor.getColumnIndex("likefood"));
        }
        return null;// 如果没有信息，则返回null
    }
}
