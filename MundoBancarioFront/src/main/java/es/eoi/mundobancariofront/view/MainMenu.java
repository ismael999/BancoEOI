package es.eoi.mundobancariofront.view;

import java.util.Scanner;

import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.controller.MainMenuController;

public class MainMenu {

	private static MainMenuController controller = MundoBancarioFrontApplication.menuController;
	
	public static void initMenu() {
		System.out.println("1- Crear cuenta");
		System.out.println("2- Iniciar sesión");
		System.out.println("3- SALIR");
		
		Scanner scan = new Scanner(System.in);
		try {
			controller.initMenuController(scan.nextInt());
		} catch (Exception e) {
			System.out.println("Opción no valida.");
			initMenu();
		}
	}
	
	public static void mainMenu() {
		System.out.println("********* Menú principal de "+MundoBancarioFrontApplication.user.getNombre()+" *********");
		
		System.out.println("\n1- Modificar información de usuario");
		System.out.println("2- Consultar mis cuentas");
		System.out.println("3- Solicitar prestamo");
		System.out.println("4- Ingresar");
		System.out.println("5- Hacer un pago");
		System.out.println("6- Amortizar prestamos");
		System.out.println("-------- Sección de administradores --------");
		System.out.println("7- Generación de reportes");
		System.out.println("8- Lista de clientes");
		System.out.println("9- Lista de cuentas");
		System.out.println("10- Lista de cuentas deudoras");
		System.out.println("11- Buscar cuenta");
		System.out.println("12- SALIR.");
		
		Scanner scan = new Scanner(System.in);
		try {
			controller.mainMenuController(scan.nextInt());
		} catch (Exception e) {
			System.out.println("Opción no valida.");
			mainMenu();
		}
	}
	
}