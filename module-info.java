module sample {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens sample to javafx.fxml;
    exports sample;
}