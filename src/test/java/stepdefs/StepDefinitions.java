package stepdefs;

import core.DBHelper;


import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.assertj.core.api.SoftAssertions;
import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;


import java.io.FileInputStream;
import java.sql.*;

import core.SalaryCalculation;
import org.dbunit.operation.DatabaseOperation;
import org.testng.Assert;

import static org.assertj.core.api.Assertions.*;

public class StepDefinitions extends DatabaseTestCase {
    public static final String TABLE_LOGIN = "salarydetails";
    private FlatXmlDataSet loadedDataSet;
    private SalaryCalculation salaryCalculation;
    private static Connection jdbcConnection;
    private ResultSet resultSet=null;
    private DBHelper dh = new DBHelper();
    private final SoftAssertions softly = new SoftAssertions();
    private Scenario scenario;

    /** * Provide a connection to the database * @return IDatabaseConnection */

    protected IDatabaseConnection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        jdbcConnection = DriverManager.getConnection(
                "jdbc:h2:file:~/dbdata/test", "sa", "");
        return new DatabaseConnection(jdbcConnection);
    }

    /** * Load the data which will be inserted for the test * @return IDataSet */
    protected IDataSet getDataSet() throws Exception {
        createTables();
        return new FlatXmlDataSetBuilder().build(new FileInputStream(System.getProperty("user.dir")+"\\target\\classes\\dbunitData.xml"));
    }

    @Before()
    public  void beforeTest (Scenario scenario){
        try {

            DatabaseOperation.INSERT.execute(getConnection(), this.getDataSet());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.scenario = scenario;
    }
    /** * Test case for calculator * positive scenario---Valid Employee */
    @Given("execute query:")
    public void executeQuery(String query) throws SQLException {
        resultSet = dh.execQuery(query);
        Assert.assertNotEquals(null,resultSet,"No data returned from DB");
        String output = "<html> <head> <style> table {border-collapse: collapse;} table, th, td {background-color: #e5eaee; border: 1px solid black;}" +
                " .fail {background-color: red;} .pass {background-color: green;} </style> </head><body>\n "+
                "<table class=\"tg\">";
        ResultSetMetaData rsmd = resultSet.getMetaData();

        output = output +"<tr>";
        int colNum = rsmd.getColumnCount();
        for (int i=1;i<=colNum;i++)
            output = output + "    <th>"+rsmd.getColumnName(i)+"</th>";
        output = output +"</tr>";

        while(resultSet.next()){
            output = output +"<tr>";
            for (int i=1;i<=colNum;i++)
            output = output + "<td>" + resultSet.getString(i) + "</td>";
            output = output +"</tr>";
        }
        resultSet.beforeFirst();
        scenario.embed(output.getBytes(),"text/html");
        output = output +"</table></body>";
    }

    //Algorithm can be improved by iterating rows until required number is reached
    @Then("[softcheck] number of lines is more than {int}")
    public void getLinesNumberSoftCheck (int expectedNumber) throws SQLException {
        Integer rows = null;

        if (resultSet.last()) {
            rows = resultSet.getRow();
            // Move to beginning
            resultSet.beforeFirst();
        }

        //if (rows == null)
            assertThat(rows).as("No rows returned. Check query and db.").isNotNull();
            //Assert.assertTrue(false,"No rows returned. Check query and db.");
        if (rows <= expectedNumber)
            this.softly.assertThat(rows).as("Incorrect number of rows returned.").isGreaterThan(expectedNumber);

    }

    @Then("number of lines is more than {int}")
    public void getLinesNumber (int expectedNumber) throws SQLException {
        Integer rows = null;

        if (resultSet.last()) {
            rows = resultSet.getRow();
            // Move to beginning
            resultSet.beforeFirst();
        }

        //if (rows == null)
        assertThat(rows).as("No rows returned. Check query and db.").isNotNull();
        //Assert.assertTrue(false,"No rows returned. Check query and db.");
        assertThat(rows).as("Incorrect number of rows returned.").isGreaterThan(expectedNumber);

    }


    @Then("count is more than {int}")
    public void getCount (int expectedValue) throws SQLException {
        Integer count=null;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        if (count == null)
            assertThat(count).as("No rows returned. Check query and db.").isNotNull();
            //throw new RuntimeException("No rows returned. Check query and db.");
        assertThat(count).isGreaterThan(expectedValue);
        //if (count <= expectedValue)
          //  throw new RuntimeException("Count is " + count + ", but should be more than " + expectedValue);
    }

    @Then("check soft validation")
    public void checkSoft (){
        this.softly.assertAll();
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
