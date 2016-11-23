package a4q1;

import java.util.ArrayList;


/**
 * Class for testing your implementation of the HashTable class.
 */
public class HashTableTester2 {

	/**
	 * Returns a list of songs to use for testing the hash table.
	 * @return A list of songs to use for testing the hash table
	 */
	private static ArrayList<Song> initSongList() {
		ArrayList<Song> songs = new ArrayList<Song>();
		songs.add(new Song("Paranoid Android", "Radiohead", 1997));
		songs.add(new Song("Machine Gun", "Slowdive", 1993));
		songs.add(new Song("A Change Is Gonna Come", "Sam Cooke", 1964));
		songs.add(new Song("15 Step", "Radiohead", 2008));
		songs.add(new Song("Blue Line Swinger", "Yo La Tengo", 1995));
		songs.add(new Song("Reckoner", "Radiohead", 2008));
		songs.add(new Song("Ashes To Ashes", "David Bowie", 1980));
		songs.add(new Song("Speed Law", "Mos Def", 1999));
		songs.add(new Song("93 'Til Infinity", "Souls of Mischief", 1993));
		songs.add(new Song("Season of the Shark", "Yo La Tengo", 2003));
		songs.add(new Song("Cet Air-La", "France Gall", 1966));
		songs.add(new Song("Space Oddity", "David Bowie", 1969));
		songs.add(new Song("Il Nous Faut Regarder", "Jacques Brel", 1955));
		songs.add(new Song("Happy Holidays", "Jim O'Rourke", 1999));
		songs.add(new Song("Le Premier Bonheur du Jour", "Os Mutantes", 1968));
		songs.add(new Song("Stretch Out And Wait", "The Smiths", 1987));
		songs.add(new Song("Scream", "Black Flag", 1984));
		songs.add(new Song("Europe, After the Rain", "Max Richter", 2002));
		songs.add(new Song("Why Are You Looking Grave?", "Mew", 2005));
		songs.add(new Song("Fallen Angel", "King Crimson", 1974));
		songs.add(new Song("Milk and Honey", "Nick Drake", 2007));
		songs.add(new Song("One Less Bell To Answer", "Burt Bacharach", 2003));
		songs.add(new Song("A Letter To The New York Post", "Public Enemy", 1991));
		songs.add(new Song("Murder Mystery", "Edan", 2005));
		songs.add(new Song("Heaven's Blade", "Coil", 2005));
		songs.add(new Song("Daddy's Gonna Tell You No Lie", "Sun Ra", 2005));
		songs.add(new Song("Burning", "Fugazi", 1989));
		songs.add(new Song("La goualante de pauvre jean", "Edith Piaf", 2007));
		songs.add(new Song("Traveling Riverside Blues", "Led Zeppelin", 1982));
		songs.add(new Song("Sequent C'", "Tangerine Dream", 1974));
		songs.add(new Song("Mothers Of The Disappeared", "U2", 1987));
		songs.add(new Song("Down to the Well", "Pixies", 1990));
		songs.add(new Song("Seras-tu là?", "Michel Berger", 1994));
		songs.add(new Song("Another Brick In The Wall (Part I)", "Pink Floyd", 1979));
		songs.add(new Song("She Lives On A Mountain", "Gorky's Zygotic Mynci", 1999));
		songs.add(new Song("Moody Dipper", "My Education", 2006));
		return songs;
	}
	
	public static void main(String[] args) {
		ArrayList<Song> songs = initSongList();
		MyHashTable<String,Song> songTable;
		int numBuckets = 7;
		ArrayList<String> errorLog = new ArrayList<>();
		
		/* Test 1:
		 *     - this is just to see whether the hash table works
		 *     - hash table capacity = 7
		 *     - compression function = division
		 */
		
		// Initialize the hash table.   Key will be the song title.
		
		songTable = new MyHashTable<String,Song>(numBuckets);
		for (Song song: songs) {
			songTable.put(song.getTitle(), song);
		}

		int size1 = songTable.size();
		int nB1 = songTable.getNumBuckets();
		System.out.println("number of songs: " + size1);
		System.out.println("number of buckets in hashtable: " + nB1);
				
		//  rehashing changes the capacity of the table, but not the number of entries
		songTable.rehash();
		int size2 = songTable.size();
		int nB2 = songTable.getNumBuckets();
		System.out.println("number of songs: " + size2);
		System.out.println("number of buckets in hashtable: " + nB2);
				
		// Try to retrieve a song
		StringBuffer errors = new StringBuffer();
		Song testSong1 = songTable.get("Scream");
		if (testSong1 == null || !testSong1.getArtist().equals("Black Flag") || testSong1.getYear() != 1984) {
			errors.append("Failed to retrieve song 'Scream'.\n");
		}			
		
		// Try to retrieve another song
		Song testSong2 = songTable.get("Fallen Angel");
		if (testSong2 == null || !testSong2.getArtist().equals("King Crimson") || testSong2.getYear() != 1974) {
			errors.append("Failed to retrieve song 'Fallen Angel'.\n");
		}
		
		// Try to remove a song
		Song removedSong = songTable.remove("Fallen Angel");
		Song retrievedSong = songTable.get("Fallen Angel");
		if (removedSong == null || !removedSong.getArtist().equals("King Crimson")
				|| removedSong.getYear() != 1974 || retrievedSong != null) {
			errors.append("Failed to remove song 'Fallen Angel'.\n");
		}
		
		//   PUT MORE TESTS HERE.
				
		// Display the test results
		System.out.println("---------------\nTEST 1 RESULTS:\n---------------\n");
		if (!errorLog.isEmpty()) {
			for(String s : errorLog) System.out.println(s);
		} else if (errors.length() == 0) {
			errors.append("All tests passed successfully.");
		}
		
		System.out.println(errors.toString());
		System.out.println();
		
		// Testing iterator   (NOT NECESSARY IF THEY DON'T IMPLEMENT ITERATOR
		
		MyHashTable<String,Song>.HashIterator iter= songTable.iterator();
		while(iter.hasNext())
		{
			Song song=iter.next().getValue();
			System.out.print(song);
			System.out.println();
		}
		
		// test containsKey(),  keys(),  values()
		
		System.out.println("should be false: " + songTable.containsKey("Purple Haze"));
		System.out.println("should be true: " + songTable.containsKey("Down to the Well"));
		System.out.print("SONG TITLES: ");
		for (String songTitle : songTable.keys())
			System.out.print(songTitle + ",");
		System.out.println();
		System.out.print("ALL SONGS: ");
		for (Song song:songTable.values())
			System.out.print(song + ",");
		
	}
	
}
