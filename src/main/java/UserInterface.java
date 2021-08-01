import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInterface {

    static Scanner scanner = new Scanner(System.in);
    static Map<Integer,String> companyMap = new LinkedHashMap<>();
    static Map<Integer,String> customerMap = new LinkedHashMap<>();
    static Map<Integer,String> carMap = new LinkedHashMap<>();
    static dataAccessManagement dataAccessManagement;

    void initialMENU(String[] args) throws SQLException, ClassNotFoundException {

        dataAccessManagement = new dataAccessManagement(args);

        boolean exit= false;
        int choice;
        String customerName;
        while(!exit){

            System.out.println("1. Log in as a manager\n" +
                    "2. Log in as a customer\n" +
                    "3. Create a customer\n" +
                    "0. Exit");
            choice = scanner.nextInt();

            switch (choice){
                case 0:
                    exit = true;
                    break;
                case 1:
                    companyManagementMENU();
                    break;
                case 2:
                    customerMENU();
                    break;
                case 3:
                    System.out.println("Enter the customer name:");
                    scanner.nextLine();
                    customerName = scanner.nextLine();
                    dataAccessManagement.addCustomer(new Customer(customerName));
                    System.out.println("The customer was added!");
            }
        }
    }
    static void customerMENU() throws SQLException {

        String choice;
        String customerName;
        String companyName;
        String carName;
        boolean exit = false;

        if(showCustomerList(dataAccessManagement.getCustomerList())) {
            choice = scanner.next();
            customerName = customerMap.get(Integer.parseInt(choice));

            while (!exit) {
                System.out.println("1. Rent a car\n" +
                        "2. Return a rented car\n" +
                        "3. My rented car\n" +
                        "0. Back");

                choice = scanner.next();

                switch (choice) {
                    case "0":
                        exit = true;
                        break;
                    case "1":

                        if (!dataAccessManagement.checkCustomerRental(new Customer(customerName))) {
                            showCompanyList(dataAccessManagement.getCompanyList());
                            choice = scanner.next();

                            companyName = companyMap.get(Integer.parseInt(choice));

                            showCarList(dataAccessManagement.getCompanyNotRentedCars(new Company(companyName)));
                            choice = scanner.next();

                            carName = carMap.get(Integer.parseInt(choice));

                            dataAccessManagement.updateCustomerCarRental(new Customer(customerName), new Car(carName));
                            System.out.println("You rented " + "'" + carName + "'");
                        } else System.out.println("You've already rented a car!");

                        break;
                    case "2":

                        if(dataAccessManagement.checkCustomerRental(new Customer(customerName))) {
                            dataAccessManagement.cancelCustomerRental(new Customer(customerName));
                            System.out.println("You've returned a rented car!");
                        }
                        else System.out.println("You didn't rent a car!");


                        break;
                    case "3":
                        showRentedCarsByCustomer(dataAccessManagement.getCarsRentedByUserAndItsCompany(new Customer(customerName)));
                }
            }
        }
    }

    static void companyMENU(String companyName) throws SQLException {

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
                    System.out.println("Enter the car name:");
                    scanner.nextLine();
                    carName = scanner.nextLine();
                    dataAccessManagement.addCar(new Company(companyName), new Car(carName));
                    System.out.println("The car was added!");
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

                    if (choice2 != 0) companyMENU(companyMap.get(choice2));
                }
            }
            else if(choice1 == 2){
                System.out.println("Enter the company name: ");
                scanner.nextLine();
                companyName = scanner.nextLine();
                dataAccessManagement.addCompany(new Company(companyName));
                System.out.println("The company was created!");
            }
        }
    }

    static boolean showCompanyList(ResultSet resultSet) throws SQLException {

        String companyName;
        int counter = 1;
        if(!resultSet.isBeforeFirst()){
            System.out.println("\nThe company list is empty!\n");
            return false;
        }
        else {
            System.out.println();
            System.out.println("Choose the company:");
            while (resultSet.next()) {

                companyName = resultSet.getString("NAME");

                companyMap.put(counter,companyName);

                System.out.println(counter + ". " + companyName);
                counter++;
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

                carMap.put(counter,carName);

                System.out.println(counter + ". " + carName);
                counter++;
            }
            System.out.println();
        }
    }

    static boolean showCustomerList(ResultSet resultSet) throws SQLException{

        String customerName;
        int counter = 1;
        if(!resultSet.isBeforeFirst()){
            System.out.println("\nThe customer list is empty!\n");
            return false;
        }
        else {
            System.out.println();
            System.out.println("Customer list: ");
            while (resultSet.next()) {

                customerName = resultSet.getString("NAME");

                customerMap.put(counter,customerName);

                System.out.println(counter + ". " + customerName);
                counter++;
            }
            System.out.println("0. Back");
            System.out.println();

            return true;
        }
    }

    static void showRentedCarsByCustomer(ResultSet resultSet) throws SQLException{

        String carName;
        String companyName;

        if(!resultSet.isBeforeFirst()) System.out.println("\nYou didn't rent a car!\n");
        else {
            System.out.println();
            System.out.println("Your rented car: ");

            resultSet.next();

            carName = resultSet.getString("CAR_NAME");
            companyName = resultSet.getString("COMPANY_NAME");

            System.out.println(carName);
            System.out.println("Company:");
            System.out.println(companyName);
            System.out.println();
        }
    }
}
