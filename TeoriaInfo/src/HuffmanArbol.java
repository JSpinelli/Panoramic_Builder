

abstract class HuffmanArbol implements Comparable<HuffmanArbol> {
	
    public final int frecuencia; // La probabilidad de este arbol
    
    public HuffmanArbol(int freqs) {
    	frecuencia = freqs; }
 
    public int compareTo(HuffmanArbol tree) {
        return  frecuencia - tree.frecuencia;
    }
}