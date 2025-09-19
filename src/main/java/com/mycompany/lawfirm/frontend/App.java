package com.mycompany.lawfirm.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // Load file FXML (ví dụ LoginView.fxml trong /view)
            Parent root = FXMLLoader.load(getClass().getResource("/view/login/LoginView.fxml"));

            // Tạo Scene và gắn CSS
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

            // Cấu hình stage
            stage.setTitle("LawFirm Frontend");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
