import java.util.*;
import java.io.*;

class hw4_3_serial {

	public static void main(String[] args) throws Exception {
		File infile = new File(args[0]);
		BufferedReader br = new BufferedReader(new FileReader(infile));
		String line;

		String attributes[] = {"preg","plas","pres","skin","insu","mass","pedi","age","class"};
		//Text atts = new Text();
		String atts;

		while ((line = br.readLine()) != null){

			String[] split = line.split(",");

			for (int x=0; x<split.length -2; x++){
		        for (int y=x+1; y<split.length-1; y++){
		          atts = attributes[x]+","+attributes[y];
		          System.out.println(atts);
		        }
			}

			break;

			//String delimiters = ",";
			//StringTokenizer st = new StringTokenizer(line, delimiters);

			//while (st.hasMoreTokens()){
			//	String word = st.nextToken();

			//	System.out.println(word);
			//}

		}
	}
}