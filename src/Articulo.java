import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Articulo implements Comparable<Articulo>, Serializable {

	static Comparator<Articulo> compId = Comparator.comparing((Articulo a) -> Integer.parseInt(a.getIdArticulo().split("-")[0])).thenComparing((Articulo a) -> Integer.parseInt(a.getIdArticulo().split("-")[1])).thenComparing(Articulo::getPvp);
	static Comparator<Articulo> compPvp = Comparator.comparing(Articulo::getPvp).thenComparing(compId);
	private String idArticulo;
	private String descripcion;
	private int existencias;
	private double pvp;

	public Articulo(String idArticulo, String descripcion, int existencias, double pvp) {
		this.idArticulo = idArticulo;
		this.descripcion = descripcion;
		this.existencias = existencias;
		this.pvp = pvp;
	}

	public String getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(String idArticulo) {
		this.idArticulo = idArticulo;
	}

	public String getDescripcion() {
		return Arrays.stream(descripcion.split(" ")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.joining(" "));
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	public double getPvp() {
		return pvp;
	}

	public void setPvp(double pvp) {
		this.pvp = pvp;
	}

	@Override
	public String toString() {
		return idArticulo + " - " + descripcion + " - " + existencias + " (" + pvp + "€)";
	}

	@Override
	public int compareTo(Articulo o) {
		return compId.compare(this, o);
	}
}
