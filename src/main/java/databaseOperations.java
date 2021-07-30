import java.sql.ResultSet;
import java.sql.SQLException;

interface databaseOperations {
    ResultSet getCompanyList() throws SQLException;
    void addCompany(Company company) throws SQLException;
    void createTable() throws SQLException;

}
