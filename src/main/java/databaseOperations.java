import java.sql.ResultSet;
import java.sql.SQLException;

interface databaseOperations {
    ResultSet getCompanyList() throws SQLException;
    ResultSet getCompanyCarList(Company company) throws SQLException;
    void addCompany(Company company) throws SQLException;
    void addCar(Company company, Car car) throws SQLException;
    void createCompanyTable() throws SQLException;
    void createCarTable() throws SQLException;
}
