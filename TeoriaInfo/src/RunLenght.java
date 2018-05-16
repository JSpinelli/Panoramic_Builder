import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RunLenght {
	
	public RunLenght(BufferedImage im1,int tol,String path){ 
		int Valor = new Color(im1.getRGB(0, 0)).getGreen(); //Primer valor de matriz
		int cont = 0;
		String Compresor="";
		int alto=im1.getHeight();
		Compresor+=alto;
		int ancho=im1.getWidth();
		Compresor+=" "+ancho+"\t" ;
		int Tolerancia = tol;
		int RefMay = Valor + Tolerancia;
		int RefMen = Valor - Tolerancia;
		for(int i = 0; i < ancho;i++){
			for(int j = 0; j < alto; j++){
				Color c =new Color( im1.getRGB(i, j));
				if ( ((c.getGreen() <= RefMay) && c.getGreen() >= RefMen) ){
					cont++;
				}
				else{
					Compresor = Compresor +Valor+" " + cont+ "\t";
					Valor = c.getGreen();
					RefMay = Valor + Tolerancia;
					RefMen = Valor - Tolerancia;
					cont = 1;
				}
			}		
		}
		Compresor =Compresor+Valor+" "+cont +"\t" ;
		String ruta = "./"+path+"_Comp.txt";
        File archivo = new File(ruta);
        BufferedWriter bw=null;
        try {
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(Compresor);
			} catch (IOException e) {
			e.printStackTrace();
			}
        try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}     
		}
}