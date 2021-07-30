import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Map<Integer,String> companyMap = new LinkedHashMap<>();
    static dataAccessManagement dataAccessManagement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        dataAccessManagement = new dataAccessManagement(args);

        boolean exit= false;
        String choice;
        while(!exit){

            System.out.println("1. Log in as a manager\n" + "0. Exit");
            choice = scanner.next();

            switch (choice){
                case "0":
                    exit = true;
                    break;
                case "1":
                    System.out.println();
                    companyManagementMENU();
            }
        }
    }
    static void concreteCompanyMENU(String companyName) throws SQLException {

        boolean exit = false;
        String carName;
        String choice;

        while(!exit) {

            System.out.println(companyName + " company");
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");

            choice = scanner.next();

            switch (choice) {
                case "0":
                    exit = true;
                    break;
                case "1":
                    showCarList(dataAccessManagement.getCompanyCarList(new Company(companyName)));
                    break;
                case "2":
                    System.out.println();
                    System.out.println("Enter the car name:");
                    scanner.nextLine();
                    carName = scanner.nextLine();
                    dataAccessManagement.addCar(new Company(companyName), new Car(carName));
                    System.out.println("The car was added!");
                    System.out.println();
            }
        }
    }
    static void companyManagementMENU() throws SQLException {

        String companyName;
        int choice1;
        int choice2;

        boolean exit = false;

        while(!exit) {
            System.out.println("1. Company list\n" +
                    "2. Create a company\n" +
                    "0. Back");
            choice1 = scanner.nextInt();

            if(choice1 == 0) exit = true;
            else if(choice1 == 1){
                if(showCompanyList(dataAccessManagement.getCompanyList())) {

                    scanner.nextLine();
                    choice2 = scanner.nextInt();

                    if (choice2 != 0) {
                        System.out.println();
                        concreteCompanyMENU(companyMap.get(choice2));
                    }
                }
            }
            else if(choice1 == 2){
                System.out.println();
                System.out.println("Enter the company name: ");
                scanner.nextLine();
                companyName = scanner.nextLine();
                dataAccessManagement.addCompany(new Company(companyName));
                System.out.println("The company was created!");
                System.out.println();
            }
        }
    }
    static boolean showCompanyList(ResultSet resultSet) throws SQLException {

        int id;
        String companyName;

        if(!resultSet.isBeforeFirst()){
            System.out.println("\nThe company list is empty!\n");
            return false;
        }
        else {
            System.out.println();
            System.out.println("Choose the company:");
            while (resultSet.next()) {
                id = resultSet.getInt("ID");
                companyName = resultSet.getString("NAME");

                companyMap.put(id,companyName);

                System.out.println(id + ". " + companyName);
            }
            System.out.println("0. Back");
            return true;
        }
    }

    static void showCarList(ResultSet resultSet) throws SQLException {

        String carName;
        int counter = 1;
        if(!resultSet.isBeforeFirst()) System.out.println("\nThe car list is empty!\n");
        else {
            System.out.println();
            System.out.println("Car list: ");
            while (resultSet.next()) {

                carName = resultSet.getString("NAME");

                System.out.println(counter + ". " + carName);
                counter++;
            }
            System.out.println();
        }
    }
}
