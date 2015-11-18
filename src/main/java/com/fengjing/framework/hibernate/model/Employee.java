package com.fengjing.framework.hibernate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * ��Ա��Ϣʵ��
 * 
 * @author scott
 *
 */
@Entity
@Table(name = "t_employee", catalog = "hibernate4")
public class Employee {

    /**
     * ��Ա���
     */
    private int id;

    /**
     * ��Ա����
     */
    private String empname;

    /**
     * ��Ա�Ա�
     */
    private String sex;

    /**
     * ��Աסַ
     */
    private String location;

    /**
     * �ù�Ա���ڲ���
     */
    private Department department;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @ManyToOne()
    @Cascade(value = { CascadeType.ALL })
    @JoinColumn(name = "deptid")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
