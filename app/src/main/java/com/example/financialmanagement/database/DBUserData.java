package com.example.financialmanagement.database;

/**
 * Created by wangdy11 on 2017/7/14.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUserData extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_OUTACCOUNT = "create table tb_outaccount (_id integer primary key,money decimal,time varchar(10),"
            + "type varchar(10),address varchar(100),mark varchar(200))";

    private static final String CREATE_TABLE_INACCOUNT = "create table tb_inaccount (_id integer primary key,money decimal,time varchar(10),"
            + "type varchar(10),handler varchar(100),mark varchar(200))";

    //private static final String CREATE_TABLE_USERNAME_PASSWORD = "create table tb_username_pwd (_id integer primary key,username varchar(50),password varchar(24))";
    private static final String CREATE_TABLE_USERNAME_PASSWORD = "create table tb_username_pwd (username varchar(64) primary key,password varchar(64))";

    private static final String CREATE_TABLE_PASSWORD_SECURITY = "create table tb_password_security (_id integer primary key,likeNumber varchar(64),goodfriendname varchar(64),likefood varchar(64))";

    private static final String CREATE_TABLE_FLAG = "create table tb_flag (_id integer primary key,flag varchar(200))";

    private static final String DB_PATH = "/data/data/com.example.financialmanagement/databases/";

    public DBUserData(Context context,String DBNAME,int VERSION) {// 定义构造函数

        super(context, DBNAME, null, VERSION);// 重写基类的构造函数
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// 创建数据库

        // 创建支出信息表
        db.execSQL(CREATE_TABLE_OUTACCOUNT);

        // 创建收入信息表
        db.execSQL(CREATE_TABLE_INACCOUNT);

        // 创建用户名密码表
        db.execSQL(CREATE_TABLE_USERNAME_PASSWORD);

        // 创建密保问题表
        db.execSQL(CREATE_TABLE_PASSWORD_SECURITY);

        // 创建便签信息表
        db.execSQL(CREATE_TABLE_FLAG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)// 覆写基类的onUpgrade方法，以便数据库版本更新
    {
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public static boolean checkDataBase(String DB_NAME){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * 删除数据库
     *
     * @param context
     * @return
     */
    public static boolean deleteDatabase(Context context,String dbName) {
        return context.deleteDatabase(dbName);
    }
}
