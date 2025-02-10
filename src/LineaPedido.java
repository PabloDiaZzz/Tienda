import java.io.Serializable;

public class LineaPedido implements Serializable {
	private String idArticulo;
	private int unidades;

	public LineaPedido(String idArticulo, int unidades) {
		this.idArticulo = idArticulo;
		this.unidades = unidades;
	}

	public String getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(String idArticulo) {
		this.idArticulo = idArticulo;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	@Override
	public String toString() {
		return idArticulo + " (" + unidades + ") unidades";
	}
}
