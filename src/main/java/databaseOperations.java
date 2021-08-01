import java.sql.ResultSet;
import java.sql.SQLException;

interface databaseOperations {
    ResultSet getCompanyList() throws SQLException;
    ResultSet getCompanyCarList(Company company) throws SQLException;
    ResultSet getCustomerList() throws SQLException;
    ResultSet getCarsRentedByUserAndItsCompany(Customer customer) throws SQLException;
    ResultSet getCompanyNotRentedCars(Company company) throws SQLException;
    boolean checkCustomerRental(Customer customer) throws SQLException;
    void cancelCustomerRental(Customer customer) throws SQLException;
    void updateCustomerCarRental(Customer customer, Car car) throws SQLException;
    void addCompany(Company company) throws SQLException;
    void addCar(Company company, Car car) throws SQLException;
    void addCustomer(Customer customer) throws SQLException;
    void createCompanyTable() throws SQLException;
    void createCarTable() throws SQLException;
    void createCustomerTable() throws SQLException;
    void clearTables() throws SQLException;
}
