package sample;

public class Product {
    private String name;
    private String grouping;
    private Boolean taxable;
    private double price;

    public Product(String _name, String _grouping, boolean _taxable, double _price){
        name = _name;
        grouping = _grouping;
        taxable = _taxable;
        price = _price;
    }

    // -- Getter methods
    public String getName() { return name; }
    public Boolean getTaxable() { return taxable; }
    public String getGrouping() { return grouping; }
    public double getPrice(){ return price; }


}
