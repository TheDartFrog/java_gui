public class OnlineStore
        implements ShoppingCart{

    private Product[] products = new Product[20];
    private int productCounter = 0;

//TODO: implement missing methods from interface

    public static void main(String[] args) {
        OnlineStore store = new OnlineStore();


        Product book = new Book("The Catcher in the Rye", 10.99);

        Product shirt = new Clothing("Blue Shirt", 25.99);

        Product phone = new Electronics("iPhone 12", 999.99);

        store.addProduct(book);
        store.addProduct(shirt);
        store.addProduct(phone);

        System.out.println("Total price: " + store.getTotalPrice());

        store.removeProduct(shirt);

        System.out.println("Total price: " + store.getTotalPrice());
    }

    public void addProduct(Product product) {
        if (products.length < productCounter) {System.out.println("ERROR! Max products in cart reached! Product was not added.");}
        else {
            products[productCounter] = product;
            productCounter++;
        }
        System.out.println("Added " + product.getName() + " to cart.");


    }


    public void removeProduct(Product product) {


        int toRemoveIndex = 0;

        for (int i = 0; i<productCounter; i++)
        {

            if (products[i] == product)
            {
            toRemoveIndex = i;
            break;
            }

        }

        for (int i = toRemoveIndex + 1; i < products.length; i++)
        {

            products[i-1] = products[i];

        }

        products[products.length-1] = null;

        System.out.println("Removed " + product.getName() + " from cart.");





    }


    public double getTotalPrice() {
        double theTotalPrice = 0;

        for (int i = 0; i<products.length; i++)
        {
            if (products[i] == null)
            {
                break;
            }
            else {
                theTotalPrice = theTotalPrice + products[i].getPrice();
            }
        }

        return theTotalPrice;
    }

}