package comp202_2016;

/**
 * Created by Allan Wang on 2016-12-08.
 * 
 * It is very important to distinguish the difference between 
 * a reference variable and something like a primitive variable.
 * Objects are accessed through reference variables, which is like a pointer pointing to the actual object.
 * The pointer itself can be changed to point to a different object (unless it is declared final),
 * and can also point to the same object as another reference variable.
 * When you pass an object into a method, a new reference variable is created, pointing to your original object.
 * Since both variable point to the same object, modifying x in your method will modify x in your main method.
 * However, once you reassign your method's reference variable, you no longer access the same object as your main,
 * and whatever you change to that variable will not be reflected outside of that method. 
 * Below is a sample class that shows how objects are affected when passed through other methods and modified.
 * 
 * Reference variable behaviour is different from primitive variable behaviour; you've probably already seen
 * swap methods for integers, swap(int x, int y);, where changing the two in the method has no effect in the main.
 * Primitive variables are passed as a separate variable altogether, so there is no longer any association between the two.
 * 
 * It may also be worth noting the behaviours of Strings when passed through methods. Though they are objects,
 * Strings are immutable, so whenever they are modified, a new object is generated and pointed to be the reference variable.
 * Therefore, the original String passed will never be modified by other methods. 
 * The same behaviour can be seen in class such as BigInteger.
 */

public class ObjectReferences {

    /*
     * Try to see if you can figure out what gets printed at each step
     * Remember that objects, like arrays, are references. 
     * Be careful as to what the objects are referencing
     * You can run the code to see the results
     * Note that printing a Number will print the integer it currently holds
     * \t just means tab (more spaces)
     */
    public static void main (String[] args) {
        Number x = new Number(5);
        Number y = new Number(3);
        //This should be easy; what prints?
        System.out.println("Starting:\tx=" + x + "\t\ty=" + y);
        step1(x, y);
        System.out.println("Step 1:\t\tx=" + x + "\t\ty=" + y);
        step2(x, y);
        System.out.println("Step 2:\t\tx=" + x + "\t\ty=" + y);
        x = step3(x, y);
        System.out.println("Step 3:\t\tx=" + x + "\t\ty=" + y);
        y = new Number(step4(x, new Number(5)));
        System.out.println("Step 4:\t\tx=" + x + "\t\ty=" + y);
        System.out.println("y=" + goodLuck(x, x) + " ");
    }

    static void step1(Number y, Number x) {
        Number temp = x;
        x = y;
        y = temp;
    }

    static void step2(Number z, Number zz) {
        z.set(8);
        zz = z;
    }

    static Number step3(Number x, Number y) {
        y.set(-1);
        x = y;
        x.set(-2);
        return x;
    }

    static int step4(Number x, Number y) {
        Number z = x;
        x.set(9);
        y.set(z.get() + y.get());
        return y.get();
    }

    static Number goodLuck(Number i, Number j) {
        Number l = new Number(1);
        j.set(0);
        System.out.print("x=" + i + " ");
        i.set(9);
        j.set(8);
        return j;
    }
}

/*
 * You don't have to know what a static inner class is
 * Just know that this behaves the same way as a separate class in another file
 * This is a basic class that holds one integer, along with a set and get method
 */
class Number {
    int value = 0;

    Number(int i) { //this is a constructor
        value = i;
    }

    void set(int i) {
        value = i;
    }

    int get() {
        return value;
    }

    //This allows us to print the number directly
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
