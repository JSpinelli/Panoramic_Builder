import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class DecodificadorNuevo {
	BufferedImage im;
	
public DecodificadorNuevo(String codif, String path ){
		
		StringBuilder code=new StringBuilder();
		StringBuilder linea= new StringBuilder(codif);
		HuffmanArbol arbol= Huffman(path);
		
		//Obtener Altura
		char charToDecode =linea.charAt(0);
		linea.deleteCharAt(0);
		int Altura=(int) charToDecode;
		
		//Obtener Ancho
		charToDecode =linea.charAt(0);
		linea.deleteCharAt(0);
		int Ancho=(int) charToDecode;
		
		BufferedImage imDecod = new BufferedImage(Ancho,Altura,BufferedImage.TYPE_INT_ARGB);
		charToDecode =linea.charAt(0);
		linea.deleteCharAt(0);
		code.append(this.generaBits(charToDecode));

		int j=0;
		int i=0;
		int pixel=0;
		int max=Ancho*Altura;
		HuffmanArbol treeAux= arbol;
		while (pixel<max){
			while ( code.length()>0 && pixel<max){
				String bit=code.substring(0, 1);
				code=code.delete(0, 1);
				if (treeAux instanceof NodoHuffman) {
					//0 Izquierda, 1 Derecha
					if(bit.equals("0")){
						treeAux=((NodoHuffman) treeAux).left;
					}else{
						treeAux=((NodoHuffman) treeAux).right;
					}
				}
				if (treeAux instanceof HojaHuffman){
					HojaHuffman leaf = (HojaHuffman)treeAux;
					Color color=new Color(leaf.valor, leaf.valor, leaf.valor);
					imDecod.setRGB(j, i, color.getRGB());
					pixel++;
					j++;
					if (j==Ancho){
						j=0;
						i++;
					}
					treeAux=arbol;
				}
				}
			if (linea.length()!=0){
				charToDecode =linea.charAt(0);
				linea.deleteCharAt(0);
				code.append(this.generaBits(charToDecode));
			}
		}
		this.im=imDecod;
	}
	
	public StringBuilder generaBits(char num){
		StringBuilder code= new StringBuilder();
		char mascara = 1 << 15;//desplaza el 1, 15 lugares a la izq (32768)
		for(int i = 0; i<16; i++){
			if((num & mascara)==32768){ //si el 1º bit de num es 1
				code.append(1);}
				else{
				code.append(0);}
			num = (char) (num << 1);
			}
		return code;
	};
	
	public BufferedImage getImagen(){
		return im;
	};
	
	public int[] extraerFreqs(String path){
		
		int[] freqs = new int[256];
		BufferedReader br=null;
		
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e1) {
			System.out.println("no encontro");
		}
		String line=null;	
		try {
			line=br.readLine();
		} catch (IOException e) {}
		
		while (line!=null){
			String tokens[]=line.split("[\t]");
			Integer asd=Integer.parseInt(tokens[0]);
			Integer fld=Integer.parseInt(tokens[1]);
			freqs[ asd ]= (fld);
			try {
				line=br.readLine();
				line=br.readLine();
			} catch (IOException e) {}
		}	
		return freqs;
	}
	
	public HuffmanArbol Huffman(String path){

		int[] freqs = extraerFreqs(path);
        
    	PriorityQueue<HuffmanArbol> trees= new PriorityQueue<HuffmanArbol>();
    	
        for (int i = 1; i < freqs.length; i++){
            if (freqs[i] > 0){
                trees.offer(new HojaHuffman( (int) freqs[i] , i));
            }
        }
        
        assert trees.size() > 0;
        // loop hasta que solo quede un arbol
        while (trees.size() > 1) {
        	
            // 2 arboles con menos frecuencia
        	HuffmanArbol a = trees.poll();
        	HuffmanArbol b = trees.poll();
 
            // Pone en nuevo nodo y re inserta en la cola
        	
            trees.offer(new NodoHuffman(a, b));
        }
        
        return trees.poll();
	};
}
