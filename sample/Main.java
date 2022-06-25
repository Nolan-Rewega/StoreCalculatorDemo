package sample;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override public void start(Stage stage) throws Exception{
        stage.setTitle("Store Demo");
        // -- Disabled resizing, But I could make everything fit to window.
        stage.setResizable(false);


        // -- Creating Model-View-Controller (MVC) components
        Model model = new Model();
        SaleView saleView = new SaleView();
        Controller controller = new Controller();

        // -- Setting MVC connections
        saleView.setModel(model);
        saleView.setController(controller);
        controller.setModels(model);

        // -- Adding Subscribers to the model
        model.addSubscriber(saleView);

        Scene scene = new Scene(saleView);
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
