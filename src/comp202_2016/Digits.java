package comp202_2016;

/**
 * Created by Allan Wang on 2016-10-19.
 */
public class Digits {

    public static void main (String[] args) {

        //call the method with a long parameter
        reverse(123457689L); //you need the L at the end to show it's a long

        //if you are getting a long from a string (ie when you are using the args array), you don't need L:
        String number = "13579";
        long actualNumber = Long.parseLong(number);
        reverse(actualNumber);
    }

    private static boolean reverse(long number) {
        System.out.println("Original number: " + number);
        long digit; //this will hold the value for your digit
        while (number > 0) { //continue this loop until you reach 0
            digit = number % 10; //this gets the last digit of your number (of base 10)
            System.out.print(digit); //just to show you the result
            //do something with digit
            number /= 10; //this will remove the last digit of the number; it is equivalent to number = number / 10;
            //this is the end of the loop; is the number has only one digit, number /= 10 would equal 0 and the loop would end
            //if the number has more than one digit, the last digit will be removed and the loop will start over again
        }
        System.out.println(" is your number in reverse"); //this is what this current method does

        return false; //you will need to figure out what to return
    }
}
