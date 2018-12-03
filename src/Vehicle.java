import java.util.ArrayList;

/**
 * <h1>Vehicle</h1> Represents a vehicle
 */

public class Vehicle implements Profitable {
    private String licensePlate;
    private double maxWeight;
    private double currentWeight;
    private int zipDest;
    private ArrayList<Package> packages;


    /**
     * Default Constructor
     */
    //============================================================================
    public Vehicle() {
        this.licensePlate = "";
        this.maxWeight = 0;
        this.currentWeight = 0;
        this.zipDest = 0;
        this.packages = new ArrayList<Package>();
    }
    
    //============================================================================


    /**
     * Constructor
     * 
     * @param licensePlate license plate of vehicle
     * @param maxWeight    maximum weight of vehicle
     */
    //============================================================================
    public Vehicle(String licensePlate, double maxWeight) {
        this.licensePlate = licensePlate;
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
        this.zipDest = 0;
        this.packages = new ArrayList<Package>();
    }
    
    //============================================================================

    
    /**
     * Returns the license plate of this vehicle
     * 
     * @return license plate of this vehicle
     */
    public String getLicensePlate() {
        return licensePlate;
    }


    /**
     * Updates the license plate of vehicle
     * 
     * @param licensePlate license plate to be updated to
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }


    /**
     * Returns the maximum weight this vehicle can carry
     * 
     * @return the maximum weight that this vehicle can carry
     */
    public double getMaxWeight() {
        return maxWeight;
    }


    /**
     * Updates the maximum weight of this vehicle
     * 
     * @param maxWeight max weight to be updated to
     */
    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }


    /**
     * Returns the current weight of all packages inside vehicle
     * 
     * @return current weight of all packages inside vehicle
     */
    public double getCurrentWeight() {
        return currentWeight;
    }


    /**
     * Returns the current ZIP code desitnation of the vehicle
     * 
     * @return current ZIP code destination of vehicle
     */
    public int getZipDest() {
        return zipDest;
    }
    

    /**
     * Updates the ZIP code destination of vehicle
     * 
     * @param zipDest ZIP code destination to be updated to
     */
    public void setZipDest(int zipDest) {
        this.zipDest = zipDest;
    }


    /**
     * Returns ArrayList of packages currently in Vehicle
     * 
     * @return ArrayList of packages in vehicle
     */
    public ArrayList<Package> getPackages() {
        return packages;
    }


    /**
     * Adds Package to the vehicle only if has room to carry it (adding it would not
     * cause it to go over its maximum carry weight).
     * 
     * @param pkg Package to add
     * @return whether or not it was successful in adding the package
     */
    public boolean addPackage(Package pkg) {
        if ((currentWeight + pkg.getWeight()) < maxWeight) {
            return packages.add(pkg);
        } else {
            return false;
        }
    }


    /**
     * Clears vehicle of packages and resets its weight to zero
     */
    public void empty() {
        packages.clear();
        currentWeight = 0;
    }
    

    /**
     * Returns true if the Vehicle has reached its maximum weight load, false
     * otherwise.
     * 
     * @return whether or not Vehicle is full
     */
    public boolean isFull() {
        return (currentWeight == maxWeight);
    }


    /**
     * Fills vehicle with packages with preference of date added and range of its
     * destination zip code. It will iterate over the packages intially at a range
     * of zero and fill it with as many as it can within its range without going
     * over its maximum weight. The amount the range increases is dependent on the
     * vehicle that is using this. This range it increases by after each iteration
     * is by default one.
     * 
     * @param warehousePackages List of packages to add from
     */
    public void fill(ArrayList<Package> warehousePackages) {
        int difference = 0;
        int counter = 0;

        while (counter < warehousePackages.size()) {
            for (Package currentItem : warehousePackages) {
                if (Math.abs(currentItem.getDestination().getZipCode() - zipDest) == difference) {
                    addPackage(currentItem);
                    counter++;  //iterates so that you know each item in the list has been checked
                }
            }
            difference++;   //iterates so that the difference in zip codes increments by 1
        }
    }


    @Override
    public double getProfit() {
        double income = 0;
        for (Package currentItem: packages) {
            income += currentItem.getPrice();
        }

        double costs = 0;

        return (income - costs);
    }

    @Override
    public String report() {
        return null;
        //does not have to be implemented here because it just has to be done in subclasses
    }


    public int getDistanceTraveled() {
        int difference = 0;
        for (Package currentItem : packages) {
            if (Math.abs(currentItem.getDestination().getZipCode() - zipDest) > difference) {
                difference = Math.abs(currentItem.getDestination().getZipCode() - zipDest);
            }
        }
        return difference;
    }

    public String getType() {
        return "type";
    }

}