package stepdefs;

import core.DBHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;


import java.io.FileInputStream;
import java.sql.*;

import core.SalaryCalculation;

public class StepDefinitions extends DatabaseTestCase {
    public static final String TABLE_LOGIN = "salarydetails";
    private FlatXmlDataSet loadedDataSet;
    private SalaryCalculation salaryCalculation;
    private static Connection jdbcConnection;
    private ResultSet resultSet;
    private DBHelper dh = new DBHelper();

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
    @Given("execute query:")
    public void executeQuery(String query) throws SQLException {
        resultSet = dh.execQuery(query);
    }

    //Algorithm can be improved by iterating rows until required number is reached
    @Then("number of lines is more than {int}")
    public void getLinesNumber (int expectedNumber) throws SQLException {
        Integer rows = null;

        if (resultSet.last()) {
            rows = resultSet.getRow();
            // Move to beginning
            resultSet.beforeFirst();
        }
        if (rows == null)
            throw new RuntimeException("No rows returned. Check query and db.");
        if (rows <= expectedNumber)
            throw new RuntimeException("Row number is " + rows + ", but should be more than " + expectedNumber);
    }

    @Then("count is more than {int}")
    public void getCount (int expectedValue) throws SQLException {
        Integer count=null;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        if (count == null)
            throw new RuntimeException("No rows returned. Check query and db.");
        if (count <= expectedValue)
            throw new RuntimeException("Count is " + count + ", but should be more than " + expectedValue);
    }

    /** *Test case for calculator *negative scenario---
     *
     */

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
