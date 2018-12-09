import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Project 5 - Warehouse
 *
 * Is a class runs the Amazon warehouse
 *
 * @author Akshat Poddutoori, CS180 Black
 *
 * @version December 9, 2018
 *
 */

public class Warehouse {

	//final static String FOLDER_PATH = "files/";        //Vocareum's filepath
    //final static String FOLDER_PATH = "files/";        //Linnea's filepath
    final static String FOLDER_PATH = "/Users/Akshat/Desktop/JAVA/Project5/src/files/";  //Akshat's filepath

    final static File VEHICLE_FILE = new File(FOLDER_PATH + "VehicleList.csv");
    final static File PACKAGE_FILE = new File(FOLDER_PATH + "PackageList.csv");
    final static File PROFIT_FILE = new File(FOLDER_PATH + "Profit.txt");
    final static File N_PACKAGES_FILE = new File(FOLDER_PATH + "NumberOfPackages.txt");
    final static File PRIME_DAY_FILE = new File(FOLDER_PATH + "PrimeDay.txt");

    final static double PRIME_DAY_DISCOUNT = .15;


    public static void printStatisticsReport(double profits, int packagesShipped, int numberOfPackages) {
        System.out.println("==========Statistics==========\n" +
                "Profits:                 $" + profits + "\n" +
                "Packages Shipped:           " + packagesShipped
                + "\n" + "Packages in Warehouse:      "
                + numberOfPackages + "\n" +
                "==============================");
    }

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

        DecimalFormat df = new DecimalFormat("0.00");

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
                while (true) {
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
                    String type = "";
                    int vehicleOption;
                    boolean sendPackages = false;
                    Vehicle vehicle = null;

                    while (!sendPackages) {
                        try {
                            System.out.println("Options:\n" +
                                    "1) Send Truck\n" +
                                    "2) Send Drone\n" +
                                    "3) Send Cargo Plane\n" +
                                    "4) Send First Available");
                            vehicleOption = Integer.parseInt(scan.next());
                            if (vehicleOption < 1 || vehicleOption > 4) {
                                continue;
                            }
                            boolean isTruck = false;
                            boolean isDrone = false;
                            boolean isPlane = false;
                            for (Vehicle currentVehicle : vehicles) {
                                //checks if there is a truck, drone, or cargo plane in the entire array
                                if (currentVehicle.getType().equals("Truck")) {
                                    isTruck = true;
                                } else if (currentVehicle.getType().equals("Drone")) {
                                    isDrone = true;
                                } else if (currentVehicle.getType().equals("Cargo Plane")) {
                                    isPlane = true;
                                }
                            }
                            if (vehicleOption == 1) {
                                if (!isTruck) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                    type = "Truck";
                                }
                            } else if (vehicleOption == 2) {
                                if (!isDrone) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                    type = "Drone";
                                }
                            } else if (vehicleOption == 3) {
                                if (!isPlane) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                    type = "Cargo Plane";
                                }
                            } else if (vehicleOption == 4) {
                                if (!isTruck & !isDrone && !isPlane) {
                                    System.out.println("Error: No vehicles of selected type are available.");
                                } else {
                                    sendPackages = true;
                                    type = vehicles.get(0).getType();
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("That is not a valid option.");
                        }
                    }

                    for (Vehicle currentVehicle: vehicles) {        //assigns the vehicle we are going to use
                        if (currentVehicle.getType().equals(type)) {
                            vehicle = currentVehicle; //now we know which vehicle object has been chosen
                            break;
                        }
                    }

                    
                    int zipOption = 0;
                    while (sendPackages) {
                        System.out.println("ZIP Code Options:\n" +
                                "1) Send to first ZIP Code\n" +
                                "2) Send to mode of ZIP Codes");
                        try {
                            int number = scan.nextInt();
                            scan.nextLine();
                            if (number != 1 && number != 2) {
                                System.out.println("Tengo una problema");
                                continue;
                            } else if (number == 1) {

                                int zipDestination = ((packages.get(0)).getDestination()).getZipCode();    //first ZIP
                                vehicle.setZipDest(zipDestination);

                                vehicle.fill(packages);


                                sendPackages = false;
                            }  else if (number == 2) {

                                int mode = packages.get(0).getDestination().getZipCode();
                                int maxCount = 0;
                                for (int x = 0; x < packages.size(); x++) {
                                    int startZip = packages.get(x).getDestination().getZipCode();
                                    int count = 0;
                                    for (int y = 0; y < packages.size(); y++) {
                                        if (packages.get(y).getDestination().getZipCode() == startZip)
                                            count++;
                                        if (count > maxCount) {
                                            mode = startZip;
                                            maxCount = count;
                                        }
                                    }
                                }

                                vehicle.setZipDest(mode);

                                vehicle.fill(packages);


                                sendPackages = false;
                            }

                            if (number == 1 || number == 2) {

                                profit += vehicle.getProfit();
                                numPackages += vehicle.getPackages().size();


                                for (Package currentPackage : vehicle.getPackages()) {
                                    packages.remove(currentPackage);
                                }

                                profit = (Math.round(profit * 100)) / 100.0;

                                DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, numPackages);
                                DatabaseManager.saveProfit(PROFIT_FILE, profit);
                                DatabaseManager.savePackages(PACKAGE_FILE, packages);

                                System.out.println(vehicle.report());
                                vehicles.remove(vehicle);
                                DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

            } else if (menu == 5) {

                Warehouse.printStatisticsReport(DatabaseManager.loadProfit(PROFIT_FILE),
                        DatabaseManager.loadPackagesShipped(N_PACKAGES_FILE),
                        (DatabaseManager.loadPackages(PACKAGE_FILE)).size());

            } else if (menu == 6) {

                DatabaseManager.savePackagesShipped(N_PACKAGES_FILE, numPackages);
                DatabaseManager.saveProfit(PROFIT_FILE, profit);
                DatabaseManager.savePackages(PACKAGE_FILE, packages);
                DatabaseManager.saveVehicles(VEHICLE_FILE, vehicles);
                DatabaseManager.savePrimeDay(PRIME_DAY_FILE, primeDay);

                break;
            }
        }
    	
    	

    
    }


}