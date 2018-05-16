
class HojaHuffman extends HuffmanArbol {
	
    public final int valor; // El Color(simbolo) de la hoja
 
    public HojaHuffman(int freqs, int val) {
        super(freqs);
        valor = val;
    }
}