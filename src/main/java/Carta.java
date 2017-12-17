public class Carta implements Cloneable{
	private int valor;

	private int elemento;
	public final static char ELEMENTOCORAZON=3;  //Corazones 0  A  2665
	public final static char ELEMENTOCOCO=4;  //Cocos 1         B  2666
	public final static char ELEMENTOTREBOL=5;  //Treboles 2    C  2663
	public final static char ELEMENTOESPADA=6;  //Espadas 2     D  2660

	private int usocarta;
	public final static char USO_CEMENTERIO= 0;
	public final static char USO_MANO= 1;
	public final static char USO_BARRERA= 2;
	public final static char USO_FIGHT= 3;
	public final static char USO_DECK= 4;
	
	public Carta() {
		super();
		
	}
	
	public Carta(int pelemento,int pvalor,int pusocarta){
		valor=pvalor;
		elemento=pelemento;
		usocarta=pusocarta;
	}
	
	public Carta clone(){
		Carta clon=new Carta(this.elemento,this.valor,this.usocarta);
		return clon;
	}
	
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	public int getElemento() {
		return elemento;
	}
	public void setElemento(int elemento) {
		this.elemento = elemento;
	}
	
	public int getUsocarta() {
		return usocarta;
	}
	public void setUsocarta(int usocarta) {
		this.usocarta = usocarta;
	}

}
