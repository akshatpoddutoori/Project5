/**
 * <h1>Package</h1> Represents a package
 */
public class Package {
    private String id;
    private String product;
    private double weight;
    private double price;
    private ShippingAddress destination;

    /**
     * Default Constructor
     */
    //============================================================================
    //TODO
    public Package() {
        this.id = "";
        this.product = "";
        this.weight = 0;
        this.price = 0;
        this.destination = new ShippingAddress();
    }
    
    //============================================================================
    /**
     * Constructor
     * 
     * @param id          id number of product
     * @param product     name of product in package
     * @param weight      weight of package
     * @param price       price of product
     * @param destination the destination of the package
     * 
     */
    //============================================================================
    //TODO
    public Package(String id, String product, double weight, double price, ShippingAddress destination) {
        this.id = id;
        this.product = product;
        this.weight = weight;
        this.price = price;
        this.destination = destination;
    }

    //============================================================================

    /**
     * @return id of package
     */
    public String getID() {
    	//TODO
    	
    	}
    
    
    
    /**
     * @return Name of product in package
     */
    public String getProduct() {
    	//TODO   
    }
    
    
    

    /**
     * @param product the product name to set
     */
    public void setProduct(String product) {
    	//TODO
    }

    
    
    
    /**
     * @return price of product in package
     */
    public double getPrice() {
    	//TODO
    }

    
    
    
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
    	//TODO
    }

    
    
    
    /**
     * @return Package weight
     */
    public double getWeight() {
    	//TODO
    }

    
    
    
    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
    	//TODO
    }

    
    
    /**
     * @return The shipping address of package
     */
    public ShippingAddress getDestination() {
    	//TODO
    }

    
    
    
    /**
     * @param destination the shipping address to set
     */
    public void setDestination(ShippingAddress destination) {
    	//TODO
    }

    
    
    /**
     * @return The package's shipping label.
     */
    public String shippingLabel() {
    	//TODO
    }

}