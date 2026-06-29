package CoreJava;

class Check{
    private String name;
    private int age;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAge(int age){
        this.age = age;
    }

    public int getAge(){
        return age;
    }
}


public class Encapsulation {
    public static void main(String[] args) {
        Check obj = new Check();
        obj.setName("Bhragu");
        obj.setAge(22);
        System.out.println(obj.getName() + " : " + obj.getAge());
    }
}
