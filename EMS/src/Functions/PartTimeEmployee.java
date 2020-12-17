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

PartTimeEmployee class
Superclass: EmployeeInfo

This class holds employee information specific to part time employees.

*/
public class PartTimeEmployee extends EmployeeInfo {

    // ATTRIBUTES

    private double hourlyWage;
    private double hoursPerWeek;
    private double weeksPerYear;

    // CONSTRUCTORS

    public PartTimeEmployee(int empNum, String firstName, String lastName, String sex, String workLoc, double deductRate, double hourlyWage, double hoursPerWeek, double weeksPerYear)
    {
        super(empNum, firstName, lastName, sex, workLoc, deductRate);
        this.hourlyWage = hourlyWage;
        this.hoursPerWeek = hoursPerWeek;
        this.weeksPerYear = weeksPerYear;
        
    }

    // METHODS
    
    @Override
    public String toString()
    {
        return "PT" + DATA_SEPARATOR + super.toString() + DATA_SEPARATOR + String.valueOf(hourlyWage) + DATA_SEPARATOR + String.valueOf(hoursPerWeek) + DATA_SEPARATOR + String.valueOf(weeksPerYear);
    }

    @Override
    public double getAnnualGrossIncome()
    {
        return hourlyWage * hoursPerWeek * weeksPerYear;
    }

    public double getHourlyWage()
    {
        return hourlyWage;
    }
    public void setHourlyWage(double hourlyWage)
    {
        this.hourlyWage = hourlyWage;
    }

    public double getHoursPerWeek()
    {
        return hoursPerWeek;
    }
    public void setHoursPerWeek(double hoursPerWeek)
    {
        this.hoursPerWeek = hoursPerWeek;
    }

    public double getWeeksPerYear()
    {
        return weeksPerYear;
    }
    public void setWeeksPerYear(double weeksPerYear)
    {
        this.weeksPerYear = weeksPerYear;
    }
        
}
