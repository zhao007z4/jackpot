package com.zzy.jackpot;

/**
 * Created by zerozhao on 2016/12/11.
 */
public class JackpotProduct {
    private int id;
    private int iRes;
    private String name;

    public JackpotProduct(int res, int id)
    {
        this.iRes = res;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getiRes() {
        return iRes;
    }

    public void setiRes(int iRes) {
        this.iRes = iRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

