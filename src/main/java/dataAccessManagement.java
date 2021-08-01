import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataAccessManagement implements databaseOperations{
    DatabaseConnection databaseConnection;
    Statement statement;

    dataAccessManagement(String[] args) throws SQLException, ClassNotFoundException {

        databaseConnection = new DatabaseConnection(args);
        statement = databaseConnection.connect.createStatement();
        clearTables();
        createCompanyTable();
        createCarTable();
        createCustomerTable();
    }

    @Override
    public ResultSet getCompanyList() throws SQLException {
        return statement.executeQuery("SELECT NAME FROM COMPANY ORDER BY ID");
    }

    @Override
    public ResultSet getCompanyCarList(Company company) throws SQLException {
        return statement.executeQuery("SELECT NAME FROM CAR " +
                "WHERE COMPANY_ID = (SELECT ID FROM COMPANY WHERE NAME = " + "'" + company.getName() + "')");
    }

    public ResultSet getCompanyNotRentedCars(Company company) throws SQLException {
        return statement.executeQuery("SELECT CAR.NAME FROM CAR LEFT JOIN CUSTOMER ON CAR.id = CUSTOMER.rented_car_id" +
                " WHERE company_id = (SELECT ID FROM COMPANY WHERE NAME = " + "'" + company.getName() + "')" + "AND CUSTOMER.rented_car_id is null");
    }

    @Override
    public ResultSet getCarsRentedByUserAndItsCompany(Customer customer) throws SQLException {
        return statement.executeQuery(
                "SELECT CAR.NAME as CAR_NAME, COMPANY.NAME as COMPANY_NAME FROM CAR JOIN COMPANY ON CAR.COMPANY_ID = COMPANY.ID " +
                        "WHERE CAR.ID = (SELECT RENTED_CAR_ID FROM CUSTOMER" +
                        " WHERE CUSTOMER.NAME = " + "'" + customer.getName() + "')");
    }

    @Override
    public ResultSet getCustomerList() throws SQLException {
        return statement.executeQuery("SELECT NAME FROM CUSTOMER");
    }

    @Override
    public void updateCustomerCarRental(Customer customer, Car car) throws SQLException {
        statement.execute(
                "UPDATE CUSTOMER SET RENTED_CAR_ID =" +
                        " (SELECT ID FROM CAR WHERE NAME =" + "'" + car.getName() + "')" +
                        "WHERE NAME = " + "'" + customer.getName() + "'");
    }

    @Override
    public void addCompany(Company company) throws SQLException {
        statement.executeUpdate("INSERT INTO COMPANY (NAME) VALUES(" + "'" + company.getName() + "')");
    }

    @Override
    public void addCar(Company company, Car car) throws SQLException {
        String addCarQuery = "INSERT INTO CAR (NAME,COMPANY_ID) VALUES(" + "'" + car.getName() + "',"
                + "(SELECT ID FROM COMPANY WHERE NAME = "+ "'" + company.getName() + "'))";
        statement.executeUpdate(addCarQuery);
    }

    @Override
    public void addCustomer(Customer customer) throws SQLException {
        statement.executeUpdate("INSERT INTO CUSTOMER (NAME) VALUES(" + "'" + customer.getName() + "')");
    }

    @Override
    public boolean checkCustomerRental(Customer customer) throws SQLException {
        ResultSet result = statement.executeQuery("SELECT RENTED_CAR_ID FROM CUSTOMER WHERE NAME = " + "'" + customer.getName() + "'");
        result.next();
        return result.getInt("RENTED_CAR_ID") != 0;
    }

    @Override
    public void cancelCustomerRental(Customer customer) throws SQLException {
        statement.executeUpdate("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE NAME = " + "'" + customer.getName() + "'");
    }

    @Override
    public void createCompanyTable() throws SQLException {

        String createCompanyQuery = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                "ID INTEGER AUTO_INCREMENT NOT NULL, " +
                "NAME VARCHAR(100) NOT NULL UNIQUE," +
                "PRIMARY KEY(ID))";
        statement.executeUpdate(createCompanyQuery);

        statement.executeUpdate("ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1");
    }

    @Override
    public void createCarTable() throws SQLException {

        String createCarQuery = "CREATE TABLE IF NOT EXISTS CAR (" +
                "ID INT AUTO_INCREMENT," +
                "NAME VARCHAR(20) NOT NULL UNIQUE," +
                "COMPANY_ID INT NOT NULL, " +
                "PRIMARY KEY (ID)," +
                "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID))";
        statement.executeUpdate(createCarQuery);

        statement.executeUpdate("ALTER TABLE CAR ALTER COLUMN ID RESTART WITH 1");
    }

    @Override
    public void createCustomerTable() throws SQLException {
        String createCustomerQuery = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
                "ID INT AUTO_INCREMENT," +
                "NAME VARCHAR(20) NOT NULL UNIQUE," +
                "RENTED_CAR_ID INT," +
                "PRIMARY KEY (ID)," +
                "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID))";
        statement.executeUpdate(createCustomerQuery);

        statement.executeUpdate("ALTER TABLE CUSTOMER ALTER COLUMN ID RESTART WITH 1");
    }

    @Override
    public void clearTables() throws SQLException {
        statement.executeUpdate("DROP TABLE CUSTOMER");
        statement.executeUpdate("DROP TABLE CAR");
        statement.executeUpdate("DROP TABLE COMPANY");
    }
}
