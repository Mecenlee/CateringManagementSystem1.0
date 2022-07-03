package com.swu.lmc.HMS.service;

import com.swu.lmc.HMS.dao.BillDAO;
import com.swu.lmc.HMS.dao.MultiTableDAO;
import com.swu.lmc.HMS.domain.Bill;
import com.swu.lmc.HMS.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

/**
 * @author 李敏灿
 * @version 1.0
 * 处理和账单相关的业务逻辑
 */

public class BillService {
    //定义一个 BillDAO 属性
    private BillDAO billDAO = new BillDAO();
    //定义MenuService属性
    private MenuService menuService = new MenuService();
    //定义一个DiningTableService属性
    private DiningTableService diningTableService = new DiningTableService();
    //定义一个MultiTableDAO
    private MultiTableDAO multiTableDAO = new MultiTableDAO();
    //编写点餐的方法
    //1.生成账单
    //2.需要更新对应餐桌的状态
    //3.如果成功返回true ，否则返回false
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        //生成一个账单号，UUID
        String billID = UUID.randomUUID().toString();

        //将账单生成到bill表，要求直接计算
        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未结账')",
                billID, menuId, nums, menuService.getMenuById(menuId).getPrice() * nums, diningTableId
        );
        if (update <= 0) {
            return false;
        }
        //需要更新对应餐桌的状态
        return diningTableService.updateDiningTableState(diningTableId, "就餐中");

    }

    //返回所有账单，提供给 View 使用
    public List<Bill> list() {
        return billDAO.queryMulti("select * from bill", Bill.class);
    }

    //返回所有账单并带有菜品，提供给 View 使用
    public List<MultiTableBean> list2() {
        return multiTableDAO.queryMulti("select bill.*,`name` from bill,menu " +
                "where bill.menuId = menu.id", MultiTableBean.class);
    }

    //看某个餐桌是否有未结算的账单
    public boolean hasPayBillByDiningTableId(int diningTableId) {
        Bill bill = billDAO.querySingle("select * from bill where diningTableId=? and state = '未结账' limit 0,1", Bill.class, diningTableId);
        return bill != null;
    }

    //完成结账【如果餐桌存在，而且该桌有未结算的账单】
    public boolean payBill(int diningTableId, String payMode) {
        //1,修改 Bill 表
        int update = billDAO.update("update bill set state=? where diningTableId=? and state='未结账'", payMode, diningTableId);
        if (update <= 0) {//如果更新没有成功就表示失败
            return false;
        }
        //2，修改 diningTable 表
        //注意：不要直接在这里操作，而应该调用DiningTableService方法
        if (!diningTableService.updateDiningTableToFree(diningTableId,"空")){
            return false;
        }
        return true;
    }
}
