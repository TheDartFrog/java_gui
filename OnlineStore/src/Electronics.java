public class Electronics implements Product {
    private double electroPrice = 0;
    private String electroItemName = "";

    public Electronics(String itemName, double price) {

        electroPrice = price; //can use a single variable with this.var
        electroItemName = itemName;

    }


    public double getPrice() {
        return electroPrice;
    }


    public String getName() {
        return electroItemName;
    }
}
