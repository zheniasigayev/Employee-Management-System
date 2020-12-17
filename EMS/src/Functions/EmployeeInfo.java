/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functions;

/**
 *
 */
/*

EmployeeInfo Class

Inherited by:
 - FullTimeEmployee
 - PartTimeEmployee

This class is the super class for holding employee information.

*/

public class EmployeeInfo {

    // CONSTANTS
    
    protected String DATA_SEPARATOR = ",";
    
    // ATTRIBUTES

    private int empNum;
    private String firstName;
    private String lastName;
    private String sex;
    private String workLoc;
    private double deductRate; // e.g. 21 for 21%

    // CONSTRUCTORS

    public EmployeeInfo(int empNum, String firstName, String lastName, String sex, String workLoc, double deductRate)
    {
        this.empNum = empNum;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.workLoc = workLoc;
        this.deductRate = deductRate;
    }

    // METHODS

    @Override
    public String toString()
    {
        if (firstName.isEmpty())
            firstName = " ";
        if (lastName.isEmpty())
            lastName = " ";
        if (sex.isEmpty())
            sex = " ";
        if (workLoc.isEmpty())
            workLoc = " ";
        
        return String.valueOf(empNum) + DATA_SEPARATOR + firstName + DATA_SEPARATOR + lastName + DATA_SEPARATOR + sex + DATA_SEPARATOR + workLoc + DATA_SEPARATOR + String.valueOf(deductRate);
    }

    public double getAnnualGrossIncome()
    {
        return 0;
    }
    public double getAnnualDeduction() 
    {
        return getAnnualGrossIncome() * (deductRate/100);
    }
    public double getAnnualNetIncome()
    {
        return getAnnualGrossIncome() - getAnnualDeduction();
    }


    public int getEmpNum() 
    {
        return empNum;
    }

    public void setEmpNum(int empNum)
    {
        this.empNum = empNum;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getWorkLoc()
    {
        return workLoc;
    }

    public void setWorkLoc(String workLoc)
    {
        this.workLoc = workLoc;
    }

    public double getDeductRate()
    {
        return deductRate;
    }

    public void setDeductRate(double deductRate)
    {
        this.deductRate = deductRate;
    }
    
}