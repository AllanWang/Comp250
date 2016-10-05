package scrap;

import java.math.BigInteger;
import java.util.Locale;

/**
 * Created by Allan Wang on 2016-10-03.
 */
public class MT1 {

    public static void main(String[] args) {
//        decBinTest();
//        print(bin2dec(dec2bin(9474549)));
        binAddTest(923, 2349);
    }

    /*
     * Exercise 1
     * http://www.cim.mcgill.ca/~langer/250/E1-numbers.pdf
     */

    private static void decBinTest() {
        print("--- 34 ---", "Bin " + dec2bin(34), "Dec " + bin2dec(dec2bin(34)));
        print("--- 82 ---", "Bin " + dec2bin(82), "Dec " + bin2dec(dec2bin(82)));
    }

    /**
     * Converts an integer of base 10 to a binary string
     *
     * @param i base 10 input
     * @return binary string
     */
    private static String dec2bin(int i) {
        String s = "";
        while (i > 0) {
            s = String.valueOf(i % 2) + s;
            i /= 2;
        }
        return s;
    }

    /**
     * Converts binary string to int of base 10
     *
     * @param s binary string
     * @return int of base 10
     */
    private static int bin2dec(String s) {
        int i = 0;
        while (s.length() > 0) {
            i *= 2;
            i += Character.getNumericValue(s.charAt(0));
            s = s.substring(1);
        }
        return i;
    }

    private static String binAddition(String bin1, String bin2) {
        String s = ""; //store string
        boolean carry = false; //store carry
        char c1, c2; //store chars
        //while there are still numbers
        while (bin1.length() != 0 || bin2.length() != 0) {
            //get characters for both numbers
            if (bin1.length() != 0) {
                c1 = bin1.charAt(bin1.length() - 1);
                if (c1 != '0' && c1 != '1') throw new RuntimeException("Invalid char " + c1);
                bin1 = bin1.substring(0, bin1.length() - 1);
            } else {
                c1 = '0';
            }
            if (bin2.length() != 0) {
                c2 = bin2.charAt(bin2.length() - 1);
                if (c2 != '0' && c2 != '1') throw new RuntimeException("Invalid char " + c2);
                bin2 = bin2.substring(0, bin2.length() - 1);
            } else {
                c2 = '0';
            }
            //combine chars
            c1 += (c2 - '0');
            if (carry) c1++; //add one if carry
            if (c1 >= '2') {
                c1 -= 2; //knock down 2, 2 -> 0, 3 -> 1
                carry = true;
            } else {
                carry = false;
            }
            //append result
            s = c1 + s;
        }
        //last addition if carry
        if (carry) s = '1' + s;
        return s;
    }

    private static void binAddTest(int i1, int i2) {
        print(String.format(Locale.CANADA, "Testing: %d + %d", i1, i2));
        String bin1, bin2, binR;
        bin1 = dec2bin(i1);
        bin2 = dec2bin(i2);
        print(String.format(Locale.CANADA, "Binary: %s + %s", bin1, bin2));
        int i = i1 + i2;
        binR = binAddition(bin1, bin2);
        int ii = bin2dec(binR);
        print("Result: " + ii, "Binary: " + binR);
        if (i == ii) {
            print("Success");
        } else {
            print("Fail");
        }
    }

    /*
     * Helper methods
     */

    private static void print(Object... o) {
        for (Object oo : o) {
            System.out.println(String.valueOf(oo));
        }
    }

}
