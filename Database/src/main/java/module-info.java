module com.database {
  requires javafx.controls;
  requires javafx.fxml;

  opens com.database to javafx.fxml;
  exports com.database;
}