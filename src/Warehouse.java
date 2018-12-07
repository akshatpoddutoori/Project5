import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 */

public class Warehouse {

	//final static String folderPath = "files/";        //Vocareum's filepath
    //final static String folderPath = "files/";        //Linnea's filepath
    final static String folderPath = "/Users/Akshat/Desktop/JAVA/Project5/src/files/";  //Akshat's filepath

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
    	//1) load data (vehicle, packages, profits, packages shipped and primeday) from files using DatabaseManager
        ArrayList<Vehicle> vehicles = DatabaseManager.loadVehicles(VEHICLE_FILE);
        ArrayList<Package> packages = DatabaseManager.loadPackages(PACKAGE_FILE);
        double profit = DatabaseManager.loadProfit(PROFIT_FILE);
        int numPackages = DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE);
        boolean primeDay = DatabaseManager.loadPrimeDay(PRIME_DAY_FILE);

    	//2) Show menu and handle user inputs
        Scanner scan = new Scanner(System.in);
        int menu;

        while (true) {
            while (true) {

                String active;
                if (primeDay) {
                    active = "Deactivate ";
                } else {
                    active = "Activate ";
                }

                System.out.println("==========Options==========\n" +
                        "1) Add Package\n" +
                        "2) Add Vehicle\n" +
                        "3) " + active + "Prime Day\n" +
                        "4) Send Vehicle\n" +
                        "5) Print Statistics\n" +
                        "6) Exit\n" +
                        "===========================");
                try {
                    String num = scan.next();
                    menu = Integer.parseInt(num);

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
                numPackages++;

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
                if (!primeDay) {
                    for (Package currentPackage : packages) {
                        currentPackage.setPrice((currentPackage.getPrice() * 0.85));
                    }
                } else if (primeDay) {
                    for (Package currentPackage : packages) {
                        currentPackage.setPrice((currentPackage.getPrice() / 0.85));
                    }
                }
                if (primeDay) {
                    primeDay = false;
                } else {
                    primeDay = true;
                }
                DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeDay);
                DatabaseManager.savePackages(PACKAGE_FILE, packages);

            } else if (menu == 4) {
                if (vehicles.size() == 0)
                    System.out.println("Error: No vehicles available.");
                else if (packages.size() == 0)
                    System.out.println("Error: No packages available.");
                else {
                    int vehicleOption;
                    boolean sendPackages = false;
                    while (!sendPackages) {
                        try {
                            System.out.println("Options:\n" +
                                    "1) Send Truck\n" +
                                    "2) Send Drone\n" +
                                    "3) Send Cargo Plane\n" +
                                    "4) Send First Available");
                            String num = scan.nextLine();
                            vehicleOption = Integer.parseInt(num);
                            if (vehicleOption < 1 || vehicleOption > 4) {
                                continue;
                            }
                            boolean isTruck = false;
                            boolean isDrone = false;
                            boolean isPlane = false;
                            for (Vehicle currentVehicle : vehicles) {
                                //checks if there is a truck, drone, or cargo plane in the entire array
                                if (currentVehicle.getType() == "Truck") {
                                    isTruck = true;
                                } else if (currentVehicle.getType() == "Drone") {
                                    isDrone = true;
                                } else if (currentVehicle.getType() == "Cargo Plane") {
                                    isTruck = true;
                                }
                            }
                            if (vehicleOption == 1) {
                                if (!isTruck) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                }
                            } else if (vehicleOption == 2) {
                                if (!isDrone) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                }
                            } else if (vehicleOption == 3) {
                                if (!isPlane) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                }
                            } else if (vehicleOption == 4) {
                                if (!isTruck & !isDrone && !isPlane) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("That is not a valid option.");
                        }
                    }
                    
                    int zipOption;
                    while (true) {
                        System.out.println("ZIP Code Options:\n" +
                                "1) Send to first ZIP Code\n" +
                                "2) Send to mode of ZIP Codes");
                        try {
                            String number = scan.nextLine();
                            zipOption = Integer.parseInt(number);
                            ArrayList<Integer> indexes = new ArrayList<Integer>();
                            indexes.add(new Integer(((packages.get(0)).getDestination()).getZipCode()));
                            if (zipOption == 1) {
                                int destination = ((packages.get(0)).getDestination()).getZipCode();
                                for (int x = 0; x < packages.size(); x++) {
                                    int currentZip = ((packages.get(0)).getDestination()).getZipCode();
                                    if (currentZip == destination) {
                                        indexes.add(new Integer(currentZip));
                                    }
                                }


                                break;
                            } else if (zipOption == 2) {



                                break;
                            }
                            System.out.println("That is not a valid option.");
                        } catch (Exception e) {
                            System.out.println("That is not a valid option.");
                        }
                    }
                    break;


                }

            } else if (menu == 5) { //TODO won't always lineup properly
                System.out.println("==========Statistics==========\n" +
                        "Profits:                 $" + DatabaseManager.loadProfit(PROFIT_FILE   ) + "\n" +
                        "Packages Shipped:           " + DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE)
                        + "\n" + "Packages in Warehouse:      "
                        + (DatabaseManager.loadPackages(PACKAGE_FILE)).size() + "\n" +
                        "==============================");
            } else if (menu == 6) {
                for (Package currentPackage : packages) {
                    System.out.println(currentPackage.getPrice());
                }
                break;
            }
        }
    	
    	
    	//3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
    	
    
    }


}