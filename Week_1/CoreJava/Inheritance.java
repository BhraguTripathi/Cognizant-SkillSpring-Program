package Week_1.CoreJava;

class CalculatorClass {
    public int add(int a, int b){
        return a+b;
    }

    public int sub(int a , int b){
        return a-b;
    }
}

class AdvancedCalculator extends CalculatorClass {
    public int multiply(int a , int b){
        return a*b;
    }

    public int division(int a, int b){
        return a/b;
    }
}

class VeryAdvanceCalculator extends AdvancedCalculator {
    public int modulo(int a, int b){
        return a%b;
    }

    public double power(int a, int b){
        return Math.pow(a,b);
    }
}


public class Inheritance {
    public static void main(String[] args) {
        VeryAdvanceCalculator calc = new VeryAdvanceCalculator();
        System.out.println("Addition: " + calc.add(10, 5));
        System.out.println("Subtraction: " + calc.sub(10, 5));
        System.out.println("Multiplication: " + calc.multiply(10, 5));
        System.out.println("Division: " + calc.division(10, 5));
        System.out.println("Modulo: " + calc.modulo(10, 5));
        System.out.println("Power: " + calc.power(2, 3));
    }
}
