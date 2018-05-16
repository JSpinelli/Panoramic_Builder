import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Codificador extends HuffmanRetriever {
	
	public Codificador (String PathHuffman, BufferedImage im ,String nombre)throws IOException{
		
		ObjectOutputStream writer = null;

		//Declaracion del Archivo
		writer  = new ObjectOutputStream(new FileOutputStream("./"+nombre+"_Codificacion"));
		
		int Altura=im.getHeight();
		int Ancho=im.getWidth();
		
		String codificacion[]=this.getCod(PathHuffman);

		writer.writeInt(Altura);

		writer.writeInt(Ancho);
		
		int cantdig=0;
		char bits=0;
		int val;
		String codSimbolo;
	
		for (int i=0;i<Altura;i++){
			for (int j=0;j<Ancho;j++){
				Color color=new Color(im.getRGB(j,i));
				codSimbolo=codificacion[color.getBlue()]; //Obtiene la codificacion de Huffman (En un String) del color
 			    for (int h=0;h<codSimbolo.length();h++){
			    	bits=(char) (bits<<1);
			    	val=Integer.parseInt(codSimbolo.substring(h,h+1));
			    	if (val==1){
			    		bits=(char) (bits|1);
			    	}
			    	cantdig++;
			    	if (cantdig==16){
						writer.writeChar(bits);
			    		bits=0;
			    		cantdig=0;
			    	}
			    }
			}
		}
		if (cantdig<16 && cantdig!=0){
		   bits=(char) (bits<<(16-cantdig));
		}
		
		writer.writeChar(bits);

		//Escritura del archivo

		writer.close();
		
	};
	
}
