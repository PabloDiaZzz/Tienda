// NECESARIO AL MENOS EL METODO MetodosAux.java

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Tienda2025v2 implements Serializable {
	static Tienda2025v2 tienda;
	static Scanner sc = new Scanner(System.in);
	private ArrayList<Pedido> pedidos;
	private HashMap<String, Articulo> articulos;
	private HashMap<String, Cliente> clientes;

	public Tienda2025v2() {
		this.pedidos = new ArrayList<>();
		this.articulos = new HashMap<>();
		this.clientes = new HashMap<>();
	}

	public static void main(String[] args) {
		Tienda2025v2 tienda = new Tienda2025v2();
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
					guardarTienda();
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
			String[] opciones = new String[]{"articulos", "Crear Articulo", "Modificar Articulo", "Eliminar Articulo", "Lista Articulos", "Unidades vendidas", "Salir"};
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
			String title = String.join(" ", Arrays.stream(mod.getDescripcion().split(" ")).filter(s -> !s.isEmpty()).map(String::trim).toArray(String[]::new));
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
		ArrayList<ArrayList<Dato>> tabla = new ArrayList<>();
		tabla.add(new ArrayList<>(List.of(new Dato("ID"), new Dato("Descripción"), new Dato("Uds"))));
		articulos.values().stream()
				.sorted()
				.map(a -> {
					List<String> b = new ArrayList<>();
					b.add(a.getIdArticulo());
					b.add(a.getDescripcion());
					b.add(String.valueOf(pedidos.stream().flatMap(p -> p.getLineaPedido().stream())
							.filter(lp -> lp.getIdArticulo().equals(a.getIdArticulo()))
							.mapToInt(LineaPedido::getUnidades)
							.sum()));
					return b;
				})
				.forEach(e -> {
					Dato d1 = new Dato(e.get(0), 0, "Clave");
					Dato d2 = new Dato(e.get(1), 0, "Texto");
					Dato d3 = new Dato(e.get(2), 0, "Entero");
					tabla.add(new ArrayList<>(Arrays.asList(d1, d2, d3)));
				});
		Gestor2.showInfo(tabla);

	}

	public void crearCliente() {
		sc.nextLine();
		Cliente cliente = new Cliente("", "", "", "");
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
		ArrayList<ArrayList<Dato>> tabla = new ArrayList<>();
		for (int i = 0; i < values.size() + 1; i++) {
			tabla.add(new ArrayList<>());
		}
		tabla.getFirst().add(new Dato("ID",0,"Texto"));
		tabla.getFirst().add(new Dato("Descripcion",0,"Texto")) ;
		tabla.getFirst().add(new Dato("Unidades",0,"Texto"));
		tabla.getFirst().add(new Dato("Pvp",0,"Texto"));
		for (int i = 1; i < values.size() + 1; i++) {
			tabla.get(i).add(new Dato(values.get(i-1).getIdArticulo(),0,"Clave"));
			tabla.get(i).add(new Dato(String.join(" ", Arrays.stream(values.get(i-1).getDescripcion().split(" ")).filter(s -> !s.isEmpty()).map(String::trim).toArray(String[]::new)),0,"Texto"));
			tabla.get(i).add(new Dato(String.valueOf(values.get(i-1).getExistencias()),0,"Texto"));
			tabla.get(i).add(new Dato(String.valueOf(values.get(i-1).getPvp()),0,"Texto"));
		}
		Gestor2.showInfo(tabla);
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
					ArrayList<LineaPedido> CestaCompraAux = pMod.getLineaPedido();
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
						if (!idT.isBlank() && articulos.containsKey(idT) && CestaCompraAux.stream().noneMatch(l -> l.getIdArticulo().equals(finalIdT))) {
							System.out.print(articulos.get(idT).getDescripcion() + "\nIntroduzca las Unidades >> ");
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
							articulos.get(art.getIdArticulo()).setExistencias(articulos.get(art.getIdArticulo()).getExistencias() + pMod.getLineaPedido().get(option - 1).getUnidades());
							pMod.getLineaPedido().get(option - 1).setUnidades(unidades);
							articulos.get(art.getIdArticulo()).setExistencias(articulos.get(art.getIdArticulo()).getExistencias() - unidades);
						} catch (StockAgotado | StockInsuficiente ex) {
							System.out.println(ex.getMessage());
							int disponibles = articulos.get(art.getIdArticulo()).getExistencias();
							System.out.println("¿DESEA PEDIR LAS " + disponibles + " UNIDADES DISPONIBLES? (S/N)");
							sc.nextLine();
							String yn = sc.nextLine();
							if (yn.equalsIgnoreCase("S")) {
								pMod.getLineaPedido().get(option - 1).setUnidades(disponibles);
								articulos.get(art.getIdArticulo()).setExistencias(0);
							}
						}
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
						articulos.get(pMod.getLineaPedido().get(option - 1).getIdArticulo()).setExistencias(articulos.get(pMod.getLineaPedido().get(option - 1).getIdArticulo()).getExistencias() + pMod.getLineaPedido().get(option - 1).getUnidades());
						pMod.getLineaPedido().remove(option - 1);
					}
					break;
				case 4:
					ArrayList<Articulo> values = new ArrayList<>(List.of(pMod.getLineaPedido().stream().map(v -> articulos.get(v.getIdArticulo())).toArray(Articulo[]::new)));
					Collections.sort(values);
					ArrayList<ArrayList<Dato>> tabla = new ArrayList<>();
					tabla.add(new ArrayList<>());
					for (LineaPedido lp : pMod.getLineaPedido()) {
						tabla.add(new ArrayList<>());
					}
					tabla.getFirst().add(new Dato("ID",0,"Texto"));
					tabla.getFirst().add(new Dato("Descripcion",0,"Texto")) ;
					tabla.getFirst().add(new Dato("Unidades",0,"Texto"));
					tabla.getFirst().add(new Dato("Pvp",0,"Texto"));
					for (int i = 1; i < values.size() + 1; i++) {
						tabla.get(i).add(new Dato(values.get(i-1).getIdArticulo(),0,"Clave"));
						tabla.get(i).add(new Dato(String.join(" ", Arrays.stream(values.get(i-1).getDescripcion().split(" ")).filter(s -> !s.isEmpty()).map(String::trim).toArray(String[]::new)),0,"Texto"));
						tabla.get(i).add(new Dato(String.valueOf(values.get(i-1).getExistencias()),0,"Texto"));
						tabla.get(i).add(new Dato(String.valueOf(values.get(i-1).getPvp()),0,"Texto"));
					}
					Gestor2.showInfo(tabla);
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
						for (LineaPedido l : p.getLineaPedido()) {
							articulos.get(l.getIdArticulo()).setExistencias(articulos.get(l.getIdArticulo()).getExistencias() + l.getUnidades());
						}
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
		ArrayList<ArrayList<Dato>> tabla = new ArrayList<>();
		for (int i = 0; i < values.size() + 1; i++) {
			tabla.add(new ArrayList<>());
		}
		tabla.getFirst().add(new Dato("DNI", 0, "Texto"));
		tabla.getFirst().add(new Dato("Nombre", 0, "Texto"));
		tabla.getFirst().add(new Dato("Direccion", 0, "Texto"));
		tabla.getFirst().add(new Dato("Telefono", 0, "Texto"));
		for (int i = 1; i < values.size() + 1; i++) {
			tabla.get(i).add(new Dato(values.get(i - 1).getDni(), 0, "Clave"));
			tabla.get(i).add(new Dato(values.get(i - 1).getNombre(), 0, "Texto"));
			tabla.get(i).add(new Dato(values.get(i - 1).getEmail(), 0, "Texto"));
			tabla.get(i).add(new Dato(values.get(i - 1).getTelefono(), 0, "Texto"));
		}
		Gestor2.showInfo(tabla);
	}

	public void listaPedidos() {
		while (true) {
			System.out.println();
			String[] options = new String[]{"Ordenar pedidos", "Identificador", "Fecha", "Precio total mínimo", "Salir"};
			MetodosAux.menu(options);
			int option = sc.nextInt();
			ArrayList<ArrayList<Dato>> tabla;
			switch (option) {
				case 1:
					System.out.println();
					Collections.sort(pedidos);
					tabla = new ArrayList<>();
					for (int i = 0; i < pedidos.size() + 1; i++) {
						tabla.add(new ArrayList<>());
					}
					tabla.getFirst().add(new Dato("ID", 0, "Clave"));
					tabla.getFirst().add(new Dato("DNI", 0, "Clave"));
					tabla.getFirst().add(new Dato("Fecha", 0, "Texto"));
					tabla.getFirst().add(new Dato("Lista Compra", 0, "Texto"));
					tabla.getFirst().add(new Dato("Pvp total", 0, "Texto"));
					for (int i = 1; i < pedidos.size() + 1; i++) {
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getIdPedido(), 0, "Clave"));
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getClientePedido().getDni(), 0, "Clave"));
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getFechaPedido().toString(), 0, "Texto"));
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getLineaPedido().toString().substring(1, pedidos.get(i - 1).getLineaPedido().toString().length() - 1), 0, "Texto"));
						tabla.get(i).add(new Dato(String.format("%.2f", totalPedido(pedidos.get(i - 1))) + "€", 0, "Texto"));
					}
					Gestor2.showInfo(tabla);
//					pedidos.forEach(System.out::println);
					System.out.println();
					break;
				case 2:
					System.out.println();
					tabla = new ArrayList<>();
					pedidos.sort(Pedido.compFecha);
					for (int i = 0; i < pedidos.size() + 1; i++) {
						tabla.add(new ArrayList<>());
					}
					tabla.getFirst().add(new Dato("ID", 0, "Clave"));
					tabla.getFirst().add(new Dato("DNI", 0, "Clave"));
					tabla.getFirst().add(new Dato("Fecha", 0, "Texto"));
					tabla.getFirst().add(new Dato("Lista Compra", 0, "Texto"));
					tabla.getFirst().add(new Dato("Pvp total", 0, "Texto"));
					for (int i = 1; i < pedidos.size() + 1; i++) {
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getIdPedido(), 0, "Clave"));
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getClientePedido().getDni(), 0, "Clave"));
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getFechaPedido().toString(), 0, "Texto"));
						tabla.get(i).add(new Dato(pedidos.get(i - 1).getLineaPedido().toString().substring(1, pedidos.get(i - 1).getLineaPedido().toString().length() - 1), 0, "Texto"));
						tabla.get(i).add(new Dato(String.format("%.2f", totalPedido(pedidos.get(i - 1))) + "€", 0, "Texto"));
					}
					Gestor2.showInfo(tabla);
//					pedidos.forEach(System.out::println);
					System.out.println();
					break;
				case 3:
					System.out.println();
					System.out.print("Introduzca el valor >> ");
					double v = sc.nextDouble();
					ArrayList<Pedido> pedidosFiltrados = new ArrayList<>(List.of(pedidos.stream().filter(p -> totalPedido(p) > v).sorted(Comparator.comparing(this::totalPedido)).toArray(Pedido[]::new)));
					tabla = new ArrayList<>();
					for (int i = 0; i < pedidosFiltrados.size() + 1; i++) {
						tabla.add(new ArrayList<>());
					}
					tabla.getFirst().add(new Dato("ID", 0, "Clave"));
					tabla.getFirst().add(new Dato("DNI", 0, "Clave"));
					tabla.getFirst().add(new Dato("Fecha", 0, "Texto"));
					tabla.getFirst().add(new Dato("Lista Compra", 0, "Texto"));
					tabla.getFirst().add(new Dato("Pvp total", 0, "Texto"));
					for (int i = 1; i < pedidosFiltrados.size() + 1; i++) {
						tabla.get(i).add(new Dato(pedidosFiltrados.get(i - 1).getIdPedido(), 0, "Clave"));
						tabla.get(i).add(new Dato(pedidosFiltrados.get(i - 1).getClientePedido().getDni(), 0, "Clave"));
						tabla.get(i).add(new Dato(pedidosFiltrados.get(i - 1).getFechaPedido().toString(), 0, "Texto"));
						tabla.get(i).add(new Dato(pedidosFiltrados.get(i - 1).getLineaPedido().toString().substring(1, pedidosFiltrados.get(i - 1).getLineaPedido().toString().length() - 1), 0, "Texto"));
						tabla.get(i).add(new Dato(String.format("%.2f", totalPedido(pedidosFiltrados.get(i - 1))) + "€", 0, "Texto"));
					}
					Gestor2.showInfo(tabla);
//					pedidos.stream().filter(p -> totalPedido(p) > v).sorted(Comparator.comparing(this::totalPedido)).forEach(System.out::println);
					System.out.println();
					break;
			}
			if (option == options.length - 1) {
				break;
			}
		}

	}

	public double totalPedido(Pedido p) {
		double total = 0;
		for (LineaPedido lp : p.getLineaPedido()) {
			total += articulos.get(lp.getIdArticulo()).getPvp() * lp.getUnidades();
		}
		return total;
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
						stock(pedidas, idT);
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
