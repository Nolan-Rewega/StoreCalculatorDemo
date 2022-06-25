package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;


import java.util.ArrayList;

public class SaleView extends Pane implements ModelSubscriber {
    // -- models
    Model model;

    // -- Widgets
    Button save;
    Button clear;
    ArrayList<Button> productButtons;

    // -- labels
    Label cartLabel;
    Label subtotal;
    Label tax;
    Label total;

    // -- Observable List
    ListView<String> productList;

    // -- Layout
    HBox main;
    VBox cartArea;
    GridPane productLayout;
    VBox transactionLabels;
    HBox controlButtons;


    // -- constructor
    public SaleView() {
        // -- Layout
        main = new HBox();
        cartArea = new VBox();
        transactionLabels = new VBox();
        controlButtons = new HBox();
        productList = new ListView<>();
        productButtons = new ArrayList<>();

        // -- Label initialization
        cartLabel = new Label("Product Cart");

        subtotal = new Label("Subtotal: ");
        tax = new Label("Tax: ");
        total = new Label("Total: ");

        cartLabel.setStyle("-fx-font: 18 arial;");
        cartLabel.setPadding(new Insets(10,10,10,10));
        transactionLabels.setAlignment(Pos.CENTER_LEFT);
        transactionLabels.setSpacing(5);
        transactionLabels.setPadding(new Insets(5,0,0,25));
        transactionLabels.getChildren().addAll(subtotal, tax, total);

        // -- Button Initialization
        // -- Group 1. (Meats)
        productButtons.add(new Button("Beef"));
        productButtons.add(new Button("Pork"));
        productButtons.add(new Button("Chicken"));
        productButtons.add(new Button("Turkey "));

        // -- Group 2. (Computer Accessories)
        productButtons.add(new Button("Monitor"));
        productButtons.add(new Button("Mouse"));
        productButtons.add(new Button("Web cam"));
        productButtons.add(new Button("Keyboard"));

        // -- Group 3. (Bedding)
        productButtons.add(new Button("Pillow"));
        productButtons.add(new Button("Duvet"));
        productButtons.add(new Button("Blanket"));
        productButtons.add(new Button("Mattress"));



        save = new Button("Save");
        clear = new Button("Clear");

        save.setPrefSize(70,45);
        clear.setPrefSize(70,45);

        controlButtons.setSpacing(35);
        controlButtons.setAlignment(Pos.CENTER);
        controlButtons.setPadding(new Insets(10,0,15,0));
        controlButtons.getChildren().addAll(clear, save);




        // -- products GridPane initialization
        productLayout = new GridPane();
        productLayout.setPrefSize(700,500);
        GridPane.setHgrow(productLayout, Priority.ALWAYS);
        GridPane.setVgrow(productLayout, Priority.ALWAYS);
        productLayout.setAlignment(Pos.CENTER);
        productLayout.setStyle("-fx-background-color: #454540");
        productLayout.setHgap(20);
        productLayout.setVgap(20);
        productLayout.setPadding(new Insets(20,20,20,20));

        // -- column constraints
        for (int i = 0; i < 4; i++) {
            ColumnConstraints c = new ColumnConstraints();
            //c.setPercentWidth(75);
            c.setHgrow(Priority.ALWAYS);
            c.setFillWidth(true);
            productLayout.getColumnConstraints().add(c);
        }
        // -- row constraints
        for (int i = 0; i < 3; i++) {
            RowConstraints r = new RowConstraints();
            //r.setPercentHeight(50);
            r.setVgrow(Priority.ALWAYS);
            r.setFillHeight(true);
            productLayout.getRowConstraints().add(r);
        }
        // -- add Product buttons to gridPane.
        for(int r = 0; r < 3; r++ ){
            for(int c = 0; c < 4; c++) {
                productButtons.get(4 * r + c).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                productButtons.get(4 * r + c).setStyle("-fx-font: 14 arial;");
                productLayout.add(productButtons.get(4 * r + c), c, r);
            }
        }

        cartArea.setStyle("-fx-background-color: #c5c5c0");
        cartArea.setAlignment(Pos.CENTER);
        cartArea.getChildren().addAll(cartLabel, productList, transactionLabels, controlButtons);
        cartArea.setPadding(new Insets(0,10,0,10));
        main.getChildren().addAll(productLayout, cartArea);
        this.getChildren().addAll(main);
    }



    public void setModel(Model _model){
        model = _model;
    }

    public void setController(Controller controller){
        productList.setItems(model.getProductList());


        // -- Yucky Hardcode :(
        productButtons.get(0).setOnMouseClicked(e -> controller.handleProductButtonClicked("Beef  ", "Meats", true, 10.53));
        productButtons.get(1).setOnMouseClicked(e -> controller.handleProductButtonClicked("Pork  ", "Meats", true, 8.99));
        productButtons.get(2).setOnMouseClicked(e -> controller.handleProductButtonClicked("Chicken", "Meats", true, 7.50));
        productButtons.get(3).setOnMouseClicked(e -> controller.handleProductButtonClicked("Turkey", "Meats", true, 11.32));

        productButtons.get(4).setOnMouseClicked(e -> controller.handleProductButtonClicked("Monitor", "Computer Accessories", true, 85.99));
        productButtons.get(5).setOnMouseClicked(e -> controller.handleProductButtonClicked("Mouse", "Computer Accessories", false, 14.99));
        productButtons.get(6).setOnMouseClicked(e -> controller.handleProductButtonClicked("Web cam", "Computer Accessories", true, 46.99));
        productButtons.get(7).setOnMouseClicked(e -> controller.handleProductButtonClicked("Keyboard", "Computer Accessories", false, 18.00));

        productButtons.get(8).setOnMouseClicked(e -> controller.handleProductButtonClicked("Pillow", "Bedding", false, 17.29));
        productButtons.get(9).setOnMouseClicked(e -> controller.handleProductButtonClicked("Duvet", "Bedding", false, 28.00));
        productButtons.get(10).setOnMouseClicked(e -> controller.handleProductButtonClicked("Blanket", "Bedding", false, 35.00));
        productButtons.get(11).setOnMouseClicked(e -> controller.handleProductButtonClicked("Mattress", "Bedding", true, 450.00));

        save.setOnMouseClicked(e -> controller.handleExportButtonClicked());
        clear.setOnMouseClicked(e -> controller.handleClearButtonClicked());

    }


    @Override public void ModelUpdated() {
        tax.setText("Tax: " + model.getTax());
        subtotal.setText("Subtotal: " + model.getSubtotal());
        total.setText("Total: " + model.getTotal());
    }
}
