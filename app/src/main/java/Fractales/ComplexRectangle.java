package Fractales;

import org.apache.commons.math3.complex.Complex;


public final class ComplexRectangle {
    private final Complex p1;
    private final Complex p2;
    public enum TranslationDirection{UP, DOWN, LEFT, RIGHT};

    public ComplexRectangle(Complex p1, Complex p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public ComplexRectangle(double x1, double y1, double x2, double y2) {
        this.p1 = new Complex(x1, y1);
        this.p2 = new Complex(x2, y2);
    }

    // public Complex getCenter() {
    //     double real = (getEnd().getReal() + getStart().getReal()) / 2
    //     double imag = (getEnd().getImaginary() + getStart().getImaginary()) / 2
    //     return new Complex(real, imag);
    // }

    public double getWidth() {
        return Math.abs(getEnd().getReal() - getStart().getReal());
    }

    public double getHeight() {
        return Math.abs(getEnd().getImaginary() - getStart().getImaginary());
    }

    public ComplexRectangle scaled(double scale) {
        double newWidth = getWidth()*scale;
        double newHeight = getHeight()*scale;
        double z1Real = getStart().getReal() + getWidth()/2 - newWidth/2;
        double z1Imag = getStart().getImaginary() - getHeight()/2 + newHeight/2;
        double z2Real = getEnd().getReal() - getWidth()/2 + newWidth/2;
        double z2Imag = getEnd().getImaginary() + getHeight()/2 + newHeight/2;
        return new ComplexRectangle(z1Real, z1Imag, z2Real, z2Imag);
    }

    public ComplexRectangle translated(TranslationDirection direction, double factor) {
        Complex toAdd = null;
        double delta = 0.4*factor;
        switch(direction) {
            case UP:
                toAdd = new Complex(0, delta);
                break;
            case DOWN:
                toAdd = new Complex(0, -delta);
                break;
            case LEFT:
                toAdd = new Complex(-delta, 0);
                break;
            case RIGHT:
                toAdd = new Complex(delta, 0);
                break;
        }
        return new ComplexRectangle(p1.add(toAdd), p2.add(toAdd));
    }

    public Complex getStart() {
        return p1;
    }

    public Complex getEnd() {
        return p2;
    }

}
