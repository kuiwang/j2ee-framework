package com.fengjing.framework.struts2.action;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.fengjing.framework.hibernate.model.Department;
import com.fengjing.framework.hibernate.model.Employee;
import com.fengjing.framework.hibernate.service.DepartmentService;
import com.fengjing.framework.hibernate.service.EmployeeService;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Controller("helloAction")
public class HelloAction extends ActionSupport {

    private EmployeeService employeeService;

    @Resource(name = "employeeServiceImplHibernate4")
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private DepartmentService departmentService;

    @Resource(name = "departmentServiceImplHibernate4")
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public String hello() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.write("<script type='text/javascript'>alert('Hello,Maven!');</script>");
        writer.flush();
        writer.close();
        return null;
    }

    public String save() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        try {

            Employee employee = new Employee();

            employee.setEmpname("����");
            employee.setSex("Ů");
            employee.setLocation("��������");

            Department department = new Department();
            department.setDeptname("���Բ�");

            employee.setDepartment(department);

            employeeService.add(employee);

            writer.write("<script type='text/javascript'>alert('����ɹ�!');</script>");
        } catch (Exception e) {
            writer.write("<script type='text/javascript'>alert('����ʧ��!\t" + e.getMessage()
                    + "');</script>");
        }

        writer.flush();
        writer.close();
        return null;

    }

    public String delete() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        try {

            departmentService.delete(1);

            writer.write("<script type='text/javascript'>alert('ɾ���ɹ�!');</script>");
        } catch (Exception e) {
            writer.write("<script type='text/javascript'>alert('ɾ��ʧ��!\t" + e.getMessage()
                    + "');</script>");
        }

        writer.flush();
        writer.close();
        return null;

    }

    public String print() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("<a href='http://mvnrepository.com'>http://mvnrepository.com</a>");
        writer.flush();
        writer.close();
        return null;

    }

    public String modify() throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();

        try {
            List<Employee> list = employeeService
                    .query(" from com.fengjing.framework.hibernate.model.Employee employee where employee.id = 2 ");
            Employee employee = list.get(0);

            employee.setEmpname("������");
            employee.setSex("��");
            employee.setLocation("�й�����");

            employee.getDepartment().setDeptname("����Ժ");

            employeeService.update(employee);
            writer.write("<script type='text/javascript'>alert('�޸ĳɹ�!');</script>");
        } catch (Exception e) {
            writer.write("<script type='text/javascript'>alert('�޸�ʧ��!\t" + e.getMessage()
                    + "');</script>");
        }

        writer.flush();
        writer.close();
        return null;

    }

}
