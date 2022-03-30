/** ... */

/**
 * Word Occurrences
 */

/**
 * Reviews the poem "The Raven" to see how many times each unique word occurs in the poem.
 */

import org.jsoup.nodes.Document;
import java.io.IOException;
import org.jsoup.Jsoup;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* <p>This program access the poem "The Raven" by Edgar Allen Poe through the Internet using Jsoup.
 * Once the poem is recovered it is turned into a string array and then the frequency of each word is calculated.
 * From there, each unique word and its count is stored.</p>
 * <p>Users are provided a UI where they can review the word occurrences.
 * Results are displayed in descending order with the most frequently used words appearing first.
 * Within the UI the user can indicate how many results they would like to see.</p>
 * @author Nick Astor
 * @version 1.0.0 March 30, 2022
 *
 */


public class ModuleSixAssignment {
	/**
	 *  The list model that will show the word occurrence results
	 */
	public static DefaultListModel<String> listModel = new DefaultListModel<String>();

	/**
	 * Generate string array for each word in poem
	 * Removes punctuation and makes all words lowercase
	 * @param url URL to pull poem from. Assumes URL has poem located in div class="chapter"
	 * @return array of all words in poem without punctuation and lowercased
	 */
	public static String[] generate_poem(String url) {
		String poem = null;
		Document doc;
		// Loop through to account for a potential temporary connection issue
		do {
			try {
				// Connect to url and recover text from <div class="chapter"> tag
				doc = Jsoup.connect(url).get();
				poem = doc.select("div.chapter").first().text();
			} catch (IOException e) {
				// Inform user the program is going to attempt the request again
				System.out.println(
						"Error connecting to URL...Make sure the server hosting the URL is accessible\nTrying again.\n");
			}
		} while (poem == null);
		// Remove all punctuation, make lowercase, and split into array with whitespace
		// as a delimiter
		return poem.replaceAll("[-�]", " ").replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
	}

	/**
	 * Count frequency of words in passed array
	 * @param arr String array of individual words. Assumes words are lowercase and contain no punctuation
	 * @return A mapping with the key being an entry from arr and the value being the frequency in which it occurred
	 */
	public static Map<String, Integer> count_freq(String[] arr) {
		Map<String, Integer> mp = new TreeMap<>();

		// Loop to iterate over the words
		for (int i = 0; i < arr.length; i++) {
			// Condition to check if the array element is present the hash-map
			if (mp.containsKey(arr[i])) {
				mp.put(arr[i], mp.get(arr[i]) + 1);
			} else {
				mp.put(arr[i], 1);
			}
		}

		return mp;
	}

	/** 
	 * Comparable to sort mapping by value
	 * @param map Mapping to sort by value. Assumes value within mappings can be compared.
	 * @return returns mapping sorted by value
	 */
	public static <K, V extends Comparable<V>> Map<K, V> valueSort(final Map<K, V> map) {
		// Static Method with return type Map and
		// extending comparator class which compares values
		// associated with two keys
		Comparator<K> valueComparator = new Comparator<K>() {

			public int compare(K k1, K k2) {

				int comp = map.get(k2).compareTo(map.get(k1));
				if (comp == 0)
					return 1;
				else
					return comp;
			}
		};

		// SortedMap created using the comparator
		Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
		sorted.putAll(map);
		return sorted;
	}

	/**
	 * Populates UI with amount of results user indicated
	 * @param query Amount of results from frequency mapping that needs to be displayed
	 */
	public static void populateListModel(int query) {
		// Clear JList
		listModel.clear();
		// Calling the method generate_poem
		String[] words = generate_poem("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		// Calling the method count_freq
		Map<String, Integer> map = count_freq(words);
		// Calling the method valueSort
		Map<String, Integer> sortedMap = valueSort(map);
		// Get a set of the entries on the sorted map
		Set<Map.Entry<String, Integer>> set = sortedMap.entrySet();

		// Get an iterator
		Iterator<Map.Entry<String, Integer>> i = set.iterator();

		// Iterate through and print key value pair
		for (int x = 0; x < query & x < set.size(); x++) {
			Map.Entry<String, Integer> mp = (Map.Entry<String, Integer>) i.next();
			listModel.addElement((x + 1) + ": " + mp.getKey() + " - " + mp.getValue());
		}

	}

	private static void constructGUI() {
		// Create frame
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame();
		frame.setTitle("Word Occurrences");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create layout
		frame.setLayout(new GridLayout(3, 2));

		// Create Swing Components
		JLabel searchLabel = new JLabel("Enter number of results to display: ");
		JLabel responseLabel = new JLabel("Results: ");
		JList<String> stringList = new JList<String>(listModel);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(stringList);
		stringList.setLayoutOrientation(JList.VERTICAL);
		JButton transmitButton = new JButton("Get Results");
		JTextField textSearch = new JTextField();
		JLabel emptyLabel = new JLabel();

		// Create action listener
		transmitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Populate the list model
				populateListModel(Integer.parseInt(textSearch.getText()));
			}
		});

		// Adding components to panels and frame
		frame.add(searchLabel);
		frame.add(textSearch);
		frame.add(responseLabel);
		frame.add(scrollPane);
		frame.add(emptyLabel);
		frame.add(transmitButton);

		// Pack frame
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		});

	}

}
