import java.io.Serializable;
import java.util.Comparator;

public class Cliente implements Comparable<Cliente>, Serializable {

	static Comparator<Cliente> compDni = Comparator.comparing(Cliente::getDni);
	private String dni;
	private String nombre;
	private String telefono;
	private String email;

	public Cliente(String dni, String nombre, String telefono, String email) {
		this.dni = dni;
		this.nombre = nombre;
		this.telefono = telefono;
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return dni + " - " + nombre + " " + telefono + " " + email;
	}

	@Override
	public int compareTo(Cliente o) {
		return compDni.compare(this, o);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Cliente) {
			return ((Cliente) o).getDni().equals(this.getDni()) || o == this;
		}
		return false;
	}
}
