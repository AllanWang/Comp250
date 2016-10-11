import com.sun.istack.internal.Nullable;

import java.util.HashSet;
import java.util.Locale;

/*
 * Created by Allan Wang on 2016-10-11
 */

public class Tester2 {

    private static Test[] custom = {Test._10, Test._5};
    private static Test[] full = Test.values();

    //use full or custom
    private static Test[] tests = full;

    /*
     * Don't change anything else from here
     */
    private static HashSet<Test> incorrect = new HashSet<Test>();

    private enum Test {
        _1(1, true, "foo=23"),
        _2(2, false, "foo = 23"),
        _3(3, false, "true"),
        _4(4, false, "if true then a=2 else false end"),
        _5(5, false, "if true a=2 else false end"),
        _6(6, true, "if true then a=2 else a=3 end"),
        _7(7, true, "if true then if true then a=2 else if true then a=2 else b=3 end end else if true then if true then a=2 else b=3 end else if true then if true then a=2 else b=3 end else b=3 end end end"),
        _8(8, false, "if true then else end"),
        _9(9, false, "if true then a=2 else if true then a=3 else a=4 end a=5 end"),

        //The following tests are new
        _10(10, true, "if true then if false then b=99 else if true then c=0 end end end"),
        _11(11, false, "if true then b=0 end end");

        private int key;
        private boolean result;
        private String input;

        Test(int i, boolean b, String s) {
            key = i;
            result = b;
            input = s;
        }

        int getKey() {
            return key;
        }

        boolean getResult() {
            return result;
        }

        String getInput() {
            return input;
        }
    }

    public static void main(String[] args) {

        StringSplitter splitter;

        print("#, correct answer, your answer\n");

        boolean isStatement;

        for (Test t : tests) {
            splitter = new StringSplitter(t.getInput());
            isStatement = LanguageParser.isStatement(splitter);
            print("%d %b, %b", t.getKey(), t.getResult(), isStatement);
            if (t.getResult() != isStatement) incorrect.add(t);
        }

        if (!incorrect.isEmpty()) {
            print("\n\nIncorrect Test(s)\n---------\n");
            for (Test t : incorrect) {
                print("Test #%d: %s\n\tShould be %b", t.getKey(), t.getInput(), t.getResult());
            }
        } else {
            print("\nAll tests are correct!");
        }
    }

    private static void print(String s, @Nullable Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }

}
