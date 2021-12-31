package Fractales.utils;

import org.apache.commons.math3.complex.Complex;

/**
 * A class representing a complex rectangle, defined by two complex points, as well as utility functions
 * to manipulate it.
 * This class is final and immutable to ensure proper function of the software, and can only be
 * manipulated by creating a new instance of it.
 */
public final class ComplexRectangle {
    private final Complex p1;
    private final Complex p2;
    public enum TranslationDirection{UP, DOWN, LEFT, RIGHT};

    /**
     * 
     * @param p1
     * @param p2
     */
    public ComplexRectangle(Complex p1, Complex p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * 
     * @param x1 First point's real value
     * @param y1 First point's imaginary value
     * @param x2 Second point's real value
     * @param y2 Second point's imaginary value
     */
    public ComplexRectangle(double x1, double y1, double x2, double y2) {
        this.p1 = new Complex(x1, y1);
        this.p2 = new Complex(x2, y2);
    }

    /**
     * Returns the width of the rectangle
     * @return
     */
    public double getWidth() {
        return Math.abs(getEnd().getReal() - getStart().getReal());
    }

    /**
     * Returns the height of the rectangle
     * @return
     */
    public double getHeight() {
        return Math.abs(getEnd().getImaginary() - getStart().getImaginary());
    }

    /**
     * Returns a scaled version of the rectangle
     * @param scale The scale factor (1 is no change, &lt; 0 is smaller, &gt; 0 is larger)
     * @return
     */
    public ComplexRectangle scaled(double scale) {
        double newWidth = getWidth()*scale;
        double newHeight = newWidth;
        double z1Real = getStart().getReal() + getWidth()/2 - newWidth/2;
        double z1Imag = getStart().getImaginary() - getHeight()/2 + newHeight/2;
        double z2Real = getEnd().getReal() - getWidth()/2 + newWidth/2;
        double z2Imag = getEnd().getImaginary() + getHeight()/2 - newHeight/2;
        return new ComplexRectangle(z1Real, z1Imag, z2Real, z2Imag);
    }   

    /**
     * Returns a translated version of the rectangle
     * @param direction The translation direction
     * @param factor The translation factor
     * @return
     */
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

    /**
     * Checks if this complex rectangle is a square (same width than heightnn with small
     * error margin), and if the start point is on top left of the end point on the
     * complex plane
     * 
     * @return
     */
    public boolean isValidComplexSquare() {
        double x1 = p1.getReal();
        double y1 = p1.getImaginary();
        double x2 = p2.getReal();
        double y2 = p2.getImaginary();

        return (Math.abs(x2 - x1) - Math.abs(y2 - y1) < 1.0E-10) && x2 > x1 && y2 < y1;
    }

    /**
     * Returns the first point (start point) of the rectangle
     * @return
     */
    public Complex getStart() {
        return p1;
    }

    /**
     * Returns the second point (end point) of the rectangle
     * @return
     */
    public Complex getEnd() {
        return p2;
    }

}
