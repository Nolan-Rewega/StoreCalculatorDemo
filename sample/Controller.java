package sample;

public class Controller {
    Model model;
    public Controller(){}

    // -- Event handler methods.
    public void handleProductButtonClicked(String name, String group, boolean taxable, double price){
        model.addProduct(name, group, taxable, price);
    }
    public void handleClearButtonClicked(){
        model.clearProducts();
    }
    public void handleExportButtonClicked(){
        model.exportAsJSON();
    }


    public void setModels(Model _model){
        model = _model;
    }
}
