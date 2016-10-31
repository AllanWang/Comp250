package comp202_2016.midterm;

/**
 * Created by Allan Wang on 2016-10-30.
 */
public class ArraySwapping {

    public static void main(String[] args) {
        int[] a = new int[8];
        int[] b = new int[5];
        printLength("a1", a); //prints 8
        printLength("b1", b); //prints 5
        swapArray(a, b);
        printLength("a2", a); //prints 8
        printLength("b2", b); //prints 5
        /*
         * replica of swapArray, except it's in main
         * here, you change the references of a and b once again,
         * but they are still the exact references you are looking at when
         * printing the length
         */
        int[] temp = a;
        a = b;
        b = temp;
        printLength("a3", a); //prints 5
        printLength("b3", b); //prints 8
        /*
         * At the end of the day, the references in your main method
         * are not the same as the references in your other method
         * However, when you pass a variable through, the references
         * initially point to the same object, which is why you can modify
         * a in main by modifying a in the other method
         * To further illustrate this, let's look at the following:
         */
        a[0] = 2;
        b[0] = 5;
        swapLots(a, b); //see comments in this method
        printValue("a2", a); //prints 9
        printValue("b2", b); //prints -2
    }

    public static void swapArray(int[] a, int[] b) {
        int[] temp = a; //temp now points to same array as a (length 8)
        a = b; //a now points to same array as b(length 5)
        b = temp; //b now points to same array as temp (length 8)
        /*
         * HOWEVER: all you've done is change the references of a and b in this method
         * Nothing has changed in regards to a and b in the main
         * To have these effects shown in main, you must do this method in main
         */
    }

    public static void swapLots(int[] a, int[] b) {
        //a points to a in main, b points to b in main
        printValue("a1", a); //prints 2
        printValue("b1", b); //prints 5
        int[] temp = a; //temp points to a in main
        a = b; //a points to b in main
        b = temp; //b points to a in main
        b[0] = 9; //at index 0, b in this method has value 9, so a in main has value 9
        a[0] = -2; //same logic, b in main is now changed
    }

    private static void printLength(String tag, int[] array) {
        System.out.println(tag + ": " + array.length);
    }

    private static void printValue(String tag, int[] array) {
        System.out.println(tag + " has value " + array[0] + " at index 0");
    }
}
