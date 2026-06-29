package CoreJava;

public class ConditionalStatement {
    public static void main(Strings[] args) {
        int a = 10;
        int b = 20;
        if (a > b) {
            System.out.println("a is greater than b");
        } else {
            System.out.println("a is less than or equal to b");
        }

        int i=1;
        switch(i){
            case 1: System.out.println("i is 1"); break;
            case 2:System.out.println("i is 2"); break;
            default: System.out.println("i is neither 1 nor 2");break;
        }
    }
}
