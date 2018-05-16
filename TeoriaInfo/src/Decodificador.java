import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.PriorityQueue;

public class Decodificador {
	
	BufferedImage im;
	
	public Decodificador(String pathCod , String path ) throws IOException{
		
		
		ObjectInputStream br = new ObjectInputStream(new FileInputStream(pathCod));
		
		StringBuilder code=new StringBuilder();
		
		HuffmanArbol arbol= Huffman(path);
		
		int Altura=0;
		Altura = br.readInt();


		int Ancho=0;
		Ancho = br.readInt();

		BufferedImage imDecod = new BufferedImage(Ancho,Altura,BufferedImage.TYPE_INT_ARGB);		

		byte aux=br.readByte();
		
		code.append(Integer.toBinaryString((aux & 0xFF) + 0x100).substring(1));

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
		try{
		aux= br.readByte();}catch(IOException e){}
		code.append(Integer.toBinaryString((aux & 0xFF) + 0x100).substring(1));
	}
	this.im=imDecod;
	}
	
	public BufferedImage getImagen(){
		return im;
	};
	
	public int[] extraerFreqs(String path)throws IOException{
		
		int[] freqs = new int[256];
		
		BufferedReader br = new BufferedReader(new FileReader(path));

		String line=null;	
		line=br.readLine();
		
		while (line!=null){
			String tokens[]=line.split("[\t]");
			Integer asd=Integer.parseInt(tokens[0]);
			Integer fld=Integer.parseInt(tokens[1]);
			freqs[ asd ]= (fld);
			line=br.readLine();
			line=br.readLine();
		}	
		return freqs;
	}
	
	public HuffmanArbol Huffman(String path){

		int[] freqs=null;
		try {
			freqs = extraerFreqs(path);
		} catch (IOException e) {
		}
        
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
