package Esercitazione5;

public class Calcolatore implements CalcolatoreInterface {
    
    public int add(int x, int y) {
        return x + y;
    }

    public double divide(int x, int y) {
        return (1.0 * x) / (1.0 * y);
    }

    public int multiply(int x, int y) {
        return x * y;
    }

    public int substract(int x, int y) {
        return x - y;
    }
}