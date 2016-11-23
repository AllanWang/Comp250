package a4q1;

import org.omg.CORBA.OBJ_ADAPTER;

import java.util.*;


/**
 * Class for testing your implementation of the HashTable class.
 * <p>
 * Slightly modified to better reflect errors that occur - Allan
 * All tests will be compared to a HashMap to ensure that no errors are made within the tests
 */
public class HashTableTester2 {

    /**
     * Songs to be added to the Table
     */
    private static final List<Song> songs = Arrays.asList(
            new Song("Paranoid Android", "Radiohead", 1997),
            new Song("Machine Gun", "Slowdive", 1993),
            new Song("A Change Is Gonna Come", "Sam Cooke", 1964),
            new Song("15 Step", "Radiohead", 2008),
            new Song("Blue Line Swinger", "Yo La Tengo", 1995),
            new Song("Reckoner", "Radiohead", 2008),
            new Song("Ashes To Ashes", "David Bowie", 1980),
            new Song("Speed Law", "Mos Def", 1999),
            new Song("93 'Til Infinity", "Souls of Mischief", 1993),
            new Song("Season of the Shark", "Yo La Tengo", 2003),
            new Song("Cet Air-La", "France Gall", 1966),
            new Song("Space Oddity", "David Bowie", 1969),
            new Song("Il Nous Faut Regarder", "Jacques Brel", 1955),
            new Song("Happy Holidays", "Jim O'Rourke", 1999),
            new Song("Le Premier Bonheur du Jour", "Os Mutantes", 1968),
            new Song("Stretch Out And Wait", "The Smiths", 1987),
            new Song("Scream", "Black Flag", 1984),
            new Song("Europe, After the Rain", "Max Richter", 2002),
            new Song("Why Are You Looking Grave?", "Mew", 2005),
            new Song("Fallen Angel", "King Crimson", 1974),
            new Song("Milk and Honey", "Nick Drake", 2007),
            new Song("One Less Bell To Answer", "Burt Bacharach", 2003),
            new Song("A Letter To The New York Post", "Public Enemy", 1991),
            new Song("Murder Mystery", "Edan", 2005),
            new Song("Heaven's Blade", "Coil", 2005),
            new Song("Daddy's Gonna Tell You No Lie", "Sun Ra", 2005),
            new Song("Burning", "Fugazi", 1989),
            new Song("La goualante de pauvre jean", "Edith Piaf", 2007),
            new Song("Traveling Riverside Blues", "Led Zeppelin", 1982),
            new Song("Sequent C'", "Tangerine Dream", 1974),
            new Song("Mothers Of The Disappeared", "U2", 1987),
            new Song("Down to the Well", "Pixies", 1990),
            new Song("Seras-tu là?", "Michel Berger", 1994),
            new Song("Another Brick In The Wall (Part I)", "Pink Floyd", 1979),
            new Song("She Lives On A Mountain", "Gorky's Zygotic Mynci", 1999),
            new Song("Moody Dipper", "My Education", 2006));

    private static HashMap<String, Song> map;
    private static MyHashTable<String, Song> songTable;
    private static ArrayList<String> errorLog = new ArrayList<>();

    public static void main(String[] args) {
        int numBuckets = 7;

		/* Test 1:
         *     - this is just to see whether the hash table works
		 *     - hash table capacity = 7
		 *     - compression function = division
		 */

        // Initialize the hash table.   Key will be the song title.

        songTable = new MyHashTable<String, Song>(numBuckets);
        map = new HashMap<>(numBuckets);
        for (Song song : songs) {
            songTable.put(song.getTitle(), song);
            map.put(song.getTitle(), song);
        }

        int size1 = songTable.size();
        int nB1 = songTable.getNumBuckets();
        print("number of songs: " + size1);
        print("number of buckets in hashtable: " + nB1);

        //  rehashing changes the capacity of the table, but not the number of entries
        songTable.rehash();
        int size2 = songTable.size();
        int nB2 = songTable.getNumBuckets();
        print("number of songs: " + size2);
        print("number of buckets in hashtable: " + nB2);

        int sizeTrue = map.size();
        if (size1 != sizeTrue || size2 != sizeTrue) errorLog.add(s("Bad song size; both sizes should be %d", sizeTrue));
        if (nB2 / nB1 != 2)
            errorLog.add(s("Bucket size should be two times greater after rehashing; currently %d and %d", nB1, nB2));
        if (nB1 % numBuckets != 0)
            errorLog.add(s("Check your bucket size creation; should have been divisible by %d", numBuckets));

        testGet("Scream");
        testGet("Fallen Angel");
        testRemove("Fallen Angel");

        MyHashTable<String, Song>.HashIterator iter = songTable.iterator();
        int i = 0;
        while (iter.hasNext()) {
            i++;
            Song song = iter.next().getValue();
            print(song.toString());
        }
        if (i != map.size())
            errorLog.add(s("Iterator did not seem to go through all the keys; found %d of %d", i, map.size()));

        // test containsKey(),  keys(),  values()
        if (songTable.containsKey("Purple Haze"))
            errorLog.add("Table should not contain the key 'Purple Haze', but tests show it does");
        if (!songTable.containsKey("Down to the Well"))
            errorLog.add("Table should contain the key 'Purple Haze', but tests show it does not");

        System.out.print("SONG TITLES: ");
        for (String songTitle : songTable.keys())
            System.out.print(songTitle + ",");
        print("\n");
        System.out.print("ALL SONGS: ");
        for (Song song : songTable.values())
            System.out.print(song + ",");
        print("\n");

        // Display the test results
        print("---------------\nTEST RESULTS:\n---------------\n");
        for (String s : songTable.keys()) {
            map.remove(s);
        }
        for (String s : map.keySet()) {
            print(s);
        }
        if (!errorLog.isEmpty()) {
            for (String s : errorLog) print(s);
        } else {
            print("All tests passed successfully.");
        }
    }

    private static boolean compare(Song s1, Song s2) {
        if (s1 == null) return s2 == null;
        if (s2 == null) return false; //s1 is not null but s2 is
        if (!s1.getTitle().equals(s2.getTitle())) return false;
        if (!s1.getArtist().equals(s2.getArtist())) return false;
        return s1.getYear() == s2.getYear();
    }

    private static void testGet(String title) {
        if (!compare(songTable.get(title), map.get(title))) errorLog.add(s("Failed to retrieve song '%s'", title));
    }

    private static void testRemove(String title) {
        if (!compare(songTable.remove(title), map.remove(title))) {
            errorLog.add(s("Failed to remove song '%s'", title));
        } else {
            testGet(title);
        }
    }

    private static String s(String s, Object... o) {
        return String.format(Locale.CANADA, s, o);
    }

    private static void print(String s, Object... o) {
        System.out.println(s(s, o));
    }
}
