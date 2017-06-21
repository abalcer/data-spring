package com.brainacademy.swing.ui.dialogs;

import com.brainacademy.data.model.Employee;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.impl.JDatePickerImpl;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class EditEmployeeDialog
        extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JPanel birthdatePanel;
    private JPanel hiredatePanel;
    private JComboBox<String> genderComboBox;

    private Employee employee;

    public EditEmployeeDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(300, 250));

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        genderComboBox.addItem("M");
        genderComboBox.addItem("F");

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (employee == null) {
            employee = new Employee();
            employee.setFirstName(firstNameTextField.getText());
            employee.setLastName(lastNameTextField.getText());

            DateModel<?> dateModel = ((JDatePickerImpl) birthdatePanel).getModel();
            Calendar calendar = Calendar.getInstance();
            calendar.set(dateModel.getYear(), dateModel.getMonth(), dateModel.getDay());
            employee.setBirthDate(calendar.getTime());

            dateModel = ((JDatePickerImpl) hiredatePanel).getModel();
            calendar.set(dateModel.getYear(), dateModel.getMonth(), dateModel.getDay());
            employee.setHireDate(calendar.getTime());

            employee.setGender((String) genderComboBox.getSelectedItem());
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        EditEmployeeDialog dialog = new EditEmployeeDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        birthdatePanel = (JDatePickerImpl) new JDateComponentFactory().createJDatePicker();
        hiredatePanel = (JDatePickerImpl) new JDateComponentFactory().createJDatePicker();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void bind(Employee employee) {
        this.employee = employee;
        if (employee != null) {
            setTitle("Edit Employee");
            firstNameTextField.setText(employee.getFirstName());
            lastNameTextField.setText(employee.getLastName());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(employee.getBirthDate());
            ((JDatePickerImpl) birthdatePanel).getModel().setDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            calendar.setTime(employee.getHireDate());
            ((JDatePickerImpl) hiredatePanel).getModel().setDate(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            genderComboBox.setSelectedItem(employee.getGender());
        } else {
            setTitle("Add Employee");
        }
    }
}
