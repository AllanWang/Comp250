package comp202_2016.a3;

import java.util.Scanner;

/**
 * Created by Allan Wang on 2016-11-10.
 */
public class ScannerShowcase {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in); //initialize scanner
        Info info = new Info(); //Don't worry about this

        System.out.println("What is your name?"); //Just a prompt so the user knows what to type
        info.name = input.nextLine(); //nextLine allows for spaces

        System.out.println("What is the temperature?"); //Just a prompt so the user knows what to type
        while (!input.hasNextDouble()) { //input cannot be parsed as a double
            System.out.println("Invalid input, please write the temperature as a double");
            input.nextLine(); //move on to wait for the next input
        }

        //double now exists
        info.temp = input.nextDouble();
        input.nextLine(); //get new input

        System.out.println("Is it a good day?"); //Just a prompt so the user knows what to type
        while (!input.hasNextBoolean()) { //input cannot be parsed as a boolean
            System.out.println("Invalid input, please write 'true' or 'false'");
            input.nextLine(); //move on to wait for the next input
        }

        //boolean now exists
        info.isGood = input.nextBoolean();

        input.close(); //close the scanner when your done, otherwise it will still be waiting for the input

        //Now we have all our info
        System.out.print("Hello " + info.name + ", "); //System.out.print prints a statement without a new line
        System.out.print("it is currently " + info.temp + " degrees. "); //print is useful for splitting up statements
        if (info.isGood) {
            System.out.println("It's a good day."); //This is the last statement, so I end it with println.
        } else {
            System.out.println("It's a bad day."); //Splitting up the sentence allows me to type the parts of the statement that are the same in each case only once.
        }
    }

    private static class Info { //Your classes won't be private nor static; don't worry about this
        private Double temp; //Double with a capital D is an object; don't worry about this
        private String name;
        private Boolean isGood;

        Info() { //this is a constructor; notice that there is no return value because it returns itself; your constructors will also be public
            temp = null;
            name = null;
            isGood = null;
        }

        //I have no methods here because this is an inner class and I can still access the variables; you will have your public methods to get the values

    }
}
