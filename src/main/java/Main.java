import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);


    static void showCompanyList(ResultSet resultSet) throws SQLException {

        int id;
        String companyName;

        if(!resultSet.isBeforeFirst()) System.out.println("\nThe company list is empty!\n");
        else {
            System.out.println();
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                companyName = resultSet.getString("NAME");

                System.out.println(id + ". " + companyName);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        dataAccessManagement dataAccessManagement = new dataAccessManagement(args);

        dataAccessManagement.createTable();

        boolean exit1= false;
        boolean exit2 = false;
        String companyName;
        String choice1;
        String choice2;
        while(!exit1){

            System.out.println("1. Log in as a manager\n" +
                    "0. Exit");
            choice1 = scanner.next();

            switch (choice1){
                case "0":
                    exit1 = true;
                    dataAccessManagement.databaseConnection.closeConnection();
                    break;
                case "1":
                    while(!exit2) {
                        System.out.println("1. Company list\n" +
                                "2. Create a company\n" +
                                "0. Back");
                        choice2 = scanner.next();

                        switch (choice2) {
                            case "0":
                                exit2 = true;
                                break;
                            case "1":
                                showCompanyList(dataAccessManagement.getCompanyList());
                                break;
                            case "2":
                                System.out.println();
                                System.out.println("Enter the company name: ");
                                scanner.nextLine();
                                companyName = scanner.nextLine();
                                dataAccessManagement.addCompany(new Company(companyName));
                                System.out.println("The company was created!");
                                System.out.println();
                                break;

                        }
                    }
            }
        }
    }
}
