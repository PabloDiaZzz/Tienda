import java.util.Arrays;

public class MetodosAux {
	public static boolean validarDni(String dni) {
		// Verificar que el DNI tiene un formato válido
		if (dni.isBlank() || ! dni.matches("\\d{8}[A-HJ-NP-TV-Z]")) {
			return false;
		}
		// Extraer el número y la letra del DNI
		String numeroStr = dni.substring(0, 8);
		char letra = dni.charAt(8);

		// Calcular la letra correspondiente al número del DNI
		char letraCalculada = calcularLetraDni(Integer.parseInt(numeroStr));
		// Comparar la letra calculada con la letra proporcionada y devolver
		// el resultado de la comparación TRUE/FALSE
		return letra == letraCalculada;
	}

	public static char calcularLetraDni(int numero) {
		String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
		return letras.charAt(numero % 23);
	}

	public static boolean esInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static void menu(boolean input, String[] a) {
		String title = a[0];
		a = Arrays.stream(a).skip(1).toArray(String[]::new);
		int maxValue = 0;
		for (int i = 0; i < a.length; i++) {
			maxValue = Math.max(maxValue, String.valueOf(a.length).length() - String.valueOf(i).length() + ((input ? i + ". " : "") + a[i]).length());
		}
		maxValue = Math.max(maxValue, title.length());
		System.out.print("╔═");
		System.out.print(title.toUpperCase());
		for (int x = 0; x < maxValue + 1 - title.length(); x++) {
			System.out.print('═');
		}
		System.out.println('╗');
		int contador = 1;
		for (String s : a) {
			System.out.print("║ ");
			int espacios = String.valueOf(a.length).length() - String.valueOf(contador).length();
			for (int x = 0; x < espacios; x++) {
				System.out.print(" ");
			}
			System.out.print(input ? contador + ". " : "");
			System.out.print(s);
			for (int y = 0; y < 1 + maxValue - ((input ? espacios : 0) + (input ? contador + ". " + s : s).length()); y++) {
				System.out.print(' ');
			}
			System.out.println("║");
			contador++;
		}
		System.out.print('╚');
		for (int x = 0; x < maxValue + 2; x++) {
			System.out.print('═');
		}
		System.out.println('╝');
		System.out.print(input ? ">> " : "");
	}

	public static void menu(String[] a) {
		menu(true, a);
	}

	public boolean esDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
