/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeemanagementsystem;

import Functions.EmployeeInfo;
import Functions.FullTimeEmployee;
import Functions.PartTimeEmployee;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 */
public class UI {
    
    // Necessary to make UI as a Singleton
    
    private static UI instance;
    
    public static UI getInstance() {
        return instance == null ? instance = new UI() : instance;
    }
    
    // ATTRIBUTES
    
    private EmployeeManagementSystem mManager;
    private AddEmployeeForm mAddForm;
    private Home mMainView;
    private EmployeeInfo mCurrentEmployee;
    private int mSelectedRow;
    
    private boolean mEditing = false;
    
    // METHODS
    
    public void setManager(EmployeeManagementSystem manager) {
        System.out.println(manager);
        mManager = manager;
    }
    
    public void beginFormAddEmployee() {
        
        mAddForm = new AddEmployeeForm();
        mAddForm.setVisible(true);
        mAddForm.setAlwaysOnTop(true);
        mAddForm.setResizable(false);
        mAddForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mAddForm.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmCloseFormAddEmployee();
            }
        });
        
        mMainView.setEnabled(false);
        mMainView.setFocusable(false);
        
    }
    
    private void confirmCloseFormAddEmployee() {
        closeFormAddEmployee();
            }
    
    private void closeFormAddEmployee() {
        mEditing = false;
        mAddForm.dispose();
        mMainView.setEnabled(true);
        mMainView.setFocusable(true);
    }
    
    public void cancelFormAddEmployee() {
        confirmCloseFormAddEmployee();
    }
    
    public void addFormAddEmployee() {
        // TODO: get info from form and add employee
        
        String numField = mAddForm.getFieldEmpNum();
        String firstName = mAddForm.getFieldFirstName();
        String lastName = mAddForm.getFieldLastName();
        String sex = mAddForm.getFieldSex();
        String workLoc = mAddForm.getFieldWorkLocation();
        String deductRateField = mAddForm.getFieldDeductRate();
        String type = mAddForm.getFieldEmploymentType();
   
        if (firstName.contains(","))
            firstName = firstName.replace(",", "");
        if (lastName.contains(","))
            lastName = lastName.replace(",", "");
        if (sex.contains(","))
            sex = sex.replace(",", "");
        if (workLoc.contains(","))
            workLoc = workLoc.replace(",", "");
        
        String incomeField = mAddForm.getFieldIncome();
        String hoursPerWeekField = mAddForm.getFieldHoursPerWeek();
        String weeksPerYearField = mAddForm.getFieldWeeksPerYear();
        
        int EmpNum = 0;
        double deductRate = 0;
        double income = 0;
        double hoursPerWeek = 0;
        double weeksPerYear = 0;
        
        try {
            EmpNum = Integer.parseInt(numField);
            
            deductRate = Double.parseDouble(deductRateField);
            
            income = Double.parseDouble(incomeField);
            
            if (!type.equals("FT")) {
                hoursPerWeek = Double.parseDouble(hoursPerWeekField);
                weeksPerYear = Double.parseDouble(weeksPerYearField);
            }
        } catch(NumberFormatException e) {
            
            String dialogAppend = ", Hourly Wage, Hours Per Week, and Weeks Per Year";
            if (type.equals("FT")) {
                dialogAppend = ", and Annual Salary";
            }
            
            JOptionPane.showConfirmDialog(mAddForm, 
                "Make sure the: Employee Number, Deduct Rate" + dialogAppend + " fields are numbers. ", "", 
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        
        boolean success = false;
        
        if (mEditing) {
            if (type.equals("FT")) {

                success = mManager.editEmployee(mCurrentEmployee.getEmpNum(), EmpNum, firstName, lastName, sex, workLoc, deductRate, income);

            }
            else { 

                success = mManager.editEmployee(mCurrentEmployee.getEmpNum(), EmpNum, firstName, lastName, sex, workLoc, deductRate, income, hoursPerWeek, weeksPerYear);

            }
            if (!success) {
                JOptionPane.showConfirmDialog(mAddForm, 
                    "Editing employee failed. (Employee may not be in database anymore)", "Edit failed!", 
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            }
            else {
                closeFormAddEmployee();
            }
        }
        else {
            if (type.equals("FT")) {

                success = mManager.addEmployee(EmpNum, firstName, lastName, sex, workLoc, deductRate, income);

            }
            else { 

                success = mManager.addEmployee(EmpNum, firstName, lastName, sex, workLoc, deductRate, income, hoursPerWeek, weeksPerYear);

            }

            if (!success) {
                JOptionPane.showConfirmDialog(mAddForm, 
                    "Employee with employee number already exists!", "Employee exists!", 
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
            }
            else {
                closeFormAddEmployee();
            }
        }
        
        
    }
    
    public void loadView() {
        mMainView = new Home();
        mMainView.setVisible(true);
        mMainView.setResizable(false);
        mMainView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mMainView.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                
                        System.exit(0);
                    }      
        });
        
        mMainView.getTableDatabase().getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent event) {
                // Selected element in database table
                int selectedRow = mMainView.getTableDatabase().getSelectedRow();
                if (selectedRow < 0) {
                    return;
                }
                mSelectedRow = selectedRow;
                int EmpNum = (int) mMainView.getTableDatabase().getValueAt(mSelectedRow, 0);
                populateEmployeeInfo(mManager.searchEmployee(EmpNum), true);
            }
        });
        

        
    }
    
    public void clearTable() {
        mMainView.clearTable();
    }
    
    public void populateTable(EmployeeInfo employee) {

        Object[] row = new Object[5];
        row[0] = employee.getEmpNum();
        if (employee instanceof FullTimeEmployee)
            row[1] = "Full Time";
        else if (employee instanceof PartTimeEmployee)
            row[1] = "Part Time";
        else
            row[1] = "null";
        row[2] = employee.getFirstName();
        row[3] = employee.getLastName();
        row[4] = employee.getWorkLoc();

        mMainView.addRowToTable(row);
        
    }
    
    public void populateEmployeeInfo(EmployeeInfo employee, boolean isEmployee) {
        
        mCurrentEmployee = employee;
        
        mMainView.setVarEmpNum(employee.getEmpNum(), isEmployee);
        mMainView.setVarFirstName(employee.getFirstName());
        mMainView.setVarLastName(employee.getLastName());
        mMainView.setVarSex(employee.getSex());
        mMainView.setVarWorkLoc(employee.getWorkLoc());
        
        String type = "null";
        if (employee instanceof FullTimeEmployee) {
            type = "Full Time";
            mMainView.setLabelIncome("Annual Salary");
            mMainView.setPartTimeLabels(false);
        }
        else if (employee instanceof PartTimeEmployee) {
            type = "Part Time";
            mMainView.setLabelIncome("Hourly Wage");
            mMainView.setPartTimeLabels(true);
        }
        mMainView.setVarEmploymentType(type);
        
        mMainView.setVarDeductRate(employee.getDeductRate());
        
        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee fullTimeEmp = (FullTimeEmployee) employee;
            mMainView.setVarIncome(fullTimeEmp.getYearlySalary());
            mMainView.setVarHoursPerWeek(0);
            mMainView.setVarWeeksPerYear(0);
        }
        else if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee partTimeEmp = (PartTimeEmployee) employee;
            mMainView.setVarIncome(partTimeEmp.getHourlyWage());
            mMainView.setVarHoursPerWeek(partTimeEmp.getHoursPerWeek());
            mMainView.setVarWeeksPerYear(partTimeEmp.getWeeksPerYear());
        }
        
        mMainView.setVarGrossIncome(employee.getAnnualGrossIncome());
        mMainView.setVarDeduction(employee.getAnnualGrossIncome() - employee.getAnnualNetIncome());
        mMainView.setVarNetIncome(employee.getAnnualNetIncome());
        
    }
    
    public void removeCurrentEmployee() {
        
        if (mCurrentEmployee == null)
            return;
        
        mManager.removeEmployee(mCurrentEmployee);
        
        resetEmployeeInfo();
        mCurrentEmployee = null;
        
        reloadTable();
        
        if (mSelectedRow >= mMainView.getTableDatabase().getRowCount()) {
            mSelectedRow = mMainView.getTableDatabase().getRowCount() - 1;
        }
        if (mSelectedRow >= 0) {
            mMainView.selectRow(mSelectedRow);
        }
        
    }
    
    public void editCurrentEmployee() {
        
        if (mCurrentEmployee == null)
            return;
        
        int num = mCurrentEmployee.getEmpNum();
        String firstName = mCurrentEmployee.getFirstName();
        String lastName = mCurrentEmployee.getLastName();
        String sex = mCurrentEmployee.getSex();
        String workLoc = mCurrentEmployee.getWorkLoc();
        double deductRate = mCurrentEmployee.getDeductRate();
        
        if (mCurrentEmployee instanceof FullTimeEmployee) {
            FullTimeEmployee fullEmp = (FullTimeEmployee) mCurrentEmployee;
            double yearlySalary = fullEmp.getYearlySalary();
            
            beginFormAddEmployee();
            
            mAddForm.fillFieldsWithEmployee(num, firstName, lastName, sex, workLoc, deductRate, yearlySalary);
        }
        else if (mCurrentEmployee instanceof PartTimeEmployee) {
            PartTimeEmployee partEmp = (PartTimeEmployee) mCurrentEmployee;
            double hourlyWage = partEmp.getHourlyWage();
            double hoursPerWeek = partEmp.getHoursPerWeek();
            double weeksPerYear = partEmp.getWeeksPerYear();
            
            beginFormAddEmployee();
            
            mAddForm.fillFieldsWithEmployee(num, firstName, lastName, sex, workLoc, deductRate, hourlyWage, hoursPerWeek, weeksPerYear);
        }
        else {
            System.out.println("Employee is a generic employee info (or some other random type)! This should not be happening.");
            return; 
        }
        
        mEditing = true;
        
    }
    
    public void searchEmployees(String term, String type) {
        
        if (type.equals("Show All"))
            mManager.reloadFullDatabase();
        else
            mManager.searchDatabase(term, type);
        
    }
    
    public void reloadTable() {
        
        mMainView.searchDatabase();
        
    }
    
    public void resetEmployeeInfo() {
        populateEmployeeInfo(new PartTimeEmployee(-1, "", "", "", "", 0.0, 0.0, 0.0, 0.0), false);
        mCurrentEmployee = null;
    }
    
    public boolean saveDatabase() {
        return mManager.saveDatabaseToFile();
    }
    
    public void openDatabase() {
        if (mManager.readDatabaseFromFile())
            resetEmployeeInfo();
    }
    
    
}
