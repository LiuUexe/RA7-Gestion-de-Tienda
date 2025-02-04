package model;
 
public class Product
{
    private int id;
    private String name;
    private Amount publicPrice;
    private Amount wholesalerPrice;
    private boolean available;
    private int stock;
    private static int totalProducts;
    static double EXPIRATION_RATE = 0.60;
 
    public Product(String name, double wholesalerPrice, boolean available, int stock)
    {
        super();
        this.id = totalProducts + 1;
        this.name = name;
        this.wholesalerPrice = new Amount(wholesalerPrice);
        this.publicPrice = new Amount(wholesalerPrice * 2);
        this.available = available;
        this.stock = stock;
        totalProducts++;
    }
 
    public int getId()
    {
        return id;
    }
 
    public String getName()
    {
        return name;
    }
 
    public void setName(String name)
    {
        this.name = name;
    }
 
    public Amount getPublicPrice()
    {
        return publicPrice;
    }
 
    public Amount getWholesalerPrice()
    {
        return wholesalerPrice;
    }
 
    public boolean isAvailable()
    {
        return available;
    }
 
    public void setAvailable(boolean available)
    {
        this.available = available;
    }
 
    public int getStock()
    {
        return stock;
    }
 
    public void setStock(int stock)
    {
        this.stock = stock;
    }
 
    public void expire()
    {
        publicPrice.setValue(publicPrice.getValue() * EXPIRATION_RATE);
    }
}