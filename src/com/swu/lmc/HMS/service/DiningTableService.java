package com.swu.lmc.HMS.service;

import com.swu.lmc.HMS.dao.DiningTableDAO;
import com.swu.lmc.HMS.domain.DiningTable;

import java.util.List;

/**
 * @author 李敏灿
 * @version 1.0
 */

public class DiningTableService {
    //定义一个 DiningTableDAO 对象
    private DiningTableDAO diningTableDAO = new DiningTableDAO();

    //返回所有餐桌信息
    public List<DiningTable> list() {
        return diningTableDAO.queryMulti("select id,state from diningTable", DiningTable.class);
    }

    //根据 id ，查询对应的餐桌DiningTable 对象
    //如果返回null就是表示这个餐桌不存在
    public DiningTable getDiningTableById(int id) {
        return diningTableDAO.querySingle("select * from diningTable where id = ?", DiningTable.class, id);
    }

    //如果餐桌可以预定，调用方法，对其状态进行更新（包括预定人的姓名和电话）
    public boolean orderDiningTable(int id, String orderName, String orderTel) {
        int update = diningTableDAO.update("update diningTable set state='已经预定',orderName=?,orderTel=? where id=?",
                orderName, orderTel, id);
        return update > 0;
    }

    //需要提供一个更新 餐桌状态的办法
    public boolean updateDiningTableState(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state=? where id=?", state, id);
        return update > 0;
    }

    //提供方法，将指定的餐桌设置未空闲状态
    public boolean updateDiningTableToFree(int id, String state) {
        int update = diningTableDAO.update("update diningTable set state=?,orderName='',orderTel=''where id=?", state, id);
        return update > 0;
    }

}
