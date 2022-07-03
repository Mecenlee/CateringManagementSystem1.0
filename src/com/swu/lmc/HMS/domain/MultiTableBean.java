package com.swu.lmc.HMS.domain;

import java.util.Date;

/**
 * @author 李敏灿
 * @version 1.0
 * 这是一个Javabean 可以和多张表进行对应
 */

public class MultiTableBean extends Bill {
    //增加一个来自menu表的name
    private String name;
    //无参构造器
    public MultiTableBean() {
    }
    //有参构造器
    public MultiTableBean(Integer id, String billId, Integer menuId, Integer nums, Double money, Integer diningTableId, Date billDate, String state, String name) {
        super(id, billId, menuId, nums, money, diningTableId, billDate, state);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString()+
                "\t\t" + name;
    }
}
