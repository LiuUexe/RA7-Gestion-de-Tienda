package model;
 
public class Amount
{
    final private String CURRENCY = "$";
    private double value;

 
    public Amount(double value)
    {
        this.value = Math.round(value * 100.0) / 100.0;
    }
 
    public double getValue()
    {
        return value;
    }
 
    public void setValue(double value)
    {
        this.value = value;
    }
 
    public String getCurrency()
    {
        return CURRENCY;
    }
 
    @Override
    public String toString()
    {
        return value + " " + CURRENCY;
    }
}