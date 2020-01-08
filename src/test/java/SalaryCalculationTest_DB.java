import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import core.SalaryCalculation;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Assert;
import org.junit.Test;


public class SalaryCalculationTest_DB {
    public static final String TABLE_LOGIN = "salarydetails";
    private FlatXmlDataSet loadedDataSet;
    private SalaryCalculation salaryCalicutation;
    private static Connection jdbcConnection;

    /** * Provide a connection to the database * @return IDatabaseConnection */
    protected IDatabaseConnection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        jdbcConnection = DriverManager.getConnection(
                "jdbc:h2:file:~/dbdata/test", "sa", "");
        return new DatabaseConnection(jdbcConnection);
    }

    /** * Load the data which will be inserted for the test * @return IDataSet */
    protected IDataSet getDataSet() throws Exception {
        //createTables();
        return new FlatXmlDataSetBuilder().build(new FileInputStream(System.getProperty("user.dir")+"\\target\\classes\\dbunitData.xml"));
    }

    /** * Test case for calculator * positive scenario---Valid Employee */
    @Test
    public void testCalculator1() throws SQLException {
        salaryCalicutation = new SalaryCalculation();
        int salary = salaryCalicutation.calculator("24356A");
        System.out.println(salary);
        Assert.assertEquals(13245, salary);
    }


    /** *Test case for calculator *negative scenario---InValid Employee */
    @Test
    public void testCalculator2() throws SQLException {
        salaryCalicutation = new SalaryCalculation();
        salaryCalicutation.updateSalary("24356A",5000);
        int salary = salaryCalicutation.calculator("24356A");
        System.out.println(salary);
        Assert.assertEquals(8245, salary);
    }

    /** * Test case for calculator * positive scenario---Valid Employee */
    @Test
    public void testCalculator3() throws SQLException {
        salaryCalicutation = new SalaryCalculation();
        int salary = salaryCalicutation.calculator("24356A");
        System.out.println(salary);
        Assert.assertEquals(13245, salary);
    }

    /** *Test case for calculator *negative scenario---InValid Employee */
    @Test
    public void testCalculatorNeg() throws SQLException {
        salaryCalicutation = new SalaryCalculation();
        int salary = salaryCalicutation.calculator("24356B");
        Assert.assertEquals(0, salary);
    }

    private void createTables () {
        try {
            //Create tables if not exist
            Statement stmt = DriverManager.getConnection(
                    "jdbc:h2:file:~/dbdata/test", "sa", "").createStatement();
            //String sql =  "SELECT id FROM PAGE_DATA LIMIT 1";
            String sql = "drop table IF EXISTS salarydetails";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS salarydetails " +
                    "(EmpID VARCHAR(255) not NULL, " +
                    " Salary INT, " +
                    " Bonus INT, " +
                    " Increment INT, " +
                    " PRIMARY KEY ( EmpID ))";

            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}