package comp202_2016.midterm;

/**
 * Created by Allan Wang on 2016-10-29.
 */
public class Midterm {

    public static int sum(int[] array) {
        int sum = 0; //create integer that will hold the sum
        for (int i = 0; i < array.length; i++) { //loop through all elements
            sum += array[i]; //add element to sum
        }
        return sum;
    }

    public static int[] removeNegatives(int[] array) {
        //get the number of non-negative values first so you know the size of the new array
        int newSize = 0;
        for(int i = 0; i < array.length; i++) {
            if (array[i] >= 0) newSize++; //increase count by one for every non-negative number
        }
        //newSize now holds the number of non-negative values; initialize array
        int[] newArray = new int[newSize];
        int index = 0; //you'll need this to keep track of where to add the new element into newArray
        for (int i = 0; i < array.length; i++) { //loop through ORIGINAL array again
            if (array[i] >= 0) { //number is non-negative, add to new array
                newArray[index] = array[i];
                index++; //next number will now be added to new index
            }
        }
        return newArray;
    }

}

