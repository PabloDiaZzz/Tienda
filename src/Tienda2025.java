// NECESARIO AL MENOS EL METODO MetodosAux.java

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Tienda2025 implements Serializable {
	static Tienda2025 tienda;
	static Scanner sc = new Scanner(System.in);
	private ArrayList<Pedido> pedidos;
	private HashMap<String, Articulo> articulos;
	private HashMap<String, Cliente> clientes;

	public Tienda2025() {
		this.pedidos = new ArrayList<>();
		this.articulos = new HashMap<>();
		this.clientes = new HashMap<>();
	}

	static void main(String[] args) {
		Tienda2025 tienda = new Tienda2025();
		boolean cargado = tienda.importarTienda();
		if (!cargado) {
			System.out.println("[Los datos no se han podido cargar]");
			System.out.print("Desea cargar el modelo (S/N) >> ");
			String opc = sc.nextLine();
			if (opc.equalsIgnoreCase("s")) {
				tienda.cargaDatos();
			}
		}
		tienda.menu();
	}

	public static boolean validaArticulo(String id) {
		return id.matches("^[0-9]+-[0-9]+$");
	}

	public void menu() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"tienda", "Articulos", "Clientes", "Pedidos", "Copia de Seguridad", "Salir"};
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
				case 4:
					menuCopia();
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
			String[] opciones = new String[]{"articulos", "Crear Articulo", "Modificar Articulo", "Eliminar Articulo", "Lista Articulos", "Unidades vendidas", "Historial de Articulo", "Articulos por Categoria", "Salir"};
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
				case 5:
					udsVendidas();
					break;
				case 6:
					historialArticulo();
					break;
				case 7:
					artCategoria();
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
			String[] opciones = new String[]{"clientes", "Crear cliente", "Modificar cliente", "Eliminar cliente", "Lista Clientes", "Gasto por Cliente", "Salir"};
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
				case 5:
					gastoClientes();
					break;
			}
			if (option == n) {
				return;
			}
		}
	}

	public void menuCopia() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"copia", "Copia de Seguridad", "Guardar Pedidos", "Buscar Pedidos", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					guardarTienda();
					break;
				case 2:
					guardarPedidos();
					break;
				case 3:
					buscarPedidos();
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
			String title = String.join(
					" ",
					Arrays.stream(mod.getDescripcion().split(" "))
							.filter(s -> !s.isEmpty())
							.map(String::trim)
							.toArray(String[]::new));
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

	public void udsVendidas() {
		System.out.println();
		System.out.println("Unidades vendidas por Articulo:");
		articulos.values()
				.stream()
				.sorted()
				.peek(a -> System.out.print(a.getIdArticulo() + " - " + a.getDescripcion() + " - Uds: "))
				.map(a -> pedidos.stream()
						.flatMap(p -> p.getCestaCompra().stream())
						.filter(lp -> lp.getIdArticulo().equals(a.getIdArticulo()))
						.mapToInt(LineaPedido::getUnidades)
						.sum())
				.forEach(System.out::println);
	}

	public void historialArticulo() {
		String id = solicitaId();
		Articulo art = buscaArticulo(id);
		System.out.println();
		if (art != null) {
			clientes.values()
					.forEach(c -> System.out.println(c.getNombre() + " - " + pedidos.stream()
							.filter(p -> p.getClientePedido().equals(c))
							.flatMap(p -> p.getCestaCompra().stream())
							.filter(lp -> lp.getIdArticulo().equals(id))
							.mapToInt(LineaPedido::getUnidades)
							.sum()));
		}
	}

	public void artCategoria() {
		while (true) {
			System.out.println();
			String[] opciones = new String[]{"categorias", "Perifericos", "Almacenamiento", "Impresoras", "Monitores", "Todos", "Nuevo metodo", "Salir"};
			int n = opciones.length - 1;
			MetodosAux.menu(opciones);
			int opc = sc.nextInt();
			System.out.println();
			if (opc == n - 2) {
				listArt();
			} else if (opc == n - 1) {
				System.out.println(articulosXCat());
			} else {
				System.out.println(opciones[opc].toUpperCase());
				articulos.values()
						.stream()
						.sorted()
						.filter(art -> art.getIdArticulo()
								.replaceAll("(\\d+)-(\\d+)", "$1")
								.equals(String.valueOf(opc)))
						.forEach(System.out::println);
			}
			if (opc == n) {
				return;
			}
		}
	}

	public void crearCliente() {
		sc.nextLine();
		Cliente cliente = new Cliente("", "", "", "");
		System.out.print("Introduzca el DNI >> ");
		String dni = sc.nextLine().toUpperCase();
		if (dni.isBlank()) {
			return;
		}
		boolean valido = MetodosAux.validarDni(dni);
		while (!valido) {
			System.out.println("\nEl DNI no es valido");
			System.out.print(">> ");
			dni = sc.nextLine().toUpperCase();
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
			String title = String.join(
					" ",
					Arrays.stream(mod.getNombre().split(" "))
							.map(String::trim)
							.filter(s -> !s.isEmpty())
							.toArray(String[]::new));
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
						dniMod = sc.nextLine().toUpperCase();
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

	public void gastoClientes() {
		System.out.println("Gasto por clientes: ");
		clientes.values().forEach(c -> System.out.println(c.getNombre() + " - " + totalCliente(c)));
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
			dni = sc.nextLine().toUpperCase();
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
					System.out.println(String.format("%03d", id) + " - " + p.getCestaCompra());
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
					System.out.println();
				} else {
					break;
				}
			}
		}
		while (true) {
			System.out.println();
			String[] opciones = new String[]{pMod.getIdPedido(), "Añadir articulo", "Modificar articulo", "Eliminar articulo", "Listado articulos", "Modificar fecha", "Salir"};
			MetodosAux.menu(opciones);
			int n = opciones.length - 1;
			int option = sc.nextInt();
			switch (option) {
				case 1:
					String opc, idT, pedidasS = "";
					int pedidas;
					ArrayList<LineaPedido> CestaCompraAux = pMod.getCestaCompra();
					System.out.println("Introduzca los IDs uno a uno");
					sc.nextLine();
					do {
						System.out.println("ID del articulo (00 para terminar)");
						System.out.print(">> ");
						idT = sc.nextLine().toUpperCase();
						if (idT.equals("00")) {
							break;
						}
						String finalIdT = idT;
						if (!idT.isBlank() && articulos.containsKey(idT) && CestaCompraAux.stream()
								.noneMatch(l -> l.getIdArticulo().equals(finalIdT))) {
							System.out.print(articulos.get(idT).getDescripcion() + "\nIntroduzca las Unidades >> ");
							//Entrada de un int sobre un String - metodo esInt
							do {
								System.out.print(!MetodosAux.esInt(pedidasS) && !pedidasS.isBlank() ? "Introduzca un número\n" : "");
								pedidasS = sc.nextLine();
							} while (!MetodosAux.esInt(pedidasS));
							//Conversión de String a int
							pedidas = Integer.parseInt(pedidasS);
							try {
								stock(idT, pedidas);
								CestaCompraAux.add(new LineaPedido(idT, pedidas));
								articulos.get(idT).setExistencias(articulos.get(idT).getExistencias() - pedidas);
							} catch (StockAgotado | StockInsuficiente ex) {
								System.out.println(ex.getMessage());
								int disponibles = articulos.get(idT).getExistencias();
								if (ex instanceof StockInsuficiente) {
									System.out.println("¿Desea pedir las " + disponibles + " unidades disponibles? (S/N)");
									opc = sc.nextLine();
									if (opc.equalsIgnoreCase("S")) {
										CestaCompraAux.add(new LineaPedido(idT, disponibles));
										articulos.get(idT).setExistencias(0);
									}
								}
							}
						} else if (CestaCompraAux.stream().anyMatch(l -> l.getIdArticulo().equals(finalIdT))) {
							System.out.println("\nNo se puede pedir dos veces el mismo artículos\n");
						}
					} while (true);
					if (!CestaCompraAux.isEmpty()) {
						pMod.setCestaCompra(CestaCompraAux);
					}
					break;
				case 2:
					while (true) {
						ArrayList<String> opcionesArts = new ArrayList<>();
						opcionesArts.add(pMod.getIdPedido());
						for (LineaPedido l : pMod.getCestaCompra()) {
							opcionesArts.add(l.getIdArticulo() + " - " + String.join(
									" ",
									Arrays.stream(articulos.get(l.getIdArticulo()).getDescripcion().split(" "))
											.map(String::trim)
											.toArray(String[]::new)) + " (" + l.getUnidades() + ")");
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
							art = articulos.get(pMod.getCestaCompra().get(option - 1).getIdArticulo());
						} catch (IndexOutOfBoundsException ignored) {
							continue;
						}
						System.out.println("\nIntroduzca las unidades >> ");
						int unidades = sc.nextInt();
						try {
							stock(art.getIdArticulo(), unidades);
							articulos.get(art.getIdArticulo())
									.setExistencias(articulos.get(art.getIdArticulo())
											                .getExistencias() + pMod.getCestaCompra()
											.get(option - 1)
											.getUnidades());
							pMod.getCestaCompra().get(option - 1).setUnidades(unidades);
							articulos.get(art.getIdArticulo())
									.setExistencias(articulos.get(art.getIdArticulo()).getExistencias() - unidades);
						} catch (StockAgotado | StockInsuficiente ex) {
							System.out.println(ex.getMessage());
							int disponibles = articulos.get(art.getIdArticulo()).getExistencias();
							System.out.println("¿DESEA PEDIR LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N)");
							sc.nextLine();
							String yn = sc.nextLine();
							if (yn.equalsIgnoreCase("S")) {
								pMod.getCestaCompra().get(option - 1).setUnidades(disponibles);
								articulos.get(art.getIdArticulo()).setExistencias(0);
							}
						}
					}
					break;
				case 3:
					while (true) {
						ArrayList<String> opcionesArts = new ArrayList<>();
						opcionesArts.add(pMod.getIdPedido());
						for (LineaPedido l : pMod.getCestaCompra()) {
							opcionesArts.add(l.getIdArticulo() + " - " + String.join(
									" ",
									Arrays.stream(articulos.get(l.getIdArticulo()).getDescripcion().split(" "))
											.map(String::trim)
											.filter(v -> !v.isEmpty())
											.toArray(String[]::new)) + " (" + l.getUnidades() + ")");
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
						articulos.get(pMod.getCestaCompra().get(option - 1).getIdArticulo())
								.setExistencias(articulos.get(pMod.getCestaCompra().get(option - 1).getIdArticulo())
										                .getExistencias() + pMod.getCestaCompra()
										.get(option - 1)
										.getUnidades());
						pMod.getCestaCompra().remove(option - 1);
					}
					break;
				case 4:
					for (LineaPedido lp : pMod.getCestaCompra()) {
						System.out.println(lp.getIdArticulo() + " - " + Arrays.stream(articulos.get(lp.getIdArticulo())
								                                                              .getDescripcion()
								                                                              .split(" "))
								.map(String::trim)
								.filter(v -> !v.isEmpty())
								.reduce((String a, String b) -> a + " " + b) + " (" + lp.getUnidades() + ")");
					}
					break;
				case 5:
					int d, m, y;
					System.out.print("Introduzca el dia >> ");
					d = sc.nextInt();
					System.out.print("Introduzca el mes >> ");
					m = sc.nextInt();
					System.out.print("Introduzca el año >> ");
					y = sc.nextInt();
					sc.nextLine();
					pMod.setFechaPedido(LocalDate.of(y, m, d));
					String id = pMod.getIdPedido();
					pMod.setIdPedido(id.split("[-/]")[0] + "-" + id.split("[-/]")[1] + "/" + y);
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
					System.out.println(String.format("%03d", id) + " - " + p.getCestaCompra());
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
						for (LineaPedido l : p.getCestaCompra()) {
							articulos.get(l.getIdArticulo())
									.setExistencias(articulos.get(l.getIdArticulo())
											                .getExistencias() + l.getUnidades());
						}
						pedidos.remove(p);
						return;
					}
				}
				System.out.println("El pedido no existe");
			}
		}
	}

	public HashMap<String, Long> articulosXCat() {
		//		HashMap<String, Long> res = articulos.keySet().stream().map(a -> a.replaceAll("(\\d+)-(\\d+)", "$1")).collect(Collectors.groupingBy(a -> a, Collectors.counting())).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v1 , HashMap::new));

		HashMap<String, Long> res = new HashMap<>();
		for (Articulo a : articulos.values()) {
			String cat = a.getIdArticulo().replaceAll("(\\d+)-(\\d+)", "$1"); // No uso charAt por si hay categorias de 2 digitos
			res.put(cat, res.getOrDefault(cat, 0L) + 1);
		}
		return res;
	}

	public void listClientes() {
		articulos.values().stream().sorted().forEach(System.out::println);
	}

	public void listaPedidos() {
		while (true) {
			System.out.println();
			String[] options = new String[]{"Ordenar pedidos", "Identificador", "Fecha", "Precio total mínimo", "Salir"};
			MetodosAux.menu(options);
			int option = sc.nextInt();
			switch (option) {
				case 1:
					System.out.println();
					pedidos.stream().sorted().forEach(System.out::println);
					System.out.println();
					break;
				case 2:
					System.out.println();
					pedidos.stream().sorted(Pedido.compFecha).forEach(System.out::println);
					System.out.println();
					break;
				case 3:
					System.out.println();
					System.out.print("Introduzca el valor >> ");
					double v = sc.nextDouble();
					pedidos.stream()
							.distinct()
							.filter(p -> totalPedido(p) > v)
							.sorted(Comparator.comparing(this::totalPedido))
							.forEachOrdered(System.out::println);
					System.out.println();
					break;
			}
			if (option == options.length - 1) {
				break;
			}
		}
	}

	public double totalPedido(Pedido p) {
		return p.getCestaCompra()
				.stream()
				.mapToDouble(lp -> lp.getUnidades() * articulos.get(lp.getIdArticulo()).getPvp())
				.sum();
	}

	public double totalCliente(Cliente c) {
		return pedidos.stream().filter(p -> p.getClientePedido().equals(c)).mapToDouble(this::totalPedido).sum();
	}

	public double totalCliente2(Cliente c) {
		return pedidos.stream()
				.filter(p -> p.getClientePedido().equals(c))
				.mapToDouble(p -> p.getCestaCompra()
						.stream()
						.mapToDouble(lp -> lp.getUnidades() * articulos.get(lp.getIdArticulo()).getPvp())
						.sum())
				.sum();
	}

	public void stock(String id, int unidadesPed) throws StockAgotado, StockInsuficiente {
		int n = articulos.get(id).getExistencias();
		if (n == 0) {
			throw new StockAgotado("Stock AGOTADO para el artículo " + articulos.get(id).getDescripcion());
		} else if (n < unidadesPed) {
			throw new StockInsuficiente("Stock INSUFICIENTE para el artículo " + articulos.get(id)
					.getDescripcion() + " (sólo quedan " + n + " unidades)");
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

	// Al añadir un articulo al pedido las unidades se restan al stock para evitar sobrepasarlo mediante varios pedidos
	public void nuevoPedido() {
		ArrayList<LineaPedido> CestaCompraAux = new ArrayList<>();
		String dniT, idT, opc, pedidasS = "";
		int pedidas;
		do {
			dniT = solicitaDni();
			if (dniT == null) {
				break;
			}
		} while (!clientes.containsKey(dniT));
		if (dniT != null) {
			System.out.println("Introduzca los IDs uno a uno");
			do {
				System.out.println("ID del articulo (00 para terminar)");
				System.out.print(">> ");
				idT = sc.nextLine().toUpperCase();
				if (idT.equals("00")) {
					break;
				}
				if (!idT.isBlank() && articulos.containsKey(idT)) {
					System.out.print(articulos.get(idT).getDescripcion() + "\nIntroduzca las Unidades >> ");
					//Entrada de un int sobre un String - metodo esInt
					do {
						System.out.print(!MetodosAux.esInt(pedidasS) && !pedidasS.isBlank() ? "Introduzca un número\n" : "");
						pedidasS = sc.nextLine();
					} while (!MetodosAux.esInt(pedidasS));
					//Conversión de String a int
					pedidas = Integer.parseInt(pedidasS);
					try {
						stock(idT, pedidas);
						CestaCompraAux.add(new LineaPedido(idT, pedidas));
						articulos.get(idT).setExistencias(articulos.get(idT).getExistencias() - pedidas);
					} catch (StockAgotado | StockInsuficiente ex) {
						System.out.println(ex.getMessage());
						int disponibles = articulos.get(idT).getExistencias();
						if (ex instanceof StockInsuficiente) {
							System.out.println("¿Desea pedir las " + disponibles + " unidades disponibles? (S/N)");
							opc = sc.nextLine();
							if (opc.equalsIgnoreCase("S")) {
								CestaCompraAux.add(new LineaPedido(idT, disponibles));
								articulos.get(idT).setExistencias(0);
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

	public void guardarTienda() {
		String[] campos = new String[]{"articulos", "pedidos", "clientes"};
		for (String c : campos) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(c + ".dat"))) {
				switch (c) {
					case "articulos":
						oos.writeObject(articulos);
						break;
					case "pedidos":
						oos.writeObject(pedidos);
						break;
					case "clientes":
						oos.writeObject(clientes);
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + c);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("[Datos guardados con exito]");
	}

	public boolean importarTienda() {
		int contador = 0;
		String[] campos = new String[]{"articulos", "pedidos", "clientes"};
		for (String c : campos) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(c + ".dat"))) {
				switch (c) {
					case "articulos":
						articulos = (HashMap<String, Articulo>) ois.readObject();
						break;
					case "pedidos":
						pedidos = (ArrayList<Pedido>) ois.readObject();
						break;
					case "clientes":
						clientes = (HashMap<String, Cliente>) ois.readObject();
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + c);
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println(e.getMessage());
				contador++;
			}
		}
		if (contador != campos.length) {
			System.out.println("[Datos cargados con exito]");
			return true;
		}
		return false;
	}

	public void guardarPedidos() {
		clientes.values()
				.stream()
				.map(c -> new ArrayList<>(List.of(
						c.getNombre().toUpperCase(),
						pedidos.stream()
								.filter(p -> p.getClientePedido().equals(c))
								.toArray(Pedido[]::new))))
				.filter(l -> ((Pedido[]) l.getLast()).length > 0)
				.forEach(fL -> {
					try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pedidoCliente_" + fL.getFirst() + ".dat"))) {
						oos.writeObject(fL.getLast());
						System.out.println("ARCHIVOS CREADOS CORRECTAMENTE");
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				});
	}

	public void buscarPedidos() {
		try {
			String dni;
			boolean valido = true;
			boolean encontrado = true;
			sc.nextLine();
			do {
				System.out.print(!valido ? "\nEl DNI no es valido\n" : "");
				System.out.print(!encontrado ? "\nEl DNI no existe\n" : "");
				System.out.println("DNI CLIENTE PARA VER PEDIDOS ALMACENADOS: ");
				dni = sc.nextLine();
				encontrado = true;
				valido = MetodosAux.validarDni(dni);
				if (valido) {
					encontrado = clientes.containsKey(dni);
				}
				if (dni.isBlank()) {
					dni = null;
				}
			} while (!valido || !encontrado);
			if (dni != null) {
				String nombre = clientes.get(dni).getNombre().toUpperCase();
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pedidoCliente_" + nombre + ".dat"));
				ArrayList<Pedido> p = new ArrayList<>(List.of(((Pedido[]) ois.readObject())));
				for (Pedido pedido : p) {
					System.out.println();
					System.out.println("PEDIDO: " + pedido.getIdPedido() + " DE: " + nombre.toUpperCase());
					pedido.getCestaCompra()
							.forEach(lp -> System.out.println(buscaArticulo(lp.getIdArticulo()).getDescripcion() + "\t\tUNIDADES: " + lp.getUnidades()));
				}
				System.out.println("FIN ARCHIVO");
			}
		} catch (FileNotFoundException e) {
			System.out.println("EL CLIENTE NO TIENE PEDIDOS GUARDADOS");
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public HashMap<String, Cliente> getClientes() {
		return clientes;
	}

	public HashMap<String, Articulo> getArticulos() {
		return articulos;
	}

	public ArrayList<Pedido> getPedidos() {
		return pedidos;
	}

	public void cargaDatos(){
		clientes.put("90015161S",new Cliente("90015161S","ANA ","658111111","ana@gmail.com"));
		clientes.put("96819473F",new Cliente("96819473F","ANTONIO","649222222","antonio@gmail.com"));
		clientes.put("95767515T",new Cliente("95767515T","AURORA","652333333","aurora@gmail.com"));
		clientes.put("97801164N",new Cliente("97801164N","EMILIO","649222222","emilio@gmail.com"));
		clientes.put("97801364N",new Cliente("97801364N","EVA","652333333","eva@gmail.com"));


		articulos.put("1-11",new Articulo("1-11","RATON LOGITECH ST ",14,15));
		articulos.put("1-22",new Articulo("1-22","TECLADO STANDARD  ",9,18));
		articulos.put("2-11",new Articulo("2-11","HDD SEAGATE 1 TB  ",16,80));
		articulos.put("2-22",new Articulo("2-22","SSD KINGSTOM 256GB",0,70));
		articulos.put("2-33",new Articulo("2-33","SSD KINGSTOM 512GB",5,200));
		articulos.put("3-22",new Articulo("3-22","EPSON PRINT XP300 ",5,80));
		articulos.put("4-11",new Articulo("4-11","ASUS  MONITOR  22 ",10,100));
		articulos.put("4-22",new Articulo("4-22","HP MONITOR LED 28 ",5,180));

		LocalDate hoy = LocalDate.now();
		pedidos.add(new Pedido("90015161S-001/2025",clientes.get("90015161S"),hoy.minusDays(1), new ArrayList<>
				(List.of(new LineaPedido("2-33",5),new LineaPedido("4-11",5)))));
		pedidos.add(new Pedido("90015161S-002/2025",clientes.get("90015161S"),hoy.minusDays(2), new ArrayList<>
				(List.of(new LineaPedido("2-11",5),new LineaPedido("4-11",1)))));
		pedidos.add(new Pedido("96819473F-001/2025",clientes.get("96819473F"),hoy.minusDays(3), new ArrayList<>
				(List.of(new LineaPedido("4-22",1),new LineaPedido("2-22",3)))));
		pedidos.add(new Pedido("95767515T-001/2025",clientes.get("95767515T"),hoy.minusDays(5), new ArrayList<>
				(List.of(new LineaPedido("1-11",3),new LineaPedido("2-11",3)))));
		pedidos.add(new Pedido("97801164N-001/2025",clientes.get("97801164N"),hoy.minusDays(4), new ArrayList<>
				(List.of(new LineaPedido("2-11",1),new LineaPedido("2-33",3),new LineaPedido("1-11",2)))));
	}

}
