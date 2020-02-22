package pl.pawel.janusz.vectorPaint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class PaintJavaFX extends Application {

    //ta metoda jest zawsze kopiowana, zmieniamy tylko źródło i tytuł
    @Override
    public void start(Stage primaryStage) throws Exception { // pierwsze okno - Stage, w jednym stage może być kilka różnych Scene
        Parent root = FXMLLoader.load(getClass() //czytanie z pliku fxml
                .getResource("/paint.fxml"));
        primaryStage.setTitle("Paint");
        Scene scene = new Scene(root, primaryStage.getWidth(), // zawartość okna
                primaryStage.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args); // metoda publiczna
    }
}
