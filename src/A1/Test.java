package A1;

/**
 * Created by Allan Wang on 2016-10-29.
 */
public class Test {

    public static void main(String[] args) {
        int[] foo = new int[10];
        int[] bar = new int[5];
        swap(foo, bar);
        System.out.println(foo.length);
    }

    private static void method(String s1) {
        System.out.println("Initial s1 is " + s1);
        s1 = s1 + "ghijk";
        System.out.println("New s1 is " + s1);

    }

    public static void swap(int[] a, int[] b) {
        int[] temp = a;
        int[] c = {1, 2, 3};
        System.out.println("Temp1 " + temp.length);
        a = b;
        System.out.println("a " + a.length);
        System.out.println("Temp2 " + temp.length);
        b = temp;
    }


}
