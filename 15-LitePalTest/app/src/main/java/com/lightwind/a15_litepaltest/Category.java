package com.lightwind.a15_litepaltest;

import org.litepal.crud.DataSupport;

/**
 * 功能：
 * 作者：刘洋
 * 时间：2017/8/22
 */

public class Category extends DataSupport{
    private int id;
    private String categoryName;
    private int categoryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
