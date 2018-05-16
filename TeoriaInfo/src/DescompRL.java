import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DescompRL {
	
	private BufferedImage im;
	
	public DescompRL (String path){
		
		BufferedReader br=null;
		
		try {
			br = new BufferedReader(new FileReader(path+".txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line=null;
		
		try {
			line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		String delimitador = "[\t]+";
		String[] tokens = line.split(delimitador);
		
		String delimitador2 = "[ ]+";
		String[] valores = tokens[0].split(delimitador2);
		
		int Ancho=Integer.parseInt(valores[1]);
		int Alto=Integer.parseInt(valores[0]);
		BufferedImage imDecod = new BufferedImage(Ancho,Alto,BufferedImage.TYPE_INT_ARGB);
		int x=0;
		int y=0;
		for (int i=1;i<tokens.length;i++){
			valores = tokens[i].split(delimitador2);
			int contador=Integer.parseInt(valores[1]);
			int cl=Integer.parseInt(valores[0]);
			Color color=new Color(cl, cl, cl);
			for (int j=0;j<contador;j++){
				imDecod.setRGB(x, y, color.getRGB());
				y++;
				if(y==Alto){
					y=0;
					x++;
				}
			}
		}
		this.im=imDecod;
	};
	public BufferedImage getImagen(){
		return im;
	}
}
