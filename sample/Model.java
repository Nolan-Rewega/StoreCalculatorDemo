package sample;

import java.io.FileWriter;
import java.io.PrintWriter;

import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;



public class Model {

    private ArrayList<Tuple<Product, Integer>> products;
    private ArrayList<ModelSubscriber> subscribers;

    private ObservableList<String> productList;

    private double subtotal;
    private double total;
    private double tax;


    public Model(){
        products = new ArrayList<>();
        subscribers = new ArrayList<>();
        productList = FXCollections.observableArrayList();

        // -- subtotal = base price of all products selected.
        subtotal = 0.0f;
        // -- total = subtotal + tax.
        total = 0.0f;
        // -- tax = base price * 0.10 of all taxable products selected.
        tax = 0.0f;
    }


    // -- Getter methods
    public double getSubtotal() { return subtotal; }
    public double getTax() { return tax; }
    public double getTotal() { return total; }
    public ObservableList<String> getProductList() { return productList; }


    // -- Products methods
    public void addProduct(String name, String grouping, Boolean taxable, double price){
        Product newProduct = new Product(name, grouping, taxable, price);

        boolean isDuplicate = false;
        // -- Storing products in tuples to make JSON exporting easier.
        for (Tuple<Product, Integer> p : products) {
            if (p.left.getName().equals(name)){
                p.right += 1;
                isDuplicate = true;
            }
        }

        if(!isDuplicate){
            products.add(new Tuple<>(newProduct, 1));
        }

        productList.add(name + "\t\t\t (" + price + ")$");
        updateTotals(newProduct);

        notifySubscribers();
    }
    public void clearProducts(){
        products.clear();
        productList.clear();
        subtotal = 0.0f;
        total = 0.0f;
        tax = 0.0f;

        notifySubscribers();
    }
    public void updateTotals(Product product){
        if (product.getTaxable()){
            tax = roundDouble(tax + (product.getPrice() * 0.10f), 2);
        }
        subtotal = roundDouble(subtotal + product.getPrice(), 2);
        total = roundDouble( tax + subtotal, 2);
    }

    public void exportAsJSON(){
        String path = "./savedcart.json";

        JSONObject items = new JSONObject();

        for (Tuple<Product, Integer> t : products) {
            Product p = t.left;
            JSONObject pJSON = new JSONObject();

            pJSON.put("Grouping", p.getGrouping());
            pJSON.put("Taxable", p.getTaxable());
            pJSON.put("Price", p.getPrice());
            pJSON.put("Amount", t.right);

            items.put(p.getName(), pJSON);
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(items.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    // -- publish subscribe methods.
    public void addSubscriber(ModelSubscriber sub){
        subscribers.add(sub);
    }
    public void notifySubscribers(){
        subscribers.forEach(sub -> sub.ModelUpdated());
    }

    // -- Private helper method.
    private double roundDouble(double value, int decimalPlaces){
        BigDecimal bigDecimal = BigDecimal.valueOf(value);
        bigDecimal = bigDecimal.setScale(decimalPlaces, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}
