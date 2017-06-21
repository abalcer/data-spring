package com.brainacademy.swing.ui.forms;

import com.brainacademy.data.model.Department;
import com.brainacademy.data.model.Employee;
import com.brainacademy.data.service.DepartmentService;
import com.brainacademy.data.service.EmployeeService;
import com.brainacademy.swing.ui.dialogs.AddDepartmentDialog;
import com.brainacademy.swing.ui.dialogs.EditEmployeeDialog;
import com.brainacademy.swing.ui.model.EmployeeTableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@Component
public class EmployeeListForm {
    private final EmployeeTableModel employeeTableModel;

    private JPanel rootPanel;
    private JTable employeeTable;
    private JComboBox<Department> departmentsComboBox;
    private JTextField textFieldName;
    private JButton nextButton;
    private JButton prevButton;
    private JLabel pageableLabel;
    private JButton addDepartmentButton;
    private JButton addEmployeeButton;
    private JButton editEmployeeButton;
    private JButton deleteEmployeeButton;

    private EmployeeService employeeService;
    private DepartmentService departmentService;

    private Pageable pageable;

    @Autowired
    public EmployeeListForm(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;

        pageable = new PageRequest(0, 30);

        nextButton.addActionListener(e -> this.goNext());
        prevButton.addActionListener(e -> this.goPrev());

        employeeTableModel = new EmployeeTableModel();
        employeeTable.setModel(employeeTableModel);
        employeeTable.getSelectionModel().addListSelectionListener(event -> onTableRowSelected());

        addDepartmentButton.addActionListener(e -> this.addDepartment());
        addEmployeeButton.addActionListener(e -> this.addEmployee());
        editEmployeeButton.addActionListener(e -> this.editEmployee());
        deleteEmployeeButton.addActionListener(e -> this.deleteEmployee());
        textFieldName.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                reload();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reload();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reload();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void reload() {
        pageable = pageable.first();
        updateData();
    }

    private void updateData() {
        Department department = (Department) departmentsComboBox.getSelectedItem();
        if (department == null) {
            employeeTableModel.bind(Collections.emptyList());
            disablePager();
            return;
        }

        String employeeName = textFieldName.getText();
        Page<Employee> employees = (Page<Employee>) employeeService.searchEmployees(department.getNumber(), employeeName, pageable);

        employeeTableModel.bind(employees.getContent());

        int page = employees.getNumber();
        int totalPages = employees.getTotalPages();

        if (totalPages == 0) {
            disablePager();
            return;
        }

        pageableLabel.setText(String.format("%d of %d", page + 1, totalPages));
        prevButton.setEnabled(page > 0);
        nextButton.setEnabled(page < totalPages - 1);
    }

    private void disablePager() {
        pageableLabel.setText("");
        prevButton.setEnabled(false);
        nextButton.setEnabled(false);
    }

    private void createUIComponents() {
        departmentsComboBox = new JComboBox<>();
        departmentsComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value != null) {
                    setText(((Department) value).getName());
                }
                return this;
            }
        });
        initDepartmentData();

        departmentsComboBox.addActionListener(e -> onDepartmentSelect());
    }

    private void initDepartmentData() {
        departmentsComboBox.removeAllItems();

        Department emptyDepartment = new Department();
        emptyDepartment.setName("Select Department");
        departmentsComboBox.addItem(emptyDepartment);

        Iterable<Department> departments = employeeService.getAvailableDepartments();
        departments.forEach(departmentsComboBox::addItem);
    }

    private void onTableRowSelected() {
        int selectedRowsCount = employeeTable.getSelectedRowCount();
        editEmployeeButton.setEnabled(selectedRowsCount > 0);
        deleteEmployeeButton.setEnabled(selectedRowsCount > 0);
    }

    private void onDepartmentSelect() {
        if (departmentsComboBox.getSelectedIndex() > 0) {
            textFieldName.setEnabled(true);
            addEmployeeButton.setEnabled(true);
        } else {
            addEmployeeButton.setEnabled(false);
            textFieldName.setEnabled(false);
            textFieldName.setText(null);
        }
        reload();
    }

    private void goNext() {
        pageable = pageable.next();
        updateData();
    }

    private void goPrev() {
        pageable = pageable.previousOrFirst();
        updateData();
    }

    private void addDepartment() {
        AddDepartmentDialog dialog = new AddDepartmentDialog();
        dialog.setLocationRelativeTo(rootPanel);
        dialog.setVisible(true);
        if (dialog.getDepartment() != null) {
            Department department = departmentService.save(dialog.getDepartment());
            initDepartmentData();
            departmentsComboBox.setSelectedItem(department);
        }
    }

    private void deleteEmployee() {
        Employee employee = getSelectedEmployee();
        if (employee != null) {
            employeeService.delete(employee);
            updateData();
        }
    }

    private void editEmployee() {
        Employee employee = getSelectedEmployee();
        if (employee != null) {
            showEmployeeDialog(employee);
        }
    }

    private void addEmployee() {
        showEmployeeDialog(null);
    }

    private void showEmployeeDialog(Employee employee) {
        EditEmployeeDialog dialog = new EditEmployeeDialog();
        dialog.bind(employee);
        dialog.setLocationRelativeTo(rootPanel);
        dialog.setVisible(true);
        Employee newEmployee = dialog.getEmployee();
        if (newEmployee != null) {
            employeeService.save(newEmployee, (Department) departmentsComboBox.getSelectedItem());
            updateData();
        }
    }

    private Employee getSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            return employeeTableModel.getItem(selectedRow);
        }
        return null;
    }
}
