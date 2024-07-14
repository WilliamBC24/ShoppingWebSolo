package ObjectModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Product {

    private int productID;
    private String title;
    private double priceIn;
    private double priceOut;
    private int numbersSold;
    private String details;
    private int quantityInStock;
    private String productImg;
    private int gender;
    private int season;
    private int category;
    private boolean isActive;

    public Product() {
    }

    public Product(int productID, String title, double priceIn, double priceOut, int numbersSold, String details, int quantityInStock, String productImg, int gender, int season, int category, boolean isActive) {
        this.productID = productID;
        this.title = title;
        this.priceIn = priceIn;
        this.priceOut = priceOut;
        this.numbersSold = numbersSold;
        this.details = details;
        this.quantityInStock = quantityInStock;
        this.productImg = productImg;
        this.gender = gender;
        this.season = season;
        this.category = category;
        this.isActive = isActive;
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

    public double getPriceIn() {
        return priceIn;
    }

    public void setPriceIn(double priceIn) {
        this.priceIn = priceIn;
    }

    public double getPriceOut() {
        return priceOut;
    }

    public void setPriceOut(double priceOut) {
        this.priceOut = priceOut;
    }

    public int getNumbersSold() {
        return numbersSold;
    }

    public void setNumbersSold(int numbersSold) {
        this.numbersSold = numbersSold;
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public static List<Product> getProduct(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            int productID = rs.getInt("productID");
            String title = rs.getString("title");
            double priceIn = rs.getDouble("priceIn");
            double priceOut = rs.getDouble("priceOut");
            int numbersSold = rs.getInt("numbersSold");
            String details = rs.getString("details");
            int quantityInStock = rs.getInt("quantityInStock");
            String productImg = rs.getString("productImg");
            int gender = rs.getInt("gender");
            int season = rs.getInt("season");
            int category = rs.getInt("category");
            boolean active=rs.getBoolean("isActive");

            Product product = new Product(productID, title, priceIn, priceOut, numbersSold, details, quantityInStock, productImg, gender, season, category, active);
            productList.add(product);
        }

        return productList;
    }

    public void oneProduct(ResultSet rs) throws SQLException {
        this.productID = rs.getInt("productID");
        this.title = rs.getString("title");
        this.priceIn = rs.getDouble("priceIn");
        this.priceOut = rs.getDouble("priceOut");
        this.numbersSold = rs.getInt("numbersSold");
        this.details = rs.getString("details");
        this.quantityInStock = rs.getInt("quantityInStock");
        this.productImg = rs.getString("productImg");
        this.gender = rs.getInt("gender");
        this.season = rs.getInt("season");
        this.category = rs.getInt("category");
        this.isActive=rs.getBoolean("isActive");
    }
}
