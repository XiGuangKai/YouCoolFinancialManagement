package com.example.financialmanagement.database;

/**
 * Created by wangdy11 on 2017/7/14.
 */

public class PasswordSecurity {
    private String mLikeNumber;// 定义字符串，表示喜欢的数字
    private String mGoodFriendName;// 定义字符串，表示好朋友的名字
    private String mLikeFood;// 定义字符串，表示喜欢的食物

    public PasswordSecurity(String mLikeNumber,String mGoodFriendName,String mLikeFood) //构造函数
    {
        super();
        this.mLikeNumber = mLikeNumber;
        this.mGoodFriendName = mGoodFriendName;
        this.mLikeFood = mLikeFood;
    }

    public String getLikeNumber()
    {
        return mLikeNumber;
    }

    public void setLikeNumber(String mLikeNumber)
    {
        this.mLikeNumber = mLikeNumber;
    }

    public String getGoodFriendName()
    {
        return mGoodFriendName;
    }

    public void setGoodFriendName(String mGoodFriendName)
    {
        this.mGoodFriendName = mGoodFriendName;
    }

    public String getLikeFood()
    {
        return mLikeFood;
    }

    public void setLikeFood(String mLikeFood)
    {
        this.mLikeFood = mLikeFood;
    }
}
