import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.DefaultListModel;
import org.junit.jupiter.api.*;
// import org.graalvm.compiler.debug.Assertions;

class WordOccurenceTests {

	@DisplayName("Check poem generation")
	@Test
	void shouldGeneratePoem() {
		Assertions.assertEquals(1090, ModuleSixAssignment.generate_poem("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm").length);
	}
	
	@DisplayName("Check frequency")
	@Test
	void shouldCountFreq() {
		Map<String, Integer> mp = new TreeMap<>();
		mp.put("yes",3);
		mp.put("no",2);
		String[] arr1 = {"yes","yes","no","yes","no"};
		Assertions.assertEquals(mp.get("yes"), ModuleSixAssignment.count_freq(arr1).get("yes"));
		Assertions.assertEquals(mp.get("no"), ModuleSixAssignment.count_freq(arr1).get("no"));
	}
	
	@DisplayName("Check value sort")
	@Test
	void shouldValueSort() {
		Map<String, Integer> mp = new TreeMap<>();
		mp.put("yes",2);
		mp.put("no",5);
		int[] arr = {5, 2};
		Map<String, Integer> sortedMap = ModuleSixAssignment.valueSort(mp);
		Set<Map.Entry<String, Integer>> set = sortedMap.entrySet();
		// Get an iterator
		Iterator<Map.Entry<String, Integer>> i = set.iterator();

		// Iterate through and print key value pair
		for (int x = 0; x < set.size(); x++) {
			Map.Entry<String, Integer> mp1 = (Map.Entry<String, Integer>) i.next();
			Assertions.assertEquals(arr[x], mp1.getValue());
		}
	}
	
	@DisplayName("Check list model")
	@Test
	void shouldPopulateListModel() {
		DefaultListModel<String> listModelTest = new DefaultListModel<String>();
		listModelTest.addElement("1: the - 56");
		listModelTest.addElement("2: and - 38");
		listModelTest.addElement("3: i - 32");
		ModuleSixAssignment.populateListModel(3);
		for (int x = 0; x < listModelTest.size(); x++) {
			Assertions.assertEquals(listModelTest.get(x), ModuleSixAssignment.listModel.get(x));
		}
	}

}
