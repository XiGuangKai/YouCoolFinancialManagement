package com.example.financialmanagement.database;

/**
 * Created by wangdy11 on 2017/7/14.
 */

public class UsernamePwd {
    private String username;// 定义字符串，表示用户名字
    private String password;// 定义字符串，表示用户密码

    public UsernamePwd()// 默认构造函数
    {
        super();
    }

    public UsernamePwd(String password)// 默认构造函数
    {
        super();
        this.password = password;// 为密码赋值
    }

    public UsernamePwd(String password,String username)// 定义有参构造函数
    {
        super();
        this.username = username;// 为用户名赋值
        this.password = password;// 为密码赋值
    }

    public String getPassword()// 定义密码的可读属性
    {
        return password;
    }

    public void setPassword(String password)// 定义密码的可写属性
    {
        this.password = password;
    }

    public String getUsername()// 定义用户名的可读属性
    {
        return username;
    }

    public void setUsername(String username)// 定义用户名的可写属性
    {
        this.username = username;
    }

}
