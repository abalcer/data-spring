package com.brainacademy.swing.ui;

import com.brainacademy.swing.ui.forms.EmployeeListForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

@Component
public class MainFrame
        extends JFrame {

    private EmployeeListForm employeeListForm;

    @Autowired
    public MainFrame(EmployeeListForm employeeListForm)
            throws HeadlessException {

        this.employeeListForm = employeeListForm;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 600));
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        getContentPane().add(employeeListForm.getRootPanel());
        pack();
    }

    public void showForm() {
        setVisible(true);
    }
}
