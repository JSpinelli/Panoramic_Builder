import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Imagen  {
	
	BufferedImage im;
	Vector<Par> codigo = new Vector<Par>();
	
	public Imagen( BufferedImage im1){
		this.im= im1;
	};
	
	protected void changeImage( BufferedImage im1){
		this.im=im1;
	};
	
	public double[] calcularProbs(int desde, int hasta){
		double[] probabilidades = new double[256];
		for (int j=0;j<255;j++){
			probabilidades[j]=0;
		}
		int cantidad=0;
		for (int i=0;i<this.getHeight();i++){
			for (int j=desde;j<=hasta;j++){
				Color color=new Color(this.getRGB(j,i));
				probabilidades [color.getBlue()] ++;
				cantidad++;
			}
		}
		for (int j=0;j<255;j++){
			probabilidades[j]=probabilidades[j]/cantidad;
		}
		return probabilidades;
	};
	
	public JFreeChart histograma(){
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	       double Pixeles[]= new double[255];
	       for (int i=0; i<255; i++){
	    	   Pixeles[i]=0;
	       }
	       boolean[] Aparecio = new boolean[255];
	       for (int i=0; i<255; i++){
	    	   Aparecio[i]=false;
	       }
	       for (int j=0;j<im.getHeight();j++)
	       {
	           for (int i=0;i<im.getWidth();i++)
	           {
	               Color color=new Color(im.getRGB(i,j));
	               int valor = color.getBlue();
	               Pixeles[valor]++;
	               if (!Aparecio[valor]){
	                   Aparecio[valor] = true;
	               }
	           }
	       }
	       
	       for (int i=0; i<255; i++){
	    	   if (Aparecio[i]){
	    		   @SuppressWarnings("rawtypes")
	    		   Comparable h = i;
	    		   dataset.setValue(Pixeles[i], "Frecuencia", h );
	    	   }
	       } 
	       JFreeChart chart = ChartFactory.createBarChart("Frecuencias de Colores",
	    		   "Color", "Frecuencia", dataset, PlotOrientation.VERTICAL,
	    		    false, true, false);
	       return chart; 
		};
		
	public int[] calcularFreqs(int desde, int hasta){
		int[] freqs = new int[256];
			for (int j=0;j<255;j++){
				freqs[j]=0;
			}
			for (int i=0;i<this.getHeight();i++){
				for (int j=desde;j<=hasta;j++){
					Color color=new Color(this.getRGB(j,i));
					freqs [color.getBlue()] ++;
				}
			}
		return freqs;
	}
	
	public double longitudMedia(){
		double[] probs = this.calcularProbs(0, this.getWidth()-1);
		double media=0;
        for (int i=0; i<codigo.size(); i++){
        	media= media + ( codigo.elementAt(i).getY().length() * probs[codigo.elementAt(i).getX()]);
        }
       return media;
	}
	
	public void Huffman(){

		int[] freqs = this.calcularFreqs(0, this.getWidth()-1);
        
    	PriorityQueue<HuffmanArbol> trees= new PriorityQueue<HuffmanArbol>();
    	
        for (int i = 0; i < freqs.length; i++){
            if (freqs[i] > 0){
                trees.offer(new HojaHuffman(freqs[i], i));
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
        
        this.recorrerArbol(trees.poll(), new StringBuilder());
        
	};
	
	private void recorrerArbol(HuffmanArbol tree, StringBuilder prefix ) {
		
        assert tree != null;
        
        if (tree instanceof HojaHuffman) {
        	HojaHuffman leaf = (HojaHuffman)tree;
 
            // Imprime probabilidad y codigo de la hoja
            Par par = new Par(leaf.valor, new String(prefix) );
            codigo.addElement(par);
 
        } else if (tree instanceof NodoHuffman) {
        	
        	NodoHuffman node = (NodoHuffman)tree;
 
            prefix=prefix.append('0');
            this.recorrerArbol(node.left, prefix);
            prefix.delete(prefix.length()-1, prefix.length());
            
            prefix=prefix.append('1');
            this.recorrerArbol(node.right, prefix);
            prefix.delete(prefix.length()-1, prefix.length());
        }
        
    }
	
	public Vector<Par> getCodigo(){
		return this.codigo;
	}
	
	public double media(int desde, int hasta){
		double[] probabilidades = this.calcularProbs(desde, hasta);
		double media=0;
		for (int i=0;i<=255;i++){
				media=media+( (i+1) * probabilidades[i]);
		}
		return media;
	};
	
	public double desviacion(int desde, int hasta){
		double desviacion=0;
		double[] probabilidades = this.calcularProbs(desde, hasta);
		double media=this.media(desde, hasta);
		for (int i=0;i<=255;i++){
				desviacion  =desviacion + ( ( Math.pow( ((i+1)-media) , 2 ) ) * probabilidades[i] );
		}
		return ( Math.sqrt(desviacion) );
	};
	
	public int getHeight(){
		return im.getHeight();
	};
	
	public int getWidth(){
		return im.getWidth();
	};
	
	public int getRGB(int x, int y){
		return im.getRGB(x, y);
	};
	
	public BufferedImage getBufferedImage(){
		return this.im;
	};

}
