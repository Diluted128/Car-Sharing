import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class dataAccessManagement implements databaseOperations{
    DatabaseConnection databaseConnection;
    Statement statement;

    dataAccessManagement(String[] args) throws SQLException, ClassNotFoundException {

        databaseConnection = new DatabaseConnection(args);
        statement = databaseConnection.connect.createStatement();
    }

    @Override
    public ResultSet getCompanyList() throws SQLException {
        return statement.executeQuery("SELECT * FROM COMPANY");
    }

    @Override
    public void addCompany(Company company) throws SQLException {
        String addCompanyQuery = "INSERT INTO COMPANY (NAME) VALUES(" + "'" + company.getName() + "')";
        statement.executeUpdate(addCompanyQuery);

    }

    @Override
    public void createTable() throws SQLException {

        String createCompanyQuery = "CREATE TABLE IF NOT EXISTS COMPANY (" +
                "ID INTEGER AUTO_INCREMENT NOT NULL, " +
                "NAME VARCHAR(100) NOT NULL UNIQUE," +
                "PRIMARY KEY(ID))";
        statement.executeUpdate(createCompanyQuery);

    }
}
