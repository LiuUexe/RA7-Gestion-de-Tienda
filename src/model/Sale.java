package model;
 
import java.util.Arrays;
 
public class Sale
{
    private Client client;
    private Product[] products;
    private Amount amount;
 
    public Sale(Client client, Product[] products, double amount)
    {
        super();
        this.client = client;
        this.products = products;
        this.amount = new Amount(amount);
    }
 
    public Client getClient()
    {
        return client;
    }
 
    public void setClient(Client client)
    {
        this.client = client;
    }
 
    public Product[] getProducts()
    {
        return products;
    }
 
    public void setProducts(Product[] products)
    {
        this.products = products;
    }
 
    public Amount getAmount()
    {
        return amount;
    }
 
    public void setAmount(Amount amount)
    {
        this.amount = amount;
    }
 
    @Override
    public String toString()
    {
        return "Sale [client=" + client + ", products=" + Arrays.toString(products) + ", amount=" + amount + "]";
    }
}