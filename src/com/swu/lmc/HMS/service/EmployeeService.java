package com.swu.lmc.HMS.service;

import com.swu.lmc.HMS.dao.EmployeeDAO;
import com.swu.lmc.HMS.domain.Employee;

/**
 * @author 李敏灿
 * @version 1.0
 * 该类完成对Employee表的各项操作（通过调用Employee对象完成）
 */

public class EmployeeService {
    //定义一个 EmployeeDAO 属性
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    //方法，根据 empId 和 pwd 返回一个 Employee 对象
    //如果查询不到，就返回 null
    public Employee  getEmployeeByIdAndPwd(String empId,String pwd){
        return employeeDAO.querySingle(
                "select * from employee where empId=? and pwd=md5(?)",
                Employee.class, empId, pwd);
    }
}
