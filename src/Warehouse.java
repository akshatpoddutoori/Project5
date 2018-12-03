import javax.xml.crypto.Data;
import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 */

public class Warehouse {
	final static String folderPath = "Desktop/JAVA/Project5/src/files/";
    final static File VEHICLE_FILE = new File(folderPath + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(folderPath + "PackageList.csv");
    final static File PROFIT_FILE = new File(folderPath + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(folderPath + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(folderPath + "PrimeDay.txt");
    final static double PRIME_DAY_DISCOUNT = .15;

    /**
     * Main Method
     * 
     * @param args list of command line arguements
     */
    public static void main(String[] args) {
    	//TODO
    	
    	//1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager
        ArrayList<Vehicle> vehicles = DatabaseManager.loadVehicles(VEHICLE_FILE);
        ArrayList<Package> packages = DatabaseManager.loadPackages(PACKAGE_FILE);
        double profit = DatabaseManager.loadProfit(PROFIT_FILE);
        int numPackages = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        boolean primeDay = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);


        //2) Show menu and handle user inputs
        Scanner scan = new Scanner(System.in);
        int menu = 0;

        while (true) {
            while (true) {
                System.out.println("==========Options==========\n" +
                        "1) Add Package\n" +
                        "2) Add Vehicle\n" +
                        "3) Activate Prime Day\n" +
                        "4) Send Vehicle\n" +
                        "5) Print Statistics\n" +
                        "6) Exit\n" +
                        "===========================");
                try {
                    menu = scan.nextInt();
                    if (menu > 0 && menu < 7) {
                        break;
                    } else {
                        System.out.println("Error: Option not available.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Error: Option not available.");
                    continue;
                }
            }
            if (menu == 1) {
                System.out.println("Enter Package ID:");
                String id = scan.next();

                System.out.println("Enter Product Name:");
                String product = scan.next();

                System.out.println("Enter Weight:");
                double weight = scan.nextDouble();
                scan.nextLine();

                System.out.println("Enter Price:");
                double price = scan.nextDouble();
                scan.nextLine();

                System.out.println("Enter Buyer Name:");
                String name = scan.next();

                System.out.println("Enter Address:");
                String address = scan.next();

                System.out.println("Enter City:");
                String city = scan.next();

                System.out.println("Enter State:");
                String state = scan.next();

                System.out.println("Enter ZIP Code:");
                int zipCode = scan.nextInt();

                ShippingAddress shippingAddress = new ShippingAddress(name, address, city, state, zipCode);
                Package packageToBeAdded = new Package(id, product, weight, price, shippingAddress);

                System.out.println(packageToBeAdded.shippingLabel());
                packages.add(packageToBeAdded);

                DatabaseManager.savePackages(PACKAGE_FILE, packages);

            } else if (menu == 2) {
                System.out.println("Vehicle Options:\n" +
                        "1) Truck\n" +
                        "2) Drone\n" +
                        "3) Cargo Plane");
                while(true) {
                    int vehicleOption = scan.nextInt();
                    if (vehicleOption == 1) {
                        System.out.println("Enter License Plate No.:");
                        String licensePlate = scan.next();
                        System.out.println("Enter Maximum Carry Weight:");
                        double maxWeight = scan.nextDouble();

                        vehicles.add(new Truck(licensePlate, maxWeight));
                        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);

                        break;
                    } else if (vehicleOption == 2) {
                        System.out.println("Enter License Plate No.:");
                        String licensePlate = scan.next();
                        System.out.println("Enter Maximum Carry Weight:");
                        double maxWeight = scan.nextDouble();

                        vehicles.add(new Drone(licensePlate, maxWeight));
                        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);

                        break;
                    } else if (vehicleOption == 3) {
                        System.out.println("Enter License Plate No.:");
                        String licensePlate = scan.next();
                        System.out.println("Enter Maximum Carry Weight:");
                        double maxWeight = scan.nextDouble();

                        vehicles.add(new CargoPlane(licensePlate, maxWeight));
                        DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);

                        break;
                    } else {
                        System.out.println("Error: Option not available.");
                        continue;
                    }
                }
            } else if (menu == 3) {
                for (Package currentPackage: packages) {
                    currentPackage.setPrice((currentPackage.getPrice() * 0.85));
                }
                if (primeDay == true) {
                    primeDay = false;
                } else {
                    primeDay = true;
                }

            } else if (menu == 4) {

            } else if (menu == 5) {
                System.out.println("==========Statistics==========\n" +
                        "Profits:                 $" + DatabaseManager.loadProfit(PROFIT_FILE   ) + "\n" +
                        "Packages Shipped:                " + DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE)
                        + "\n" + "Packages in Warehouse:           "
                        + (DatabaseManager.loadPackages(PACKAGE_FILE)).size() + "\n" +
                        "==============================");
            } else if (menu == 6) {
                break;
            }
        }

    	
    	
    	//3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
    	
    
    }


}