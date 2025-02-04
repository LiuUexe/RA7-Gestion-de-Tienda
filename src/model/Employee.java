package model;
 
import main.Logable;
 
public class Employee extends Person implements Logable
{
    final private int EMPLOYEE_ID = 123;
    final private String PASSWORD = "test";
    private int employeeId;
    private String password;
 
    public Employee(String name, int employeeID, String password)
    {
        super(name);
        this.employeeId = employeeID;
        this.password = password;
    }
 
    @Override
    public boolean login(int employee, String password)
    {
        if (employee == EMPLOYEE_ID && password.equals(PASSWORD))
        {
            System.out.println("Login successful");
            System.out.println();
            return true;
        }
        else
        {
            System.out.println("Wrong login information");
            System.out.println();
            return false;
        }
    }
}