package scrap;

/**
 * Created by User7681 on 2016-10-13.
 */
public class Series {

    public static void main(String[] args) {
        double sum = 0;
        for (int i = 2; i < 100; i++) {
            sum += Math.pow(Math.sin(Math.PI / Math.pow(3, i)), 3) / (Math.PI / Math.pow(3, i));
        }
        System.out.println("Result " + sum);
    }
}
