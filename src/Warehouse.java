import java.io.File;
import java.util.Scanner;

/**
 * <h1>Warehouse</h1>
 */

public class Warehouse {
	final static String folderPath = "files/";
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

            }
            if (menu == 2) {

            }
            if (menu == 3) {

            }
            if (menu == 4) {

            }
            if (menu == 5) {

            }
            if (menu == 6) {
                break;
            }
        }

    	
    	
    	//3) save data (vehicle, packages, profits, packages shipped and primeday) to files (overwriting them) using DatabaseManager
    	
    
    }


}