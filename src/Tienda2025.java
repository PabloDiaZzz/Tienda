import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Tienda2025 implements Serializable {
	private final ArrayList<Pedido> pedidos;
	private final HashMap<String, Articulo> articulos;
	private final HashMap<String, Cliente> clientes;
	Scanner sc = new Scanner(System.in);

	public Tienda2025() {
		this.pedidos = new ArrayList<>();
		this.articulos = new HashMap<>();
		this.clientes = new HashMap<>();
	}

	public static void main(String[] args) {
		Tienda2025 tienda = new Tienda2025();
		tienda.cargaDatos();
		tienda.menu();
	}

	public static boolean validaArticulo(String id) {
		return id.matches("^[0-9]+-[0-9]+$");
	}

	public void menu() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"tienda", "Articulos", "Clientes", "Pedidos", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					menuArticulos();
					break;
				case 2:
					menuClientes();
					break;
				case 3:
					menuPedidos();
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void menuArticulos() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"articulos", "Crear Articulo", "Modificar Articulo", "Eliminar Articulo", "Lista Articulos", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					crearArticulo();
					break;
				case 2:
					modificarArticulo();
					break;
				case 3:
					eliminarArticulo();
					break;
				case 4:
					listArt();
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void menuPedidos() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"pedidos", "Crear pedido", "Modificar pedido", "Eliminar pedido", "Lista Pedidos", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					nuevoPedido();
					break;
				case 2:
					modificarPedido();
					break;
				case 3:
					eliminarPedido();
					break;
				case 4:
					listaPedidos();
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void menuClientes() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"clientes", "Crear cliente", "Modificar cliente", "Eliminar cliente", "Lista Clientes", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					crearCliente();
					break;
				case 2:
					modificarCliente();
					break;
				case 3:
					eliminarCliente();
					break;
				case 4:
					listClientes();
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void crearArticulo() {
		sc.nextLine();
		Articulo articulo = new Articulo("", "", 0, 0);
		System.out.print("Introduzca el ID >> ");
		String id = sc.nextLine();
		boolean valido = validaArticulo(id);
		while (!valido) {
			System.out.println("\nEl ID no es válido");
			System.out.print(">> ");
			id = sc.nextLine();
			if (id.isBlank()) {
				return;
			}
			valido = validaArticulo(id);
		}
		boolean dupe = buscaArticulo(id) != null;
		while (dupe) {
			System.out.println("\nEl ID ya esta en uso");
			System.out.print(">> ");
			id = sc.nextLine();
			dupe = buscaArticulo(id) != null;
		}
		articulo.setIdArticulo(id);
		System.out.println("\nIntroduzca la descripción >>");
		String desc = sc.nextLine();
		if (desc.isBlank()) {
			desc = "-";
		}
		articulo.setDescripcion(desc);
		System.out.print("Introduzca las existencias >> ");
		articulo.setExistencias(sc.nextInt());
		System.out.print("Introduzca el precio >> ");
		double pvp = sc.nextDouble();
		articulo.setPvp(pvp);
		articulos.put(articulo.getIdArticulo(), articulo);
	}

	public void modificarArticulo() {
		String id = solicitaId();
		boolean valido;
		boolean encontrado;
		Articulo mod = articulos.get(id);
		while (true) {
			System.out.println();
			String title = String.join(" ", Arrays.stream(mod.getDescripcion().split(" ")).map(String::trim).toArray(String[]::new));
			String[] opciones = new String[]{title, "Id", "Descripción", "Existencias", "Pvp", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					String idMod;
					sc.nextLine();
					valido = true;
					encontrado = false;
					do {
						System.out.print(!valido ? "\nEl ID no es valido\n" : "");
						System.out.print(encontrado ? "\nEl ID ya existe\n" : "");
						System.out.print("Introduzca el ID >> ");
						idMod = sc.nextLine();
						encontrado = false;
						valido = validaArticulo(idMod);
						if (valido) {
							encontrado = buscaArticulo(idMod) != null;
						}
						if (idMod.isBlank()) {
							break;
						}
					} while (!valido || encontrado);
					if (valido && !encontrado) {
						mod.setIdArticulo(idMod);
						articulos.put(idMod, mod);
						articulos.remove(id);
					}
					break;
				case 2:
					String descMod;
					sc.nextLine();
					System.out.println("Introduzca la descripción >> ");
					descMod = sc.nextLine();
					if (descMod.isBlank()) {
						break;
					}
					mod.setDescripcion(descMod);
					articulos.put(id, mod);
					break;
				case 3:
					int exsMod;
					sc.nextLine();
					System.out.print("Introduzca las existencias >> ");
					exsMod = sc.nextInt();
					mod.setExistencias(exsMod);
					articulos.put(id, mod);
					break;
				case 4:
					double pvpMod;
					sc.nextLine();
					System.out.print("Introduzca el PVP >> ");
					pvpMod = sc.nextDouble();
					mod.setPvp(pvpMod);
					articulos.put(id, mod);
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void eliminarArticulo() {
		articulos.remove(solicitaId());
	}

	public void crearCliente() {
		sc.nextLine();
		Cliente cliente = new Cliente("", "", "","");
		System.out.print("Introduzca el DNI >> ");
		String dni = sc.nextLine();
		if (dni.isBlank()) {
			return;
		}
		boolean valido = MetodosAux.validarDni(dni);
		while (!valido) {
			System.out.println("\nEl DNI no es valido");
			System.out.print(">> ");
			dni = sc.nextLine();
			if (dni.isBlank()) {
				return;
			}
			valido = MetodosAux.validarDni(dni);
		}
		boolean dupe = clientes.containsKey(dni);
		while (dupe) {
			System.out.print("\nEl DNI ya esta en uso");
			System.out.print(" >> ");
			dni = sc.nextLine();
			dupe = clientes.containsKey(dni);
		}
		cliente.setDni(dni);
		System.out.print("\nIntroduzca el Nombre >> ");
		String nom = sc.nextLine();
		if (nom.isBlank()) {
			nom = "-";
		}
		cliente.setNombre(nom);
		System.out.print("\nIntroduzca el Telefono >> ");
		String tlf = sc.nextLine();
		if (tlf.isBlank()) {
			tlf = "-";
		}
		cliente.setTelefono(tlf);
		System.out.print("\nIntroduzca el Email >> ");
		String mail = sc.nextLine();
		if (mail.isBlank()) {
			mail = "-";
		}
		cliente.setEmail(mail);
		clientes.put(dni, cliente);
	}

	public void modificarCliente() {
		listClientes();
		String dni = solicitaDni();
		boolean valido;
		boolean encontrado;
		Cliente mod = clientes.get(dni);
		while (true) {
			System.out.println();
			String title = String.join(" ", Arrays.stream(mod.getNombre().split(" ")).map(String::trim).filter(s -> !s.isEmpty()).toArray(String[]::new));
			String[] opciones = new String[]{title, "DNI", "Nombre", "Teléfono", "Email", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					String dniMod;
					sc.nextLine();
					valido = true;
					encontrado = false;
					do {
						System.out.print(!valido ? "\nEl DNI no es valido\n" : "");
						System.out.print(encontrado ? "\nEl DNI ya existe\n" : "");
						System.out.print("Introduzca el DNI >> ");
						dniMod = sc.nextLine();
						encontrado = false;
						valido = MetodosAux.validarDni(dniMod);
						if (valido) {
							encontrado = clientes.containsKey(dniMod);
						}
						if (dniMod.isBlank()) {
							break;
						}
					} while (!valido || encontrado);
					if (valido && !encontrado) {
						mod.setDni(dniMod);
						clientes.put(dniMod, mod);
						clientes.remove(dni);
						dni = dniMod;
					}
					break;
				case 2:
					String nombreMod;
					sc.nextLine();
					System.out.print("Introduzca el nombre >> ");
					nombreMod = sc.nextLine();
					if (nombreMod.isBlank()) {
						break;
					}
					mod.setNombre(nombreMod);
					clientes.put(dni, mod);
					break;
				case 3:
					String telefonoMod;
					sc.nextLine();
					System.out.print("Introduzca el teléfono >> ");
					telefonoMod = sc.nextLine();
					if (telefonoMod.isBlank()) {
						break;
					}
					mod.setTelefono(telefonoMod);
					clientes.put(dni, mod);
					break;
				case 4:
					String emailMod;
					sc.nextLine();
					System.out.print("Introduzca el email >> ");
					emailMod = sc.nextLine();
					if (emailMod.isBlank()) {
						break;
					}
					mod.setEmail(emailMod);
					clientes.put(dni, mod);
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void eliminarCliente() {
		clientes.remove(solicitaDni());
	}

	public String solicitaId() {
		listArt();
		String id;
		boolean valido = true;
		boolean encontrado = true;
		sc.nextLine();
		do {
			System.out.print(!valido ? "\nEl ID no es valido\n" : "");
			System.out.print(!encontrado ? "\nEl ID no existe\n" : "");
			System.out.print("Introduzca el ID >> ");
			id = sc.nextLine();
			encontrado = true;
			valido = validaArticulo(id);
			if (valido) {
				encontrado = buscaArticulo(id) != null;
			}
			if (id.isBlank()) {
				return null;
			}
		} while (!valido || !encontrado);
		return id;
	}

	public String solicitaDni() {
		String dni;
		boolean valido = true;
		boolean encontrado = true;
		sc.nextLine();
		do {
			System.out.print(!valido ? "\nEl DNI no es valido\n" : "");
			System.out.print(!encontrado ? "\nEl DNI no existe\n" : "");
			System.out.print("Introduzca el DNI >> ");
			dni = sc.nextLine();
			encontrado = true;
			valido = MetodosAux.validarDni(dni);
			if (valido) {
				encontrado = clientes.containsKey(dni);
			}
			if (dni.isBlank()) {
				return null;
			}
		} while (!valido || !encontrado);
		return dni;
	}

	public void listArt() {
		ArrayList<Articulo> values = new ArrayList<>(articulos.values());
		Collections.sort(values);
		values.forEach(System.out::println);
	}

	public void modificarPedido() {
		Pedido pMod = null;
		while (true) {
			String dni;
			int year, id;
			int contador = 0;
			dni = solicitaDni();
			if (dni == null) {
				return;
			}
			System.out.print("Introduzca el año >> ");
			year = sc.nextInt();
			System.out.println();
			for (Pedido p : pedidos) {
				if (p.getClientePedido().getDni().equalsIgnoreCase(dni) && p.getFechaPedido().getYear() == year) {
					id = Integer.parseInt(p.getIdPedido().split("[-/]")[1]);
					System.out.println(String.format("%03d", id) + " - " + p.getLineaPedido());
					contador++;
				}
			}
			if (contador == 0) {
				System.out.println("No hay pedidos para ese cliente en ese año");
			} else {
				contador = 0;
				System.out.print("Introduzca el ID >> ");
				id = sc.nextInt();
				for (Pedido p : pedidos) {
					if (p.getIdPedido().equalsIgnoreCase(dni + "-" + String.format("%03d", id) + "/" + year)) {
						pMod = p;
						pedidos.remove(p);
						contador++;
						break;
					}
				}
				if (contador == 0) {
					System.out.println("El pedido no existe");
				} else {
					break;
				}
			}
		}
		while (true) {
			System.out.println();
			String[] opciones = new String[]{pMod.getIdPedido(),"Añadir articulo","Modificar articulo","Eliminar articulo","Listado articulos","Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					String opc, idT, pedidasS = "";
					int pedidas;
					ArrayList<LineaPedido> CestaCompraAux = pMod.getLineaPedido();
					System.out.println("INTRODUZCA LOS ARTÍCULOS QUE DESEA PEDIR UNO A UNO");
					sc.nextLine();
					do {
						System.out.print("ID ARTÍCULO (00 para terminar)");
						System.out.print(" >> ");
						idT = sc.nextLine().toUpperCase();
						if (idT.equals("00")) {
							break;
						}
						String finalIdT = idT;
						if (!idT.isBlank() && articulos.containsKey(idT) && CestaCompraAux.stream().noneMatch(l -> l.getIdArticulo().equals(finalIdT))) {
							System.out.println("(" + articulos.get(idT).getDescripcion() + ") - UNIDADES?");
							//Entrada de un int sobre un String - metodo esInt
							do {
								System.out.print(!MetodosAux.esInt(pedidasS) && !pedidasS.isBlank() ? "Introduzca un número\n" : "");
								pedidasS = sc.nextLine();
							} while (!MetodosAux.esInt(pedidasS));
							//Conversión de String a int
							pedidas = Integer.parseInt(pedidasS);
							try {
								stock(pedidas, idT);
								CestaCompraAux.add(new LineaPedido(idT, pedidas));
							} catch (StockAgotado | StockInsuficiente ex) {
								System.out.println(ex.getMessage());
								int disponibles = articulos.get(idT).getExistencias();
								if (ex instanceof StockInsuficiente) {
									System.out.println("¿DESEA PEDIR LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N)");
									opc = sc.nextLine();
									if (opc.equalsIgnoreCase("S")) {
										CestaCompraAux.add(new LineaPedido(idT, disponibles));
									}
								}
							}
						} else if (CestaCompraAux.stream().anyMatch(l -> l.getIdArticulo().equals(finalIdT))) {
							System.out.println("\nNo se puede pedir dos veces el mismo artículos\n");
						}
					} while (true);
					if (!CestaCompraAux.isEmpty()) {
						pMod.setLineaPedido(CestaCompraAux);
					}
					break;
				case 2:
					while (true) {
						ArrayList<String> opcionesArts = new ArrayList<>();
						opcionesArts.add(pMod.getIdPedido());
						for (LineaPedido l : pMod.getLineaPedido()) {
							opcionesArts.add(l.getIdArticulo() + " - " + String.join(" ", Arrays.stream(articulos.get(l.getIdArticulo()).getDescripcion().split(" ")).map(String::trim).toArray(String[]::new)) + " (" + l.getUnidades() + ")");
						}
						opcionesArts.add("Salir");
						opciones = opcionesArts.toArray(new String[0]);
						System.out.println();
						MetodosAux.menu(opciones);
						n = opciones.length - 1;
						try {
							option = sc.nextInt();
						} catch (InputMismatchException ignored) {
							sc.nextLine();
							continue;
						}
						if (option == n) {
							option = 0;
							sc.nextLine();
							break;
						}
						Articulo art;
						try {
							art = articulos.get(pMod.getLineaPedido().get(option - 1).getIdArticulo());
						} catch (IndexOutOfBoundsException ignored) {
							continue;
						}
						System.out.println("\nIntroduzca las unidades >> ");
						int unidades = sc.nextInt();
						try {
							stock(unidades, art.getIdArticulo());
							pMod.getLineaPedido().get(option - 1).setUnidades(unidades);
						} catch (StockAgotado | StockInsuficiente ex) {
							System.out.println(ex.getMessage());
							int disponibles = articulos.get(art.getIdArticulo()).getExistencias();
							System.out.println("¿DESEA PEDIR LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N)");
							String yn = sc.nextLine();
							if (yn.equalsIgnoreCase("S")) {
								pMod.getLineaPedido().get(option - 1).setUnidades(disponibles);
						}}
					}
					break;
				case 3:
					while (true) {
						ArrayList<String> opcionesArts = new ArrayList<>();
						opcionesArts.add(pMod.getIdPedido());
						for (LineaPedido l : pMod.getLineaPedido()) {
							opcionesArts.add(l.getIdArticulo() + " - " + String.join(" ", Arrays.stream(articulos.get(l.getIdArticulo()).getDescripcion().split(" ")).map(String::trim).filter(v -> !v.isEmpty()).toArray(String[]::new)) + " (" + l.getUnidades() + ")");
						}
						opcionesArts.add("Salir");
						opciones = opcionesArts.toArray(new String[0]);
						System.out.println();
						MetodosAux.menu(opciones);
						n = opciones.length - 1;
						try {
							option = sc.nextInt();
						} catch (InputMismatchException ignored) {
							sc.nextLine();
							continue;
						}
						if (option == n) {
							option = 0;
							sc.nextLine();
							break;
						}
						pMod.getLineaPedido().remove(option - 1);
					}
					break;
				case 4:
					for (LineaPedido lp : pMod.getLineaPedido()) {
						System.out.println(lp.getIdArticulo() + " - "+ String.join(" ", Arrays.stream(articulos.get(lp.getIdArticulo()).getDescripcion().split(" ")).map(String::trim).filter(v -> !v.isEmpty()).toArray(String[]::new)) + " (" + lp.getUnidades() + ")");
					}
					break;
			}
			if (option == n) {
				break;
			}
		}
		pedidos.add(pMod);
	}

	public void eliminarPedido() {
		while (true) {
			String dni;
			int year, id;
			int contador = 0;
			dni = solicitaDni();
			if (dni == null) {
				return;
			}
			System.out.print("Introduzca el año >> ");
			year = sc.nextInt();
			for (Pedido p : pedidos) {
				if (p.getClientePedido().getDni().equalsIgnoreCase(dni) && p.getFechaPedido().getYear() == year) {
					id = Integer.parseInt(p.getIdPedido().split("[-/]")[1]);
					System.out.println(String.format("%03d", id) + " - " + p.getLineaPedido());
					contador++;
				}
			}
			if (contador == 0) {
				System.out.println("No hay pedidos para ese cliente en ese año");
			} else {
				System.out.print("Introduzca el ID >> ");
				id = sc.nextInt();
				for (Pedido p : pedidos) {
					if (p.getIdPedido().equalsIgnoreCase(dni + "-" + String.format("%03d", id) + "/" + year)) {
						pedidos.remove(p);
						return;
					}
				}
				System.out.println("El pedido no existe");
			}
		}
	}

	public void listClientes() {
		ArrayList<Cliente> values = new ArrayList<>(clientes.values());
		Collections.sort(values);
		values.forEach(System.out::println);
	}

	public void listaPedidos() {
		Collections.sort(pedidos);
		pedidos.forEach(System.out::println);
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public HashMap<String, Articulo> getArticulos() {
		return articulos;
	}

	public HashMap<String, Cliente> getClientes() {
		return clientes;
	}

	public void stock(int unidadesPed, String id) throws StockAgotado, StockInsuficiente {
		int n = articulos.get(id).getExistencias();
		if (n == 0) {
			throw new StockAgotado("Stock AGOTADO para el artículo " + articulos.get(id).getDescripcion());
		} else if (n < unidadesPed) {

			throw new StockInsuficiente("Stock INSUFICIENTE para el artículo " + articulos.get(id).getDescripcion() + " (sólo quedan " + n + " unidades)");
		}
	}

	public String generaIdPedido(String idCliente) {
		int contador = 0;
		String nuevoId;
		for (Pedido p : pedidos) {
			if (p.getClientePedido().getDni().equalsIgnoreCase(idCliente)) {
				contador++;
			}
		}
		contador++;
		nuevoId = idCliente + "-" + String.format("%03d", contador) + "/" + LocalDate.now().getYear();
		return nuevoId;
	}

	public void nuevoPedido() {
		ArrayList<LineaPedido> CestaCompraAux = new ArrayList<>();
		String dniT, idT, opc, pedidasS = "";
		int pedidas;
		sc.nextLine();
		do {
			System.out.print("CLIENTE PEDIDO (DNI) >> ");
			dniT = sc.nextLine().toUpperCase();
			if (dniT.isBlank())
				break;
			if (!MetodosAux.validarDni(dniT)) {
				System.out.println("DNI NO VÁLIDO");
			}
			if (!clientes.containsKey(dniT) && MetodosAux.validarDni(dniT)) {
				System.out.println("DNI NO ENCONTRADO");
			}
		} while (!clientes.containsKey(dniT));
		if (!dniT.isBlank()) {
			System.out.println("INTRODUZCA LOS ARTÍCULOS QUE DESEA PEDIR UNO A UNO");
			do {
				System.out.println("INTRODUZCA EL CÓDIGO DEL ARTÍCULO (00 para terminar)");
				System.out.print(">> ");
				idT = sc.nextLine().toUpperCase();
				if (idT.equals("00")) {
					break;
				}
				if (!idT.isBlank() && articulos.containsKey(idT)) {
					System.out.println("(" + articulos.get(idT).getDescripcion() + ") - UNIDADES?");
					//Entrada de un int sobre un String - metodo esInt
					do {
						System.out.print(!MetodosAux.esInt(pedidasS) && !pedidasS.isBlank() ? "Introduzca un número\n" : "");
						pedidasS = sc.nextLine();
					} while (!MetodosAux.esInt(pedidasS));
					//Conversión de String a int
					pedidas = Integer.parseInt(pedidasS);
					try {
						stock(pedidas, idT);
						CestaCompraAux.add(new LineaPedido(idT, pedidas));
					} catch (StockAgotado | StockInsuficiente ex) {
						System.out.println(ex.getMessage());
						int disponibles = articulos.get(idT).getExistencias();
						if (ex instanceof StockInsuficiente) {
							System.out.println("¿DESEA PEDIR LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N)");
							opc = sc.nextLine();
							if (opc.equalsIgnoreCase("S")) {
								CestaCompraAux.add(new LineaPedido(idT, disponibles));
							}
						}
					}
				}
			} while (true);
			if (!CestaCompraAux.isEmpty()) {
				pedidos.add(new Pedido(generaIdPedido(dniT), clientes.get(dniT), LocalDate.now(), CestaCompraAux));
			}
		}
	}

	public Articulo buscaArticulo(String id) {
		return articulos.get(id);
	}

	public void cargaDatos() {

		clientes.put("80580845T", new Cliente("80580845T", "ANA ", "658111111", "ana@gmail.com"));
		clientes.put("36347775R", new Cliente("36347775R", "LOLA", "649222222", "lola@gmail.com"));
		clientes.put("63921307Y", new Cliente("63921307Y", "JUAN", "652333333", "juan@gmail.com"));
		clientes.put("02337565Y", new Cliente("02337565Y", "EDU", "634567890", "edu@gmail.com"));

		articulos.put("1-11", new Articulo("1-11", "RATON LOGITECH ST ", 14, 15));
		articulos.put("1-22", new Articulo("1-22", "TECLADO STANDARD  ", 9, 18));
		articulos.put("2-11", new Articulo("2-11", "HDD SEAGATE 1 TB  ", 16, 80));
		articulos.put("2-22", new Articulo("2-22", "SSD KINGSTOM 256GB", 9, 70));
		articulos.put("2-33", new Articulo("2-33", "SSD KINGSTOM 512GB", 0, 200));
		articulos.put("3-22", new Articulo("3-22", "EPSON PRINT XP300 ", 5, 80));
		articulos.put("4-11", new Articulo("4-11", "ASUS  MONITOR  22 ", 5, 100));
		articulos.put("4-22", new Articulo("4-22", "HP MONITOR LED 28 ", 5, 180));
		articulos.put("4-33", new Articulo("4-33", "SAMSUNG ODISSEY G5", 12, 580));

		LocalDate hoy = LocalDate.now();
		pedidos.add(new Pedido("80580845T-001/2025", clientes.get("80580845T"), hoy.minusDays(1), new ArrayList<>(List.of(new LineaPedido("1-11", 3), new LineaPedido("4-22", 3)))));
		pedidos.add(new Pedido("80580845T-002/2025", clientes.get("80580845T"), hoy.minusDays(2), new ArrayList<>(List.of(new LineaPedido("4-11", 3), new LineaPedido("4-22", 2), new LineaPedido("4-33", 4)))));
		pedidos.add(new Pedido("36347775R-001/2025", clientes.get("36347775R"), hoy.minusDays(3), new ArrayList<>(List.of(new LineaPedido("4-22", 1), new LineaPedido("2-22", 3)))));
		pedidos.add(new Pedido("36347775R-002/2025", clientes.get("36347775R"), hoy.minusDays(5), new ArrayList<>(List.of(new LineaPedido("4-33", 3), new LineaPedido("2-11", 3)))));
		pedidos.add(new Pedido("63921307Y-001/2025", clientes.get("63921307Y"), hoy.minusDays(4), new ArrayList<>(List.of(new LineaPedido("2-11", 5), new LineaPedido("2-33", 3), new LineaPedido("4-33", 2)))));
	}
}
