import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Vector;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class TrabajoEspecial implements ActionListener {
	
	enum Actions {
	    HISTOGRAMA,
	    PANORAMIZAR,
	    HUFFMAN,
	    PANORAMIZAR2,
	    PANORAMIZAR1Combinatoria,
	    PANORAMIZAR2Combinatoria,
	    G_Huffman,
	    G_Media,
	    G_Datos,
	    VOLVER_Output,
	    Gen_Cod,
	    DECODIFICAR,
	    COMPRIMIR1,
	    DESCOMPRIMIR,
	    PARTE1,
	    PARTE2,
	    VOLVERINICIO,
	    Descomp_Menu,
	    parte2_Menu,
	    parte1_Menu,
	    Comprimir_Menu
	  }
	
	Ventana ventana;
	private Vector<Imagen> imagenes;
	private Imagen panoramica;
	private String nombreArch;

	public TrabajoEspecial(Vector<Imagen> Ims){
		this.imagenes= Ims;
	};
	
	public void actionPerformed(ActionEvent e) {
		
		//VENTANA DE LA SEGUNDA PARTE
		if (e.getActionCommand() == Actions.PARTE2.name()){
			String nombreusuario=ventana.campo.getText();
			String path="./"+nombreusuario+".png";
			File pano = new File(path);
			panoramica=null;
			try {
				panoramica =new Imagen(ImageIO.read(pano));
			} catch (IOException c) {
				System.err.println("Problema al cargar la imagen panoramica");
			}
			ventana.Trabajo2(panoramica.getBufferedImage());
		}

		//PANORAMIZAR
		if (e.getActionCommand() == Actions.PANORAMIZAR.name() || e.getActionCommand() == Actions.PANORAMIZAR2.name()  || e.getActionCommand() == Actions.PANORAMIZAR1Combinatoria.name()|| e.getActionCommand() == Actions.PANORAMIZAR2Combinatoria.name()){
			
			//EXTRAER PATH
			nombreArch=ventana.campo.getText();
			String path="./"+nombreArch+".png";
			
			//GENERAR PANORAMICA
			if (e.getActionCommand() == Actions.PANORAMIZAR.name()){
				//PANORAMIZAR CON VENTAnA CORREDIZA HASTA 50 PIXELES DE SUPERPOSICION
				this.panoramica = new PanoramicaHasta50(imagenes);}
			if (e.getActionCommand() == Actions.PANORAMIZAR1Combinatoria.name()){
				this.panoramica = new PanoramicaCombinatoria(imagenes);
			}
			
			//GUARDAR PANORAMICA
			File Pano = new File (path);
			try {
				ImageIO.write( panoramica.getBufferedImage(), "PNG", Pano);
			} catch (IOException f) {
				System.err.println("Problema al guardar la iamgen panoramica");
			}
			
			//GENERAR NUEVA VENTANA
			ventana.outPut(panoramica);
		}
		
		//CODIFICAR
		if (e.getActionCommand() == Actions.Gen_Cod.name()){
			String nombre=ventana.campo.getText();
			File Imagen = new File("./"+nombre+".png");
			try {
				panoramica= new Imagen(ImageIO.read(Imagen));
			} catch (IOException r) {}
			this.guardarFreqs(panoramica, nombre);
			this.guardarHuffman(panoramica, nombre);
			String pathHu="./"+nombre +"_Huffman.txt";
			//String pathHu="./panoramica1_Huffman.txt";
			try {
				Codificador cod = new Codificador(pathHu, panoramica.getBufferedImage(),nombre);
			} catch (IOException e1) {
			}
		}
		
		//DECODIFICAR
		if (e.getActionCommand() == Actions.DECODIFICAR.name()){
			String nombre=ventana.campo.getText();
			//DECODIFICA LA IMAGEN USANDO LA CODIFICACION GUARDADA EN UN ARCHIVO
			Decodificador decod=null;
			try {
				decod = new Decodificador("./"+nombre+"_Codificacion","./"+nombre+"_Frecuencias.txt");
			} catch (IOException e1) {
			}
			try {
				ImageIO.write( decod.getImagen(), "PNG", new File ("./"+nombre+"_Decodificada.png"));
			} catch (IOException f) {
				System.err.println("Problema al guardar imagen"+nombre+" decodificada usando archivos de por medio");
			}
			//DECODIFICA LA IMAGEN USANDO EL STRING DE LA CODIFICACION REALIZADA EN LA MISMA EJECUCION
			ventana.decodificadas(null,decod.getImagen());
			}
		
		//COMPRIMIR
		if (e.getActionCommand() == Actions.COMPRIMIR1.name()){
			String campo=ventana.campo.getText();
			String tol=ventana.tolerancia.getText();
			Integer tolerancia=null;
			BufferedImage imagen=null;
			String impath="./"+campo+".png";
			try {
				imagen= ImageIO.read(new File(impath));
				} catch (IOException f) {}

			try{
				tolerancia=Integer.parseInt(tol);
			} catch (NumberFormatException n){}
			
			//Tolerancia Default
			if (tolerancia==null){
				tolerancia=20;
			}
			//COMPRIMIR
			RunLenght rl= new RunLenght(imagen,tolerancia,campo);
			ventana.terminoComprimir=true;
			ventana.Comprimir_Menu();
		}
		
		//DESCOMPRIMIR
		if (e.getActionCommand() == Actions.DESCOMPRIMIR.name()){
			String campo="./"+ventana.campo.getText()+"_Comp";
			DescompRL des= new DescompRL(campo);
			try {
				ImageIO.write( des.getImagen(), "PNG", new File ("./"+ventana.campo.getText()+"_Descomprimida.png"));
			} catch (IOException f) {
				System.err.println("Problema al guardar la nueva iamgen");
			}
			ventana.Descomp(des.getImagen());
		}
		
		//GENERAR Y GUARDAR HISTOGRAMA
		if (e.getActionCommand() == Actions.HISTOGRAMA.name()){
			JFreeChart chart = panoramica.histograma();
			try {
			     ChartUtilities.saveChartAsJPEG(new File("./"+nombreArch+"_Histograma.jpg"), chart, 500, 300);
			} catch (IOException c) {
			     System.err.println("Hubo un problema al guardar el histograma");
			}
			ventana.histograma();
		}
		
		//GENERAR Y GUARDAR HUFFMAN
		if (e.getActionCommand() == Actions.G_Huffman.name()){
			this.guardarHuffman(panoramica, nombreArch );
			ventana.outPut(panoramica);
		}
		
		//GUARDAR DATOS
		if (e.getActionCommand() == Actions.G_Datos.name()){
			this. guardarDatos (panoramica, nombreArch);
		}
		
		//VOLVER A MENU PRINCIPAL
		if (e.getActionCommand() == Actions.VOLVERINICIO.name()){
			ventana.Home();
		}
	
		//VENTANA DE INPUT PRIMER PARTE
		if (e.getActionCommand() == Actions.parte1_Menu.name()){
			ventana.parte1_Menu();
		}
		
		//VENTANA DE LA PRIMER PARTE
		if (e.getActionCommand() == Actions.PARTE1.name()){
			ventana.Trabajo1(imagenes);
		}
		
		//VENTANA DE INPUT SEGUNDA PARTE
		if (e.getActionCommand() == Actions.parte2_Menu.name()){
			ventana.parte2_Menu();
		}
		
		//VENTANA DE DESCOMPRESION
		if (e.getActionCommand() == Actions.Descomp_Menu.name()){
			ventana.descomp_Menu();
		}
		
		//MENU DE COMPRESION
		if (e.getActionCommand() == Actions.Comprimir_Menu.name()){
			ventana.Comprimir_Menu();
		}
		
		// VOLVER_Output
		if (e.getActionCommand() == Actions.VOLVER_Output.name()){
			ventana.outPut(panoramica);
		}
		
	};
	public void guardarFreqs (Imagen panoramica, String nombre){
		File Frecuencias = new File("./"+nombre+"_Frecuencias.txt");
		BufferedWriter writer= null;
		
		try {
			writer = new BufferedWriter(new FileWriter(Frecuencias));
		} catch (IOException e2) {
			System.err.println("No se pudo cear el archivo Frecuencias");
		}
		
		int[] freqs = panoramica.calcularFreqs(0, panoramica.getWidth()-1);
		for (int i=0; i<255;i++){
			try {
				String escribir=new String();
				escribir=Integer.toString(i);
				escribir=escribir+"\t" + freqs[i]+"\n";
				writer.write(escribir,0,escribir.length());
				writer.newLine();
			} catch (IOException e1) {
				System.err.println("No se pudo guardar la probabilidad de"+i);
			}
		}
		
		try {
			writer.close();
		} catch (IOException e1) {
			System.err.println("No se pudo cerrar el archivo");
		} 
	}
	
	public void guardarDatos (Imagen panoramica, String nombre){
		
		//GUARDAR MEDIA Y DESVIACION DE LA IMAGEN
		File Media_Desviacion = new File("./"+nombre+"Media_Desviacion.txt");
		BufferedWriter writer= null;
		try {
			writer = new BufferedWriter(new FileWriter(Media_Desviacion));
		} catch (IOException e2) {
			System.err.println("No se pudo crear archivo Media_Desviacion");
		}
		
		try {
			String escribir=new String();
			escribir=Double.toString(panoramica.media(0,panoramica.getWidth()-1));
			writer.write(escribir,0,escribir.length());
			writer.newLine();
			escribir=Double.toString(panoramica.desviacion(0,panoramica.getWidth()-1));
			writer.write(escribir,0,escribir.length());
			writer.newLine();
		} catch (IOException e1) {
			System.err.println("No se pudo Guardar la Media y la Desviacion");
		}finally {
		    if (writer != null) try { writer.close(); } catch (IOException ignore) {}
		}
		
		//GUARDAR LAS PROBABILIDADES DE LOS COLORES DE LA IAMGEN
		File Probabilidades = new File("./"+nombre+"_Probabilidades.txt");
		writer= null;
		
		try {
			writer = new BufferedWriter(new FileWriter(Probabilidades));
		} catch (IOException e2) {
			System.err.println("No se pudo cear el archivo Probabilidades");
		}
		
		double[] probs = panoramica.calcularProbs(0, panoramica.getWidth()-1);
		int cantidad=panoramica.getHeight()*panoramica.getWidth();
		try {
			String wr= Integer.toString(cantidad);
			writer.write( wr);
			writer.newLine();
		} catch (IOException e) {
			}
		for (int i=0; i<255;i++){
			
			try {
				String escribir=new String();
				escribir=Integer.toString(i);
				escribir=escribir+"\t" + probs[i]+"\n";
				writer.write(escribir,0,escribir.length());
				writer.newLine();
			} catch (IOException e1) {
				System.err.println("No se pudo guardar la probabilidad de"+i);
			}
		}
		try {
			writer.close();
		} catch (IOException e1) {
			System.err.println("No se pudo cerrar el archivo");
		} 
	}
	
	public void ventana(Ventana v){
		this.ventana=v;
	}
	
	public void guardarHuffman(Imagen im,String nombre){
		
		im.Huffman();
		String arch="./"+nombre+"_Huffman.txt";
		File Cod_Huffman = new File(arch);
		BufferedWriter writer= null;
		
		try {
			writer = new BufferedWriter(new FileWriter(Cod_Huffman));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		try {
			String escribir=new String();
			escribir=Double.toString(im.longitudMedia());
			writer.write(escribir,0,escribir.length());
			writer.newLine();
		} catch (IOException e1) {
			System.err.println("No se pudo Guardar");
			e1.printStackTrace();
		}
		
		Vector<Par> codigo= new Vector<Par>();
		codigo= im.getCodigo();
		
		for (int i=0; i<codigo.size();i++){
			
			try {
				String escribir=new String();
				escribir=Integer.toString(codigo.elementAt(i).getX());
				escribir=escribir+"\t" + codigo.elementAt(i).getY()+"\n";
				writer.write(escribir,0,escribir.length());
				writer.newLine();
			} catch (IOException e1) {
				System.err.println("No se pudo Guardar");
				e1.printStackTrace();
			}
		}
		
		try {
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	public static void main(String[] args){
		
		File Imagen1 = new File("./Image1.bmp");
		File Imagen2 = new File("./Image2.bmp");
		File Imagen3 = new File("./Image3.bmp");
		Vector<Imagen> imagenes=new Vector<Imagen>();
		Imagen im1 = null;
		Imagen im2 = null;
		Imagen im3 = null;
		
		try {
			im1= new Imagen(ImageIO.read(Imagen1));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			im2 = new Imagen (ImageIO.read(Imagen2));
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		try {
			im3 = new Imagen ( ImageIO.read(Imagen3));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		imagenes.add( im1);
		imagenes.add( im2);
		imagenes.add( im3);
		TrabajoEspecial tp= new TrabajoEspecial(imagenes);
		Ventana Home=new Ventana(tp);
		Home.Home();
		
	};

}
