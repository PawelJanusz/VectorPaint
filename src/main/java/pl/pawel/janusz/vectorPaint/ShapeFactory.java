package pl.pawel.janusz.vectorPaint;

import javafx.geometry.Point2D;
import pl.pawel.janusz.vectorPaint.shapes.Line;
import pl.pawel.janusz.vectorPaint.shapes.Rectangle;
import pl.pawel.janusz.vectorPaint.shapes.Shape;
import pl.pawel.janusz.vectorPaint.shapes.Triangle;

public class ShapeFactory {

    public Shape get(String string) {
        String[] data = string.split(";");
        String shapeName = data[0].toLowerCase();

        switch (shapeName) {
            case "line":
                return getLine(data);
            case "rectangle":
                return getRectangle(data);
            case "triangle":
                return getTriangle(data);
        }
        return null;
    }

    private Shape getLine(String[] data) {
        //Line;73.0;25.0;73.0;25.0;0xffc0cbff;0x7fffd4ff;
        double x1 = Double.parseDouble(data[1]);
        double y1 = Double.parseDouble(data[2]);
        double x2 = Double.parseDouble(data[3]);
        double y2 = Double.parseDouble(data[4]);

        String fillColor = data[5];
        String strokeColor = data[6];

        Line.Builder builder = new Line.Builder()
                .setX1(x1)
                .setY1(y1)
                .setX2(x2)
                .setY2(y2)
                .setFillColor(fillColor)
                .setStrokeColor(strokeColor);

        return builder.build();
    }

    private Shape getRectangle(String[] data) {
        //Line;73.0;25.0;73.0;25.0;0xffc0cbff;0x7fffd4ff;
        double x = Double.parseDouble(data[1]);
        double y = Double.parseDouble(data[2]);
        double w = Double.parseDouble(data[3]);
        double h = Double.parseDouble(data[4]);

        String fillColor = data[5];
        String strokeColor = data[6];

        Rectangle.Builder builder = new Rectangle.Builder()
                .setX(x)
                .setY(y)
                .setW(w)
                .setH(h)
                .setFillColor(fillColor)
                .setStrokeColor(strokeColor);

        return builder.build();
    }

    private Shape getTriangle(String[] data) {
        //Line;73.0;25.0;73.0;25.0;0xffc0cbff;0x7fffd4ff;
        double x1 = Double.parseDouble(data[1]);
        double y1 = Double.parseDouble(data[2]);
        double x2 = Double.parseDouble(data[3]);
        double y2 = Double.parseDouble(data[4]);
        double x3 = Double.parseDouble(data[5]);
        double y3 = Double.parseDouble(data[6]);

        String fillColor = data[7];
        String strokeColor = data[8];

        Triangle.Builder builder = new Triangle.Builder()
                .setPoint1(x1, y1)
                .setPoint2(x2, y2)
                .setPoint3(x3, y3)

                .setFillColor(fillColor)
                .setStrokeColor(strokeColor);

        return builder.build();

    }
}