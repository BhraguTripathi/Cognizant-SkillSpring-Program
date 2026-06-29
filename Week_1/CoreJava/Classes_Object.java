package Week_1.CoreJava;

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
}

public class Classes_Object {
    public static void main(Strings[] args) {
        int num1 = 10;
        int num2 = 20;

        Calculator calc = new Calculator();
        int sum = calc.add(num1, num2);
        System.out.println("Sum: " + sum);
    }
}
