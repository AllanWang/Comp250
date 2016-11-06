import a3posted.Trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by Allan Wang on 2016-11-06.
 * Helper methods for your Trie implementation
 */
public class Tester3 {

    private static final Trie trie = new Trie();

    private static String[] words = new String[]{
            "a", "add", "adb", "cat", "dog", "potato", "97", "90.3"
    };

    private static ArrayList<String> wordList;

    public static void main(String[] args) {
        //Make sure you create and load the arraylist
        wordList = new ArrayList<String>(Arrays.asList(words));
        trie.loadKeys(wordList);

        //Check to see if all the keys were properly added; will print errors
        getAllKeys();
        //Test the contains function; will print out errors
        contains("a", "adb", "asd", "99", "9");
        //Test getPrefix; print only
        prefix("a", "arr", "hippo", "ask", "potaato", "9");
        //Test getAllPrefixMatches; will print out errors
        autoComplete("a", "b", "ad", "pot", "z", "9");

    }

    private static void contains(String... ss) {
        print("Testing contains");
        boolean correct = true;
        for (String s : ss) {
            boolean actual = wordList.contains(s);
            boolean test = trie.contains(s);
            if (actual != test) {
                correct = false;
                print("\tcontains for %s should be %b, but returned %b", s, actual, test);
            }
        }
        if (correct) print("\tAll tests passed");
    }

    private static void prefix(String... ss) {
        print("Testing getPrefix");
        for (String s : ss) {
            print("\tLongest prefix for %s: '%s'", s, trie.getPrefix(s));
        }
    }

    private static void autoComplete(String... ss) {
        print("Testing autocomplete");
        for (String s : ss) {
            ArrayList<String> matches = trie.getAllPrefixMatches(s);
            print("\t%s: %s", s, matches);
            for (String m : wordList) {
                if (m.length() < s.length()) continue;
                //I could use remove with an index but this is easier to read
                if (m.substring(0, s.length()).equals(s) && !matches.remove(m)) {
                    print("\t\tMissing autocomplete key: %s", m);
                }
            }
            if (!matches.isEmpty()) {
                print("\t\tExtra autocomplete keys:");
                for (String e : matches) {
                    print("\t\t\t%s", e);
                }
            }
        }
    }

    private static void getAllKeys() {
        print("Testing getAllPrefixMatches from root");
        ArrayList<String> list = new ArrayList<>(trie.getAllPrefixMatches(""));
        boolean correct = true;
        for (String s : wordList) {
            if (!list.remove(s)) {
                correct = false;
                print("\t%s missing from getAllPrefixMatches", s);
            }
        }
        if (!list.isEmpty()) {
            print("\tgetAllPrefixMatches contains extra keys...");
            for (String s : list) {
                print("\t\t%s", s);
            }
        } else if (correct) {
            print("\tAll tests passed");
        }
    }

    private static void print(String s, Object... o) {
        System.out.println(String.format(Locale.CANADA, s, o));
    }
}
