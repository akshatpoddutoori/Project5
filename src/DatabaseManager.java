import java.io.*;
import java.util.ArrayList;

/**
 * <h1>Database Manager</h1>
 * 
 * Used to locally save and retrieve data.
 */
public class DatabaseManager {

    /**
     * Creates an ArrayList of Vehicles from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * If filePath does not exist, a blank ArrayList will be returned.
     * 
     * @param file CSV File
     * @return ArrayList of vehicles
     */
    public static ArrayList<Vehicle> loadVehicles(File file) {
        ArrayList<Vehicle> output = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            for (String line; (line = br.readLine()) != null; ) {

                String vehicleType = line.substring(0, line.indexOf(","));
                String licensePlate = line.substring(line.indexOf(",") + 1, line.lastIndexOf(","));
                double maxWeight = Double.parseDouble(line.substring(line.lastIndexOf(",") + 1));

                if (vehicleType.equals("Truck")) {
                    output.add(new Truck(licensePlate, maxWeight));
                } else if (vehicleType.equals("Drone")) {
                    output.add(new Drone(licensePlate, maxWeight));
                } else if (vehicleType.equals("Cargo Plane")) {
                    output.add(new CargoPlane(licensePlate, maxWeight));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("KOWALSKI, ANALYSIS");
            e.printStackTrace();
        }
        return output;
    }

    
    
    
    
    /**
     * Creates an ArrayList of Packages from the passed CSV file. The values are in
     * the CSV file as followed:
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * 
     * If filePath does not exist, a blank ArrayList will be returned.
     * 
     * @param file CSV File
     * @return ArrayList of packages
     */
//    123ABC,Echo dot,30.0,49.99,Nathan Cohen,3203 Thousand Oaks Drive,Louisville,KY,40205
//    213,OP,10.0,230.0,John,3203,LV,KY,40205
//    QWE,IL,5.0,20.03,Harry,3201,UP,ER,40208
//    123ABC,Echo Dot,5.7,49.99,Lawson Computer Science Building,305 N University St,West Lafayette,IN,47907
    public static ArrayList<Package> loadPackages(File file) {

        ArrayList<Package> output = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String line; (line = br.readLine()) != null; ) {

                int c1 = line.indexOf(',');
                int c2 = line.indexOf(',', c1 + 1);
                int c3 = line.indexOf(',', c2 + 1);
                int c4 = line.indexOf(',', c3 + 1);
                int c5 = line.indexOf(',', c4 + 1);
                int c6 = line.indexOf(',', c5 + 1);
                int c7 = line.indexOf(',', c6 + 1);
                int c8 = line.indexOf(',', c7 + 1);

                String licensePlate = line.substring(0, c1);
                String productName = line.substring(c1 + 1, c2);
                double weight = Double.parseDouble(line.substring(c2 + 1, c3));
                double price = Double.parseDouble(line.substring(c3 + 1, c4));
                String addressName = line.substring(c4 + 1, c5);
                String address = line.substring(c5 + 1, c6);
                String city = line.substring(c6 + 1, c7);
                String state = line.substring(c7 + 1, c8);
                int zipCode = Integer.parseInt(line.substring(c8 + 1));

                output.add(new Package(licensePlate, productName, weight, price,
                        new ShippingAddress(addressName, address, city, state, zipCode)));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
    
    
    
    
    

    /**
     * Returns the total Profits from passed text file. If the file does not exist 0
     * will be returned.
     * 
     * @param file file where profits are stored
     * @return profits from file
     */
    public static double loadProfit(File file) {
        if (!file.exists()) {
            return 0.0;
        }

        double output = 0.0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String line; (line = br.readLine()) != null; ) {
                output = Double.parseDouble(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    
    
    
    
    /**
     * Returns the total number of packages shipped stored in the text file. If the
     * file does not exist 0 will be returned.
     * 
     * @param file file where number of packages shipped are stored
     * @return number of packages shipped from file
     */
    public static int loadPackagesShipped(File file) {
        if (!file.exists()) {
            return 0;
        }

        int output = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String line; (line = br.readLine()) != null; ) {
                output = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    
    
    
    /**
     * Returns whether or not it was Prime Day in the previous session. If file does
     * not exist, returns false.
     * 
     * @param file file where prime day is stored
     * @return whether or not it is prime day
     */
    public static boolean loadPrimeDay(File file) {
        if (!file.exists()) {
            return false;
        }

        int counter = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String line; (line = br.readLine()) != null; ) {
                counter += Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (counter > 0);
    }

    
    
    
    
    /**
     * Saves (writes) vehicles from ArrayList of vehicles to file in CSV format one vehicle per line.
     * Each line (vehicle) has following fields separated by comma in the same order.
     * <ol>
     * <li>Vehicle Type (Truck/Drone/Cargo Plane)</li>
     * <li>Vehicle License Plate</li>
     * <li>Maximum Carry Weight</li>
     * </ol>
     * 
     * @param file     File to write vehicles to
     * @param vehicles ArrayList of vehicles to save to file
     */
    public static void saveVehicles(File file, ArrayList<Vehicle> vehicles) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            StringBuilder sb = new StringBuilder();
            for (Vehicle vehicle: vehicles) {
                sb.append(vehicle.getType() + "," + vehicle.getLicensePlate() + "," +
                        vehicle.getMaxWeight() + "\n");
            }
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("An IOException occurred. I will now print the stack trace so "
                    + "I get more information on what caused this exception:");
            e.printStackTrace();
        }
    }

    
    
    
    /**
     * Saves (writes) packages from ArrayList of package to file in CSV format one package per line.
     * Each line (package) has following fields separated by comma in the same order.
     * <ol>
     * <li>ID</li>
     * <li>Product Name</li>
     * <li>Weight</li>
     * <li>Price</li>
     * <li>Address Name</li>
     * <li>Address</li>
     * <li>City</li>
     * <li>State</li>
     * <li>ZIP Code</li>
     * </ol>
     * 
     * @param file     File to write packages to
     * @param packages ArrayList of packages to save to file
     */
    public static void savePackages(File file, ArrayList<Package> packages) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            StringBuilder sb = new StringBuilder();
            for (Package currentPackage : packages) {
                sb.append(currentPackage.getID() + "," + currentPackage.getProduct() + "," +
                        currentPackage.getWeight() + "," + currentPackage.getPrice() + "," +
                        currentPackage.getDestination().betterToString() + "\n");
            }
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            System.out.println("An IOException occurred. I will now print the stack trace so "
                    + "I get more information on what caused this exception:");
            e.printStackTrace();
        }
    }

    
    
    
    /**
     * Saves profit to text file.
     * 
     * @param file   File to write profits to
     * @param profit Total profits
     */

    public static void saveProfit(File file, double profit) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write("" + profit);
        } catch (IOException e) {
            System.out.println("An IOException occurred. I will now print the stack trace so "
                    + "I get more information on what caused this exception:");
            e.printStackTrace();
        }
    }

    
    
    
    
    /**
     * Saves number of packages shipped to text file.
     * 
     * @param file      File to write profits to
     * @param nPackages Number of packages shipped
     */

    public static void savePackagesShipped(File file, int nPackages) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            bw.write("" + nPackages);
        } catch (IOException e) {
            System.out.println("An IOException occurred. I will now print the stack trace so "
                    + "I get more information on what caused this exception:");
            e.printStackTrace();
        }
    }

    
    
    
    
    
    /**
     * Saves status of prime day to text file. If it is primeDay "1" will be
     * writtern, otherwise "0" will be written.
     * 
     * @param file     File to write profits to
     * @param primeDay Whether or not it is Prime Day
     */

    public static void savePrimeDay(File file, boolean primeDay) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
            if (primeDay) {
                bw.write("1");
            } else {
                bw.write("0");
            }
        } catch (IOException e) {
            System.out.println("An IOException occurred. I will now print the stack trace so "
                    + "I get more information on what caused this exception:");
            e.printStackTrace();
        }
    }
}