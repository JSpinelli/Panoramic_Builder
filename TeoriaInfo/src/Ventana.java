 import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*; 
 import java.awt.FlowLayout;   
 import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
 import javax.swing.JFrame;
 
public class Ventana {
	
	TrabajoEspecial tp;
	JFrame Ventana;
	public boolean panoramica=false;
	public boolean terminoComprimir=false;
	public JTextField campo;
	public JTextField tolerancia;
	
	public Ventana(TrabajoEspecial tp) {
		 
		 this.tp=tp;
		 tp.ventana(this);
		 Ventana =new JFrame("Home");
		 campo = new JTextField(10);
		 tolerancia = new JTextField(10);
		 
	 };
	 
	 public void Home(){
		 
		 Ventana.dispose();
		 
		 Ventana = new JFrame("Home");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //PANEL BOTONES
		 JPanel Botones = new JPanel(new BorderLayout());
		 Botones.setLayout(new BoxLayout(Botones, BoxLayout.PAGE_AXIS));
		
		 //Acceder al menu de la primer parte
		 JButton primera = new JButton("  Generar Panoramica   ");
		 primera.setActionCommand("parte1_Menu");
		 primera.addActionListener( (ActionListener) tp );
		 Botones.add( primera,BorderLayout.CENTER);
		 
		 //Acceder al menu de la segunda parte
		 JButton segunda = new JButton(" ");
		 segunda.setText("<html>Codificacion y <br />Decodificacion </html>");
		 segunda.setActionCommand("parte2_Menu");
		 segunda.addActionListener( (ActionListener) tp );
		 Botones.add( segunda, BorderLayout.CENTER);
		 
		 //Acceder al menu de Compresion
		 JButton Comprimir = new JButton("            Comprimir            ");
		 Comprimir.setActionCommand("Comprimir_Menu");
		 Comprimir.addActionListener( (ActionListener) tp );
		 Botones.add( Comprimir, BorderLayout.CENTER);
		 
		 //Acceder al menu de Descompresion
		 JButton Descomprimir = new JButton("         Descomprimir        ");
		 Descomprimir.setActionCommand("Descomp_Menu");
		 Descomprimir.addActionListener( (ActionListener) tp );
		 Botones.add( Descomprimir, BorderLayout.CENTER);
		 
		 Ventana.add(Botones,BorderLayout.WEST);
		 
		 //Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);	 
	 };
	 
	 public void parte2_Menu(){
		 
		 Ventana.dispose();
		 
		 Ventana = new JFrame("Ingresar nombre de Archivo");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //PANEL BOTONES
		 JPanel Botones = new JPanel(new BorderLayout());
		 Botones.setLayout(new BoxLayout(Botones, BoxLayout.PAGE_AXIS));
		 
		 //Acceder al menu de la segunda parte
		 JButton siguiente = new JButton("Siguiente ");
		 siguiente.setActionCommand("PARTE2");
		 siguiente.addActionListener( (ActionListener) tp );
		 Botones.add( siguiente, BorderLayout.SOUTH);
		 
		//PANEL INGRESO DE DATOS
		 JPanel Ingresar = new JPanel(new BorderLayout());
		 JLabel label = new JLabel("Nombre de la imagen a codificar ó  que se codificó");
		 Ingresar.add(label,BorderLayout.NORTH);
		 Ingresar.add(campo,BorderLayout.SOUTH);
		 
		 Ventana.add(Ingresar, BorderLayout.EAST);
		 Ventana.add(Botones,BorderLayout.WEST);
		 
		 //Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);	 
	 }
	 
	 public void parte1_Menu(){
		 
		 Ventana.dispose();
		 
		 Ventana = new JFrame("Ingresar nombre de Archivo");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //PANEL BOTONES
		 JPanel Botones = new JPanel(new BorderLayout());
		 Botones.setLayout(new BoxLayout(Botones, BoxLayout.PAGE_AXIS));
		 
		 //Acceder al menu de la segunda parte
		 JButton siguiente = new JButton("Siguiente ");
		 siguiente.setActionCommand("PARTE1");
		 siguiente.addActionListener( (ActionListener) tp );
		 Botones.add( siguiente, BorderLayout.SOUTH);
		 
		//PANEL INGRESO DE DATOS
		 JPanel Ingresar = new JPanel(new BorderLayout());
		 JLabel label = new JLabel("Guardar imagen como:");
		 Ingresar.add(label,BorderLayout.NORTH);
		 Ingresar.add(campo,BorderLayout.SOUTH);
		 
		 Ventana.add(Ingresar, BorderLayout.EAST);
		 Ventana.add(Botones,BorderLayout.WEST);
		 
		 //Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);	 
	 }
	 
	 public void Trabajo1( Vector<Imagen> ims){
		 
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Home");
		 
		 Ventana.setLayout( new FlowLayout());
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //PANEL IMAGENES
		 JPanel Imagenes = new JPanel(new BorderLayout());
		 Imagenes.setLayout(new BoxLayout(Imagenes, BoxLayout.LINE_AXIS));
		 
		 //Imagen1
		 ImageIcon icon = new ImageIcon(ims.elementAt(0).getBufferedImage());
		 JLabel Imagen1 = new JLabel();
		 Imagen1.setIcon(icon);
		 
		 //Imagen2
		 icon = new ImageIcon(ims.elementAt(1).getBufferedImage());
		 JLabel Imagen2 = new JLabel();
		 Imagen2.setIcon(icon);
		 
		//Imagen3
		 icon = new ImageIcon(ims.elementAt(2).getBufferedImage());
		 JLabel Imagen3 = new JLabel();
		 Imagen3.setIcon(icon);
		
		 //Ubicacion de imagenes
		 Imagenes.add(Imagen1, BorderLayout.WEST);
		 Imagenes.add(Imagen2, BorderLayout.CENTER);
		 Imagenes.add(Imagen3, BorderLayout.EAST);
		 
		 //PANEL BOTONES
		 JPanel Botones = new JPanel();
		 Botones.setLayout(new BoxLayout(Botones, BoxLayout.PAGE_AXIS));
		
		 //Boton Panoramizar Ventana Corrediza
		 JButton empezar = new JButton("Ventana Corrediza");
		 empezar.setActionCommand("PANORAMIZAR");
		 empezar.addActionListener( (ActionListener) tp );
		 Botones.add( empezar,BorderLayout.CENTER);
		 
		 //Boton Panoramizar Ventana Corrediza
		 JButton empezarCombiantoria = new JButton("V. C. Combinatoria");
		 empezarCombiantoria.setActionCommand("PANORAMIZAR1Combinatoria");
		 empezarCombiantoria.addActionListener( (ActionListener) tp );
		 Botones.add( empezarCombiantoria,BorderLayout.CENTER);
		 
		 //Boton Volver a Inicio
		 JButton volver = new JButton("    Volver a Inicio    ");
		 volver.setActionCommand("VOLVERINICIO");
		 volver.addActionListener( (ActionListener) tp );
		 Botones.add( volver,BorderLayout.CENTER);
		 
		 Botones.add(Box.createRigidArea(new Dimension(5,5)));
		 
		 //Organizacion de Paneles
		 
		 Ventana.add(Imagenes,BorderLayout.WEST);
		 Ventana.add(Botones,BorderLayout.EAST);
		 
		 //Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);
		 
	 };
	 
	 public void Trabajo2( BufferedImage Panoramica){
		 
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Home");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //PANEL BOTONES
		 JPanel Botones = new JPanel(new BorderLayout());
		 
		 if (Panoramica!=null){
			 
			 //PANORAMICA
			 ImageIcon icon = new ImageIcon(Panoramica);
			 JLabel Pano = new JLabel();
			 Pano.setIcon(icon);
			
			 //Ubicacion de imagenes
			 Ventana.getContentPane().add(Pano, BorderLayout.WEST);
			 
			 //BOTON CODIFICAR
			 JButton Cod = new JButton("Codificar");
			 Cod.setActionCommand("Gen_Cod");
			 Cod.addActionListener( (ActionListener) tp );
			 Botones.add( Cod, BorderLayout.EAST);
			 
			 //BOTON DECODIFICAR
			 JButton decode = new JButton("Decodificar");
			 decode.setActionCommand("DECODIFICAR");
			 decode.addActionListener( (ActionListener) tp );
			 Botones.add( decode, BorderLayout.CENTER);
			 
		 }else{
			 JLabel Error = new JLabel("La imagen ingresada no se pudo encontrar");
			 Botones.add( Error, BorderLayout.NORTH);
		 	}
		 
		//BOTON VOLVER
		JButton Volver = new JButton("Volver");
		Volver.setActionCommand("VOLVERINICIO");
		Volver.addActionListener( (ActionListener) tp );
		Botones.add( Volver, BorderLayout.SOUTH);
		
		Ventana.add(Botones,BorderLayout.SOUTH);
		 
		//Display the window
		Ventana.pack();
		Ventana.setLocationRelativeTo(null); 
		Ventana.setVisible(true);
	 };
	 
	 public void Comprimir_Menu(){
		 
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Comprimir");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //PANEL INPUT
		 JPanel INPUT = new JPanel(new BorderLayout());
		 INPUT.setLayout(new BoxLayout(INPUT, BoxLayout.PAGE_AXIS));
		 
		 //INPUT
		JLabel label = new JLabel(" Nombre del archivo ");
		INPUT.add(label,BorderLayout.NORTH);
		INPUT.add(campo, BorderLayout.SOUTH);
		JLabel label2 = new JLabel(" Tolerancia ");
		INPUT.add(label2,BorderLayout.NORTH);
		INPUT.add(tolerancia, BorderLayout.SOUTH);

		 Ventana.add(INPUT,BorderLayout.WEST);
		 
		 //PANEL compresores
		 JPanel compresores = new JPanel(new BorderLayout());
		 compresores.setLayout(new BoxLayout(compresores, BoxLayout.PAGE_AXIS));
		 
		 //Boton comprimir
		 JButton comprimir1 = new JButton("   Comprimir   ");
		 comprimir1.setActionCommand("COMPRIMIR1");
		 comprimir1.addActionListener( (ActionListener) tp );
		 compresores.add( comprimir1,BorderLayout.CENTER );
		 
		 if (terminoComprimir){
			 JLabel termino = new JLabel(" Compresion Finalizada ");
			 compresores.add( termino,BorderLayout.CENTER );
		 }
		 
		//BOTON VOLVER
		JButton Volver = new JButton("      Volver        ");
		Volver.setActionCommand("VOLVERINICIO");
		Volver.addActionListener( (ActionListener) tp );
		compresores.add( Volver, BorderLayout.SOUTH);
		
		 Ventana.add(compresores,BorderLayout.EAST);
		 
		//Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);
	 }
	 
	 public void outPut(Imagen pano){
		 
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Home");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //Imagen1
		 ImageIcon icon = new ImageIcon(pano.getBufferedImage());
		 JLabel Pano = new JLabel();
		 Pano.setIcon(icon);
		 
		 //Ubicacion de panoramica
		 Ventana.getContentPane().add(Pano, BorderLayout.WEST);
		 
		 //PANEL BOTONES
		 JPanel Botones = new JPanel(new BorderLayout());
		 Botones.setLayout(new BoxLayout(Botones, BoxLayout.PAGE_AXIS));
		 
		 //Boton Histograma
		 JButton histograma = new JButton("   Histograma   ");
		 histograma.setActionCommand("HISTOGRAMA");
		 histograma.addActionListener( (ActionListener) tp );
		 Botones.add( histograma,BorderLayout.NORTH );
		 
		 //Boton Huffman
		 JButton huffman = new JButton("      Huffman      ");
		 huffman.setActionCommand("G_Huffman");
		 huffman.addActionListener( (ActionListener) tp );
		 Botones.add( huffman,BorderLayout.CENTER );
		 
		 //Boton GuardarDatos
		 JButton G_Datos = new JButton("Guardar Datos ");
		 G_Datos.setActionCommand("G_Datos");
		 G_Datos.addActionListener( (ActionListener) tp );
		 Botones.add( G_Datos,BorderLayout.CENTER );
		 
		 //Boton Volver a Inicio
		 JButton volver = new JButton("  Volver a Inicio");
		 volver.setActionCommand("VOLVERINICIO");
		 volver.addActionListener( (ActionListener) tp );
		 Botones.add( volver,BorderLayout.CENTER);
		 
		 Ventana.add(Botones,BorderLayout.EAST);
		
		 //Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);	 
	 }
	 
	 public void histograma(){
		
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Home");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String nombreArch=campo.getText();
		File chart = new File("./"+nombreArch+"_Histograma.jpg");
		Imagen histograma=null;
		try {
			histograma= new Imagen(ImageIO.read(chart));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Imagen1
		 ImageIcon icon = new ImageIcon(histograma.getBufferedImage());
		 JLabel histog = new JLabel();
		 histog.setIcon(icon);
		 
		 Ventana.getContentPane().add(histog, BorderLayout.WEST);
		 
		 //Volver
		 JButton Volver = new JButton("Volver");
		 Volver.setActionCommand("VOLVER_Output");
		 Volver.addActionListener( (ActionListener) tp );
		 Ventana.add( Volver );
		 
		 //Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);
	}
	
	 public void descomp_Menu (){
		
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Descomprimir");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //INPUT
		 JLabel label = new JLabel(" Nombre del archivo que se comprimio ");
		 Ventana.add(label,BorderLayout.NORTH);
		 Ventana.add(campo, BorderLayout.SOUTH);
		 
		 //Boton descomprimir
		 JButton descomprimir = new JButton("Siguiente");
		 descomprimir.setActionCommand("DESCOMPRIMIR");
		 descomprimir.addActionListener( (ActionListener) tp );
		 Ventana.add( descomprimir,BorderLayout.CENTER );
		
		//Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);
	}
	
	 public void Descomp(BufferedImage im){
		
		 Ventana.dispose();
		 
		 Ventana =new JFrame("Imagen Descomprimida");
		 
		 Ventana.setLayout( new FlowLayout() );
			
		 Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 //Imagen1
		 ImageIcon icon = new ImageIcon(im);
		 JLabel descomp = new JLabel();
		 descomp.setIcon(icon);
		 
		 //Ubicacion de imagen descomprimida
		 Ventana.getContentPane().add(descomp, BorderLayout.SOUTH);
		 
		 //Volver
		 JButton Volver = new JButton("Volver");
		 Volver.setActionCommand("VOLVERINICIO");
		 Volver.addActionListener( (ActionListener) tp );
		 Ventana.add( Volver, BorderLayout.NORTH );
		 
		//Display the window
		 Ventana.pack();
		 Ventana.setLocationRelativeTo(null); 
		 Ventana.setVisible(true);
		
	 }
	 
	 public void decodificadas(BufferedImage imSinArch, BufferedImage imConArch){
		 
		Ventana.dispose();
		 
		Ventana =new JFrame("Imagenes Decodificadas");
		 
		Ventana.setLayout( new FlowLayout() );
			
		Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		 //PANEL IMAGENES
		 JPanel Imagenes = new JPanel(new BorderLayout());
		 Imagenes.setLayout(new BoxLayout(Imagenes, BoxLayout.PAGE_AXIS));
		
		//Imagen con Archivo
		JPanel ImageneAr = new JPanel(new BorderLayout());
		ImageneAr.setLayout(new BoxLayout(ImageneAr, BoxLayout.PAGE_AXIS));
		
		JLabel tituloAR = new JLabel("Imagen Decodificada del Archivo");
		ImageIcon icon = new ImageIcon(imConArch);
		JLabel imArch = new JLabel();
		imArch.setIcon(icon);
		
		ImageneAr.add(tituloAR, BorderLayout.CENTER);
		ImageneAr.add(imArch, BorderLayout.CENTER);
		
		Imagenes.add(ImageneAr, BorderLayout.CENTER);
		
		if (imSinArch!=null){
			//Imagen sin Archivo
			JPanel ImageneSinAr = new JPanel(new BorderLayout());
			ImageneSinAr.setLayout(new BoxLayout(ImageneSinAr, BoxLayout.PAGE_AXIS));
			
			JLabel tituloSAR = new JLabel("Imagen Decodificada del String en Ejecucion");
			ImageIcon icon2 = new ImageIcon(imSinArch);
			JLabel imS_Arch = new JLabel();
			imS_Arch.setIcon(icon2);
			
			ImageneSinAr.add(tituloSAR, BorderLayout.CENTER);
			ImageneSinAr.add(imS_Arch, BorderLayout.CENTER);
			
			Imagenes.add(ImageneSinAr, BorderLayout.CENTER);
		}
		
		Ventana.add(Imagenes,BorderLayout.NORTH);
		 
		//Volver
		JButton Volver = new JButton("Volver");
		Volver.setActionCommand("VOLVERINICIO");
		Volver.addActionListener( (ActionListener) tp );
		Ventana.add( Volver, BorderLayout.SOUTH);
		 
		//Display the window
		Ventana.pack();
		Ventana.setLocationRelativeTo(null); 
		Ventana.setVisible(true);
	 }
}
