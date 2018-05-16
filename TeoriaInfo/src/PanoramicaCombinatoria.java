import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class PanoramicaCombinatoria extends Imagen{
	public PanoramicaCombinatoria( Vector<Imagen> imagenes ){
		super (imagenes.elementAt(0).getBufferedImage());
		int CoordenadaPrim=0;
		int Restante=-1;
		int[]  MitadPrim=new int[2]; 
		double CorrPrim=-1;
		for (int i=0;i<imagenes.size();i++){
			for (int j=0;j<imagenes.size();j++){
				if (i!=j){
					int largo1=imagenes.elementAt(i).getWidth();
					for (int k=1; k<50 ;k++){
						double[] probalidades1 = imagenes.elementAt(i).calcularProbs( largo1-(k+1) , largo1-1);
						double[] probalidades2 = imagenes.elementAt(j).calcularProbs( 0, k);
						int Coordenada= k;
						double Corr = this.calcularCorr(imagenes.elementAt(i),imagenes.elementAt(j), k, probalidades1, probalidades2);
						if (CorrPrim<Corr){
							CorrPrim=Corr;
							CoordenadaPrim=Coordenada;
							MitadPrim[0]=i;
							MitadPrim[1]=j;
							if ((j==1 && i==2)||(j==2 && i==1))
							Restante=0;
							if ((j==0 && i==2)||(j==2 && i==0))
							Restante=1;
							if ((j==0 && i==1)||(j==1 && i==0))
							Restante=2;
							}
						}
					}
				}
			}
		int largo1=imagenes.elementAt(Restante).getWidth();
		double corr2=0;
		int Coordenada2=0;
		for (int k=1; k<50 ;k++){
			double[] probalidades1 = imagenes.elementAt(Restante).calcularProbs( largo1-(k+1) , largo1-1);
			double[] probalidades2 = imagenes.elementAt(MitadPrim[0]).calcularProbs( 0, k);
			Coordenada2= k;
			corr2 = this.calcularCorr(imagenes.elementAt(Restante),imagenes.elementAt(MitadPrim[0]), k, probalidades1, probalidades2);}
		largo1=imagenes.elementAt(MitadPrim[1]).getWidth();
		double corr3=0;
		int Coordenada3=0;
		for (int k=1; k<50 ;k++){
			double[] probalidades1 = imagenes.elementAt(MitadPrim[1]).calcularProbs( largo1-(k+1) , largo1-1);
			double[] probalidades2 = imagenes.elementAt(Restante).calcularProbs( 0, k);
			Coordenada3= k;
			corr3 = this.calcularCorr(imagenes.elementAt(MitadPrim[1]),imagenes.elementAt(Restante), k, probalidades1, probalidades2);}
		if(corr2>corr3){
			this.armarPanoramica(imagenes.elementAt(Restante),imagenes.elementAt(MitadPrim[0]),imagenes.elementAt(MitadPrim[1]),Coordenada2,CoordenadaPrim);
		}else{
			this.armarPanoramica(imagenes.elementAt(MitadPrim[0]),imagenes.elementAt(MitadPrim[1]),imagenes.elementAt(Restante),CoordenadaPrim,Coordenada3);
		}
			
		
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
