package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int productID;
    private String title;
    private double price;
    private int sale;
    private String details;
    private int quantityInStock;
    private String productImg;
    public Product(){}
    public Product(int productID, String title, double price, int sale, String details, int quantityInStock, String productImg) {
        this.productID=productID;
        this.title = title;
        this.price = price;
        this.sale = sale;
        this.details = details;
        this.quantityInStock = quantityInStock;
        this.productImg = productImg;
    }
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }
    public static List<Product> getProduct(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            int productID=rs.getInt("productID");
            String title = rs.getString("title");
            double price = rs.getDouble("price");
            int sale = rs.getInt("sale");
            String details = rs.getString("details");
            int quantityInStock = rs.getInt("quantityInStock");
            String productImg = rs.getString("productImg");

            Product product = new Product(productID,title, price, sale, details, quantityInStock, productImg);
            productList.add(product);
        }

        return productList;
    }
}
