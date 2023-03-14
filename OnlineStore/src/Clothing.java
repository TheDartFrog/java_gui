public class Clothing implements Product {

    private double clothPrice = 0;
    private String clothItemName = "";

    public Clothing(String itemName, double price) {

        clothPrice = price; //can use a single variable with this.var
        clothItemName = itemName;

    }


    public double getPrice() {
        return clothPrice;
    }


    public String getName() {
        return clothItemName;
    }
}
