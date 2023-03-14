public class Book implements Product {
    private double bookPrice = 0;
    private String bookItemTitle = "";

    public Book(String bookTitle, double price) {

        bookPrice = price; //can use a single variable with this.var
        bookItemTitle = bookTitle;

    }


    public double getPrice() {
        return bookPrice;
    }


    public String getName() {
        return bookItemTitle;
    }
}
