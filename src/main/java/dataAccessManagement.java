import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataAccessManagement implements databaseOperations{
    DatabaseConnection databaseConnection;
    Statement statement;

    dataAccessManagement(String[] args) throws SQLException, ClassNotFoundException {

        databaseConnection = new DatabaseConnection(args);
        statement = databaseConnection.connect.createStatement();
        createCompanyTable();
        createCarTable();
    }

    @Override
    public ResultSet getCompanyList() throws SQLException {
        return statement.executeQuery("SELECT * FROM COMPANY");
    }

    @Override
    public ResultSet getCompanyCarList(Company company) throws SQLException {
        return statement.executeQuery("SELECT * FROM CAR " +
                "WHERE COMPANY_ID = (SELECT ID FROM COMPANY WHERE NAME = " + "'" + company.getName() + "')");
    }

    @Override
    public void addCompany(Company company) throws SQLException {
        String addCompanyQuery = "INSERT INTO COMPANY (NAME) VALUES(" + "'" + company.getName() + "')";
        statement.executeUpdate(addCompanyQuery);
    }

    @Override
    public void addCar(Company company, Car car) throws SQLException {
        String addCarQuery = "INSERT INTO CAR (NAME,COMPANY_ID) VALUES(" + "'" + car.getName() + "',"
                + "(SELECT ID FROM COMPANY WHERE NAME = "+ "'" + company.getName() + "'))";
        statement.executeUpdate(addCarQuery);
    }

    @Override
    public void createCompanyTable() throws SQLException {

         statement.executeUpdate("DROP TABLE CAR");
         statement.executeUpdate("DROP TABLE COMPANY");

        String createCompanyQuery = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                "ID INTEGER AUTO_INCREMENT NOT NULL, " +
                "NAME VARCHAR(100) NOT NULL UNIQUE," +
                "PRIMARY KEY(ID))";
        statement.executeUpdate(createCompanyQuery);

        statement.executeUpdate("ALTER TABLE company ALTER COLUMN id RESTART WITH 1");
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
    }
}
