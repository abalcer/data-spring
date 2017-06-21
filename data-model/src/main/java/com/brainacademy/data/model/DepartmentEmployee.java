package com.brainacademy.data.model;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "dept_emp")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.employee", joinColumns = @JoinColumn(name = "emp_no")),
        @AssociationOverride(name = "primaryKey.department", joinColumns = @JoinColumn(name = "dept_no"))})
public class DepartmentEmployee {

    @EmbeddedId
    private DepartmentEmployeeId primaryKey = new DepartmentEmployeeId();

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    public DepartmentEmployeeId getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(DepartmentEmployeeId primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Transient
    public Employee getEmployee() {
        return primaryKey.getEmployee();
    }

    public void setEmployee(Employee employee) {
        primaryKey.setEmployee(employee);
    }

    @Transient
    public Department getDepartment() {
        return primaryKey.getDepartment();
    }

    public void setDepartment(Department department) {
        primaryKey.setDepartment(department);
    }
}
