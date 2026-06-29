package CoreJava;

class Calculators{
    public int add(int a, int b) {
        return a+b;
    }

    public double add(double a, double b){
        return a+b;
    }
}
public class MethodOverloading {
    public static void main(Strings[] args) {
        Calculators calc = new Calculators();
        System.out.println(calc.add(10.0,20.0));
        System.out.println(calc.add(10,20));
    }
}
