package com.brainacademy.swing.ui.dialogs;

import com.brainacademy.data.model.Department;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class AddDepartmentDialog
        extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField numberTextField;
    private JTextField nameTextField;

    private Department department;

    public AddDepartmentDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(300, 200));
        setTitle("Add Department");

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        department = new Department();
        department.setNumber(numberTextField.getText());
        department.setName(nameTextField.getText());
        dispose();
    }

    private void onCancel() {
        department = null;
        dispose();
    }

    public static void main(String[] args) {
        AddDepartmentDialog dialog = new AddDepartmentDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    public Department getDepartment() {
        return department;
    }
}
