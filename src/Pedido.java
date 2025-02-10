import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Pedido implements Comparable<Pedido> {

	static Comparator<Pedido> compId = Comparator.comparing(Pedido::getIdPedido).thenComparing(Pedido::getFechaPedido);
	private String idPedido;
	private Cliente clientePedido;
	private LocalDate fechaPedido;
	private ArrayList<LineaPedido> cestaCompra;

	public Pedido(String idPedido, Cliente clientePedido, LocalDate fechaPedido, ArrayList<LineaPedido> cestaCompra) {
		this.idPedido = idPedido;
		this.clientePedido = clientePedido;
		this.fechaPedido = fechaPedido;
		this.cestaCompra = cestaCompra;
	}

	public String getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(String idPedido) {
		this.idPedido = idPedido;
	}

	public Cliente getClientePedido() {
		return clientePedido;
	}

	public void setClientePedido(Cliente clientePedido) {
		this.clientePedido = clientePedido;
	}

	public LocalDate getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(LocalDate fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public ArrayList<LineaPedido> getLineaPedido() {
		return cestaCompra;
	}

	public void setLineaPedido(ArrayList<LineaPedido> cestaCompra) {
		this.cestaCompra = cestaCompra;
	}

	@Override
	public String toString() {
		return idPedido + " - " + clientePedido.getNombre() + " - " + cestaCompra;
	}

	@Override
	public int compareTo(Pedido o) {
		return compId.compare(this, o);
	}
}
