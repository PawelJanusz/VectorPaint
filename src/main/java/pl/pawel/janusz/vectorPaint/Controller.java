package pl.pawel.janusz.vectorPaint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.pawel.janusz.vectorPaint.shapes.Line;
import pl.pawel.janusz.vectorPaint.shapes.Rectangle;
import pl.pawel.janusz.vectorPaint.shapes.Shape;
import pl.pawel.janusz.vectorPaint.shapes.Triangle;
import pl.pawel.janusz.vectorPaint.shapes.io.SDAFileReader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Controller {
//poprzez plik paint.xml tworzymy pola do przycisków, alt + enter na danym przycisku

    @FXML public ColorPicker fillColorPicker;
    @FXML public ColorPicker strokoColorPicker;
    @FXML public Button lineTool;
    @FXML public Button rectangleTool;
    @FXML public Button triangleTool;
    @FXML public Button circleTool;
    @FXML public Button elipseTool;
    @FXML public Button starTool;
    //aby dodać ramkę do rysowania canvas, wklejamy w pliku FXML kod:
    //       <center>
    //        <Canvas fx:id="canvas" height="400.0" width="700.0" />
    //      </center>
    @FXML public Canvas canvas;
    private double startX;
    private double startY;
    private double EndX;
    private double EndY;
    private Shape currentShape;
    private Tool currentTool = Tool.LINE;
    private List<Shape> shapeList = new ArrayList<Shape>();

    public void initialize(){ // ta metoda zawsze nazywa się initialize
        System.out.println("Hello");
        fillColorPicker.setValue(Color.PINK);
        strokoColorPicker.setValue(Color.AQUAMARINE);
        refreshCanvas();
        //akcje działania myszą wewnątrz ramki do rysowania canvas
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() { // wciśnięcie przycisku myszy
            public void handle(MouseEvent event) {
                startX  = event.getX();
                startY = event.getY();
                System.out.printf("pressed x =%f y=%f\n", startX, startY);

            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() { // puszczenie przycisku myszy
            public void handle(MouseEvent event) {
                EndX = event.getX();
                EndY = event.getY();
                System.out.printf("released x =%f y=%f\n", EndX, EndY);
                prepareShaped();
                applyShaped();
                refreshCanvas();
            }
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() { // mysz z wciśniętym przyciskiem i z przesuwaniem
            public void handle(MouseEvent event) {
                EndX = event.getX();
                EndY = event.getY();
                System.out.printf("dragged x =%f y=%f\n", EndX, EndY);
                prepareShaped();
                refreshCanvas();
            }
        });
    }

    private void applyShaped() { //dodanie obiektów do rysowania do listy
        shapeList.add(currentShape);

    }

    private void prepareShaped() {

        currentShape = createShape();
        currentShape.setFillColor(fillColorPicker.getValue());
        currentShape.setStrokeColor(strokoColorPicker.getValue());
    }

    private Shape createShape() { //tworzenie obiektów do rysowania
        switch(currentTool) {
            default:
            case LINE:
                return new Line(startX, startY, EndX, EndY);
            case RECTANGLE:
                return new Rectangle(startX, startY, EndX, EndY);
            case TRIANGLE:
                return new Triangle(startX, startY, EndX, EndY);
        }
    }

    @FXML
    public void changeTool(ActionEvent actionEvent){ // do tej metody odwołują się przyciski obiektów do rysowania
        Object source = actionEvent.getSource();
        if (source == lineTool){
            currentTool = Tool.LINE;
        }else if (source == rectangleTool){
            currentTool = Tool.RECTANGLE;
        }else if (source == triangleTool){
            currentTool = Tool.TRIANGLE;
        }else if (source == circleTool){
            currentTool = Tool.CIRCLE;
        }else if (source == starTool){
            currentTool = Tool.STAR;
        }else if (source == elipseTool){
            currentTool = Tool.ELLIPSE;
        }
        System.out.println(currentTool);
    }

    private void refreshCanvas() { // odświeżanie pola do rysowania
        GraphicsContext context = canvas.getGraphicsContext2D();

        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        context.setStroke(Color.RED);
        context.strokeRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (Shape shape: shapeList){
            shape.drawShape(context);
        }
        if (currentShape != null){
            currentShape.drawShape(context);
        }


    }

    @FXML
    public void handleSave() { // zapisywanie pliku
        Optional<String> reduce = shapeList.stream()
                .map(shape -> shape.getData())
                .reduce((acc, text) -> acc + "\n" + text);
        if (reduce.isPresent()) {
            System.out.println(reduce.get());
            FileChooser fileChooser = new FileChooser(); // klasa pozwala na przejście okienkiem do zapisu pliku
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("YOLO files (*.yolo)", "*.yolo"); // wpisanie rodzaju pliku, z jaką ścieżką
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(new Stage());// new stage daje nowe okno dialogowe, scene to zawartość okna
            if (file != null) {
                saveTextToFile(reduce.get(), file);// zapisywanie w danej ścieżce
            }
        }
    }
    private void saveTextToFile(String content, File file) { // zapisanie tekstu do pliku
        try {
            PrintWriter writer;
            writer = new PrintWriter(file);
            writer.println(content); // wydrukowanie danych w pliku
            writer.close(); //zawsze dajemy close po zapisie pliku
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleLoad(){
        FileChooser fileChooser = new FileChooser(); // klasa pozwala na przejście okienkiem do zapisu pliku
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("YOLO files (*.yolo)", "*.yolo"); // wpisanie rodzaju pliku, z jaką ścieżką
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());// new stage daje nowe okno dialogowe, scene to zawartość okna

        System.out.println(file.getAbsolutePath());

        if (file != null){
            ShapeFactory factory = new ShapeFactory();
            SDAFileReader reader = new SDAFileReader(file);
            shapeList = reader.readFile().stream()  //wyświetlanie
                    .map(string -> factory.get(string))
                    .filter(shape -> shape != null)
                    .collect(Collectors.toList());
            refreshCanvas();
        }
    }

}
