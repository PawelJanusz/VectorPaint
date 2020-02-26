package pl.pawel.janusz.vectorPaint.shapes;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape {

    private double xc;
    private double yc;
    private double radius;

    public Circle(double x1, double y1, double x2, double y2) {
        xc = (x1+x2)/2;
        yc = (y1+y2)/2;

        double width = Math.abs(x1-x2);
        double height = Math.abs(y1-y2);
        double d = Math.min(width, height);
        radius = d/2;
    }

    private Circle(Builder builder){
        this.xc = builder.x;
        this.yc = builder.y;
        this.radius = builder.x;

        setFillColor(builder.fillColor);
        setStrokeColor(builder.strokeColor);

    }

    public void draw(GraphicsContext context) {
        double x = xc - radius;
        double y = yc - radius;
        double size = 2*radius;
        context.strokeOval(x, y, size, size);
        context.fillOval(x, y, size, size);


//        context.beginPath(); // rozpoczęcie ścieżki
//        context.moveTo(x,y);//przesuwamy sie do punktu rysowania
//        context.lineTo(x +20, y + 20); //
//        context.lineTo(x, y +20);
//        context.lineTo(x+20, y);
//        context.stroke();
//        context.fill();
//        context.closePath();
    }
    @Override
    public String getData() {
        StringBuilder builder =new StringBuilder();
        builder.append("Circle;");
        builder.append(xc).append(";");
        builder.append(yc).append(";");
        builder.append(radius).append(";");

        builder.append(getFillColor()).append(";");
        builder.append(getStrokeColor()).append(";");
        return builder.toString();
    }
    public static class Builder{
        double x;
        double y;
        double w;
        double h;

        Color fillColor = Color.RED;
        Color strokeColor = Color.GREEN;


        public Circle build(){
            return new Circle(this);
        }
        // zamiast void dając nazwę klasy nie musimy wywoływać settery w klasie ShapeFactory z nazwą tylko z samą kropką
        public Builder setX(double x) {
            this.x = x;
            return this;
        }

        public Builder setY(double y) {
            this.y = y;
            return this;
        }

        public Builder setW(double w) {
            this.w = w;
            return this;
        }

        public Builder setH(double h) {
            this.h = h;
            return this;
        }

        public Builder setFillColor(String fillColor) {
            this.fillColor = Color.valueOf(fillColor);
            return this;
        }

        public Builder setStrokeColor(String strokeColor) {
            this.strokeColor = Color.valueOf(strokeColor);
            return this;
        }
    }

}
