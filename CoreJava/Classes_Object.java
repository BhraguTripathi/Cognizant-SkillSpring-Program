package CoreJava;

class Calculator {   // class to perform basic add operation
    public int add (int a, int b){   // method to add two numbers in the calculator class
        return a+b;
    }

}

public class Classes_Object {
    public static void main(Strings[] args) {
        int num1 = 10;
        int num2 = 20;

        Calculator calc = new Calculator();  // creating object of Calculator class
        int sum = calc.add(num1, num2);
        System.out.println("Sum: " + sum);
    }
}
