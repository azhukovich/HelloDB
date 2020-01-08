package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalaryCalculation {
    /** * Creating Connection * @return Connection * @throws SQLException */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:file:~/dbdata/test", "sa", "");
    }

    /**
     * * Salary Calculation * @param EmpID string * @return salary * @throws
     * SQLException
     */
    public int calculator(String EmpID) throws SQLException {
        Statement stmt = getConnection().createStatement();
        ResultSet rs = stmt
                .executeQuery("select * from salarydetails where EmpID='"
                        + EmpID + "'");
        int salary = 0;
        int bonus = 0;
        int increment = 0;
        while (rs.next()) {
            salary = rs.getInt("Salary");
            bonus = rs.getInt("Bonus");
            increment = rs.getInt("Increment");
        }
        return (salary + bonus + increment);
    }

    /**
     * * Salary Calculation * @param EmpID string * @return salary * @throws
     * SQLException
     */
    public void updateSalary(String EmpID,int newSalary) throws SQLException {
        Statement stmt = getConnection().createStatement();
        stmt
                .execute("update salarydetails set  Salary="+newSalary+" where EmpID='"
                        + EmpID + "'");
    }
}