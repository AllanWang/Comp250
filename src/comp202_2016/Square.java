package comp202_2016;

/**
 * Created by Allan Wang on 2016-10-19.
 */
public class Square {

    public static void main(String[] args) {
        //Specify the length of a side of the square
        int size = 9;

        //for loop for each row of the square
        for (int i = 0; i < size; i++) {
            //for loop for each item in a row
            for (int j = 0; j < size; j++) {
                if (j == 0) { //first item is always a #
                    System.out.print("#");
                } else if (j == (size - 1)) { //last item is always a # and a new line -> use println
                    System.out.println("#");
                } else if (i == 0 || i == (size - 1)) { //first and last row have only #s
                    System.out.print("#");
                } else { //for all the middle rows, the inner items are whitespaces
                    System.out.print(" ");
                }
            }
        }
    }
}
