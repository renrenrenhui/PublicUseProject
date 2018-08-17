package com.example.xiaoniu.publicuseproject.recyclerView;

public class Category {
    /**
     * 样式的声明
     */
    public static final int SECOND_TYPE = 0;
    public static final int THIRD_TYPE = 1;
    /**
     * 代表名称
     */
    private String categoryName;
    /**
     * 区别布局类型
     */
    private int type;

    public Category(String name, int type) {
        this.categoryName = name;
        this.type = type;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getType() {
        return type;
    }

}