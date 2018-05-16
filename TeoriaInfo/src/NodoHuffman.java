
class NodoHuffman extends HuffmanArbol {
	
    public final HuffmanArbol left, right; // subArboles
 
    public NodoHuffman(HuffmanArbol l, HuffmanArbol r) {
        super(l.frecuencia + r.frecuencia);
        left = l;
        right = r;
    }
}