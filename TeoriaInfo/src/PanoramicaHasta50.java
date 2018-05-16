import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class PanoramicaHasta50 extends Imagen {
		
		public PanoramicaHasta50( Vector<Imagen> imagenes ){
			super (imagenes.elementAt(0).getBufferedImage());
			int CoordenadaPrim=0;
			int CoordenadaSeg=0;
			int[]  MitadSeg=new int[2];
			int[]  MitadPrim=new int[2]; 
			double CorrPrim=-1;
			double CorrSeg=-1;
			for (int i=0;i<imagenes.size();i++){
				for (int j=0;j<imagenes.size();j++){
					if (i!=j){
						int largo1=imagenes.elementAt(i).getWidth();
						double CorrMayor=-1;
						int CoordenadaMayor=0;
						for (int k=1; k<50 ;k++){
							double[] probalidades1 = imagenes.elementAt(i).calcularProbs( largo1-(k+1) , largo1-1);
							double[] probalidades2 = imagenes.elementAt(j).calcularProbs( 0, k);
							int Coordenada= k;
							double Corr = this.calcularCorr(imagenes.elementAt(i),imagenes.elementAt(j), k, probalidades1, probalidades2);
							if (CorrMayor<Corr){
								CorrMayor=Corr;
								CoordenadaMayor=Coordenada;
							}
						}
						if (CorrMayor >= CorrPrim){
							CoordenadaSeg=CoordenadaPrim;
							CorrSeg=CorrPrim;
							MitadSeg[0]=MitadPrim[0];
							MitadSeg[1]=MitadPrim[1];
							CoordenadaPrim= CoordenadaMayor;
							CorrPrim=CorrMayor;
							MitadPrim[0]=i;
							MitadPrim[1]=j;
						}else{
							if (CorrMayor >= CorrSeg){
							CoordenadaSeg= CoordenadaMayor;
							CorrSeg=CorrMayor;
							MitadSeg[0]=i;
							MitadSeg[1]=j;
									}
							} 
					}
				}
			}
			//COMPOSICION
			int[] orden= new int[3];
			int coordenada1;
			int coordenada2;
			if (MitadPrim[0]==MitadSeg[1]){
				orden[0]=MitadSeg[0];
				orden[1]=MitadPrim[0];
				orden[2]=MitadPrim[1];
				coordenada1=CoordenadaSeg;
				coordenada2=CoordenadaPrim;
			}else{
				orden[0]=MitadPrim[0];
				orden[1]=MitadSeg[0];
				orden[2]=MitadSeg[1];
				coordenada1=CoordenadaPrim;
				coordenada2=CoordenadaSeg;
				}
				this.armarPanoramica(imagenes.elementAt(orden[0]),imagenes.elementAt(orden[1]),imagenes.elementAt(orden[2]),coordenada1,coordenada2);
			};
	
	private void armarPanoramica(Imagen im1, Imagen im2, Imagen im3, int coordenada1, int coordenada2){
		int largo=im1.getWidth()-coordenada1-coordenada2+im2.getWidth()+im3.getWidth();
		int alto=im1.getHeight();
		BufferedImage imTotal=new BufferedImage(largo,alto,BufferedImage.TYPE_INT_ARGB);
		for (int i=0;i<alto;i++){
			
			for (int j=0; j< im1.getWidth()-coordenada1;j++){
				imTotal.setRGB(j, i,im1.getRGB(j, i) );
			}
			for (int j=0; j< im2.getWidth();j++){
				imTotal.setRGB(j+im1.getWidth()-coordenada1, i,im2.getRGB(j, i) );
			}
			for (int j=0; j< im3.getWidth();j++){
				int h=j+im1.getWidth()+im2.getWidth()-coordenada1-coordenada2;
				imTotal.setRGB(h, i, im3.getRGB(j, i) );
			}
		}
		this.changeImage(imTotal);
	};
	
	public double calcularCorr(Imagen Imagen1 , Imagen Imagen2 , int fin,double[] probabilidades1,double[] probabilidades2 ){
	double Desviacion1 = Imagen1.desviacion((Imagen1.getWidth()-fin-1), Imagen1.getWidth()-1);
	double Desviacion2 = Imagen2.desviacion(0, fin);
	double Desviaciones= Desviacion1 * Desviacion2;
	double AutoCov=0;
	double AutoCorrelacion=0;
	int PosIm1=Imagen1.getWidth()-fin-1;
	for (int h=0;h<=fin;h++){
		for (int j=0;j<Imagen1.getHeight();j++){
			Color Simbolo1=new Color(Imagen1.getRGB(PosIm1+h,j));
			Color Simbolo2=new Color(Imagen2.getRGB(h,j));
			AutoCorrelacion=AutoCorrelacion+(Simbolo1.getBlue()*Simbolo2.getBlue());
		}
	}
	double MediaIm1=Imagen1.media((Imagen1.getWidth()-fin-1), Imagen1.getWidth()-1);
	double MediaIm2=Imagen2.media(0, fin);
	AutoCov=(AutoCorrelacion/((fin+1)*Imagen1.getHeight()))-(MediaIm1*MediaIm2);
	return AutoCov/Desviaciones;
	};

}
