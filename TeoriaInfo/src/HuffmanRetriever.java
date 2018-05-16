import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public abstract class HuffmanRetriever {
	
	public HuffmanRetriever(){};
	
	protected String[] getCod(String CodHuffman){
		String codificacion[]=new String[256];
		BufferedReader br=null;
		
		try {
			br = new BufferedReader(new FileReader(CodHuffman));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String line=null;
		
		try {
			line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		try {
			line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		String delimitador = "[\t]+";
		String[] tokens = line.split(delimitador);
		   
		while (line != null) {
		    tokens = line.split(delimitador);
		    if (tokens.length>1){
		    	codificacion[Integer.parseInt(tokens[0])]=tokens[1];
		    }
		    try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return codificacion;
	}

}
