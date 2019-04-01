import java.util.*;
import java.io.*;

class WordCount_serial {
	private static Map<String, Integer> map = new HashMap<>();

	public static void main(String[] args) throws Exception {
		File infile = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(infile));
		String line;

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);
			while (st.hasMoreTokens()){
				String word = st.nextToken();
				Integer count = map.get(word);
				count = (count == null) ? 1 : ++count;
				map.put(word, count);

			}
		}

		for (String word: map.keySet()) {
			System.out.println(word + "\t" + map.get(word));

		}

		br.close();

	}
}