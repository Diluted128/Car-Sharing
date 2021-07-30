import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    Connection connect = null;
    static final String driver = "org.h2.Driver";
    static String url = "jdbc:h2:file:./src/main/db/";

    DatabaseConnection(String[] args) throws ClassNotFoundException, SQLException {

        registerJDBCDriver();
        createURL(args);
        createConnection();
    }

    void registerJDBCDriver() throws ClassNotFoundException {
        Class.forName(driver);
    }

    void createURL(String[] args){
        url = args.length > 0 ? url + args[1] : url + "default";
    }

    void createConnection() throws SQLException {
        connect = DriverManager.getConnection(url);
    }

    void closeConnection() throws SQLException {
        connect.close();
        connect.setAutoCommit(true);
    }
}
