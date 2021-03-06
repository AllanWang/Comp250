package A1;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Allan Wang 2016/09/18
 */
public class Tester2 {

    public static void main(String[] args) throws Exception {
        /*
         *  Which operation(s) would you like to test?
         *  May be one of PLUS, MINUS, MULTIPLY, DIVIDE, ALL
         */
        oper = Operations.PLUS;

        /*
         *  To test many random numbers, enable untilFailure
         *  (Will test up to 9999999 operations; stop it manually at any time)
         *  Leave as false to test one time
         */

        untilFailure = false;

        /*
         *  To specify number to test, add them here
         *  Leave blank for random numbers
         *  This will be ignored if untilFailure is enabled
         */

        s1 = null;
        s2 = null;

        /*
         * Specify your base here
         * Write 0 for a random base between 2 & 10
         */

        base = 10;

        start();

    }

    //That's it! Leave the rest of the file as is

    private static void start() throws Exception {
        needRandom = untilFailure || s1 == null || s2 == null;
        if (!needRandom) {
            try {
                num1 = Integer.parseInt(s1);
                num2 = Integer.parseInt(s2);
            } catch (NumberFormatException e) {
                needRandom = true;
            }
        }
        needRandomBase = base < 2 || base > 10;

        if (!untilFailure) {
            test();
        } else {
            final Timer timer = new Timer();
            timer.schedule(new StatusUpdate(), 3000, 5000);
            while (i < 9999999) {
                if (!test()) {
                    System.out.println("You did well until test #" + (i + 1));
                    return;
                }
                i++;
            }
            timer.cancel();
            System.out.println("Success! No mismatches found after " + (i - skipped) + " tests.");
        }

    }

    private static class StatusUpdate extends TimerTask {
        @Override
        public void run() {
            System.out.println("Passed " + (i - skipped) + " tests! Still going...");
        }
    }

    /*
     * Java naming conventions not used; I did this quickly
     */

    private enum Operations {
        PLUS, MINUS, MULTIPLY, DIVIDE, ALL
    }

    private static Random rnd = new Random();
    private static Operations oper;
    private static String s1;
    private static String s2;
    private static boolean untilFailure;
    private static boolean needRandom;
    private static boolean needRandomBase;
    private static int base, num1, num2, skipped = 0, i = 0;

    private static boolean test() throws Exception {
        if (needRandom) {
            if (!untilFailure || oper == Operations.DIVIDE || oper == Operations.MINUS) {
                num1 = rnd.nextInt(999999) + 999;
                if (!untilFailure && (oper == Operations.ALL || oper == Operations.DIVIDE)) { //smaller num2 to give a better example of division
                    num2 = rnd.nextInt(num1 / 100) + 1;
                } else {
                    num2 = rnd.nextInt(num1 - 998) + 999;
                }
            } else {
                num1 = rnd.nextInt(999999) + 99;
                num2 = rnd.nextInt(999999) + 99;
            }
        }
        if (needRandomBase) base = rnd.nextInt(8) + 2;

        s1 = Integer.toString(num1, base);
        s2 = Integer.toString(num2, base);

        if (base != 10) { //update num as it will be used to compare with the string later
            try {
                num1 = Integer.parseInt(s1);
                num2 = Integer.parseInt(s2);
            } catch (NumberFormatException e) {
                //number is too big; skip because I'm lazy
                skipped++;
                return true;
            }
        }

        BigInteger big1 = new BigInteger(s1, base);
        BigInteger big2 = new BigInteger(s2, base);

        NaturalNumber n1 = new NaturalNumber(s1, base);
        NaturalNumber n2 = new NaturalNumber(s2, base);

        if (!untilFailure) {
            System.out.println("n1 is    " + n1);
            System.out.println("n2 is    " + n2);
            System.out.println("base is  " + base);
            System.out.println("");
        }

        String testBig;
        String testNat;
        boolean mismatch;

        if (oper == Operations.ALL || oper == Operations.PLUS) {
            testBig = "(" + big1.add(big2).toString(base) + ")_" + base;
            testNat = n1.plus(n2).toString();
            mismatch = !testBig.equals(testNat);
            if (untilFailure && mismatch) {
                System.out.println("Mismatch for " + s1 + " + " + s2);
                System.out.println("Base " + base);
            }
            if (!untilFailure || mismatch) {
                System.out.print("sum: big1+big2 =        ");
                System.out.println(testBig);  // BigInteger
                System.out.print("sum: n1+n2     =        ");
                System.out.println(testNat);  // A1.NaturalNumber
                checkNumUnchanged();
            }
            if (untilFailure && mismatch) return false;
        }

        if (num1 > num2 && (oper == Operations.ALL || oper == Operations.MINUS)) {
            testBig = "(" + big1.subtract(big2).toString(base) + ")_" + base;
            testNat = n1.minus(n2).toString();
            mismatch = !testBig.equals(testNat);
            if (untilFailure && mismatch) {
                System.out.println("Mismatch for " + s1 + " - " + s2);
                System.out.println("Base " + base);
            }
            if (!untilFailure || mismatch) {
                System.out.print("diff: big1-big2 =       ");
                System.out.println(testBig);  // BigInteger
                System.out.print("diff: n1-n2     =       ");
                System.out.println(testNat);  // A1.NaturalNumber
                checkNumUnchanged();
            }
            if (untilFailure && mismatch) return false;
        }

        if (oper == Operations.ALL || oper == Operations.MULTIPLY) {
            testBig = "(" + big1.multiply(big2).toString(base) + ")_" + base;
            testNat = n1.times(n2).toString();
            mismatch = !testBig.equals(testNat);
            if (untilFailure && mismatch) {
                System.out.println("Mismatch for " + s1 + " * " + s2);
                System.out.println("Base " + base);
            }
            if (!untilFailure || mismatch) {
                System.out.print("multiply: big1*big2   = ");              // BigInteger
                System.out.println(testBig);  // BigInteger
                System.out.print("multiply: n1*n2       = ");               // A1.NaturalNumber
                System.out.println(testNat);  // A1.NaturalNumber
                checkNumUnchanged();
            }
            if (untilFailure && mismatch) return false;
        }

        if (oper == Operations.ALL || oper == Operations.DIVIDE) {
            testBig = "(" + big1.divide(big2).toString(base) + ")_" + base;
            testNat = n1.divide(n2).toString();
            mismatch = !testBig.equals(testNat);

            if (untilFailure && mismatch) {
                System.out.println("Mismatch for " + s1 + " / " + s2);
                System.out.println("Base " + base);
            }
            if (!untilFailure || mismatch) {
                System.out.print("divide: big1/big2     = ");              // BigInteger  
                System.out.println(testBig);  // BigInteger
                System.out.print("divide: n1/n2         = ");                 // A1.NaturalNumber
                System.out.println(testNat);  // A1.NaturalNumber
                checkNumUnchanged();
            }
            if (untilFailure && mismatch) return false;
        }
        return true;
    }

    private static void checkNumUnchanged() throws Exception {
        if (!String.valueOf(num1).equals(s1))
            throw new Exception(String.format(Locale.CANADA, "num1 has changed from %s to %d; try cloning the numbers to avoid this", s1, num1));
        if (!String.valueOf(num2).equals(s2))
            throw new Exception(String.format(Locale.CANADA, "num2 has changed from %s to %d; try cloning the numbers to avoid this", s2, num2));
    }
}
