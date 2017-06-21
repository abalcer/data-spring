package com.brainacademy.swing.ui.model;


import com.brainacademy.data.model.Employee;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EmployeeTableModel
        extends AbstractTableModel {
    private static final String[] COLUMNS = {"#", "First Name", "Last Name", "Birth Date", "Hire Date"};

    private List<Employee> employees = Collections.emptyList();

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy");

        switch (columnIndex) {
            case 0:
                return employee.getId();
            case 1:
                return employee.getFirstName();
            case 2:
                return employee.getLastName();
            case 3:
                return simpleDateFormat.format(employee.getBirthDate());
            case 4:
                return simpleDateFormat.format(employee.getHireDate());
        }
        return null;
    }

    public void bind(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
        fireTableDataChanged();
    }

    public Employee getItem(int row) {
        return employees.get(row);
    }
}
