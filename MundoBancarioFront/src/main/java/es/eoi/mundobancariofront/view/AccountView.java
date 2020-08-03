package es.eoi.mundobancariofront.view;

import java.util.Scanner;

import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.controller.ClienteController;

public class AccountView {

	private static ClienteController controller = MundoBancarioFrontApplication.clienteController;
	
	public static void login() {
		
		System.out.println("Inicio de sesión: ");
		System.out.print("\nUsuario: ");
		String user = new Scanner(System.in).next();
		System.out.print("\nContraseña: ");
		String pass = new Scanner(System.in).next();
		
		controller.loginController(user, pass);
	}
	
	public static void createAccount() {
		System.out.println("Crear cuenta: ");
		System.out.print("\nUsuario: ");
		String user = new Scanner(System.in).next();
		System.out.print("\nContraseña: ");
		String pass = new Scanner(System.in).next();
		System.out.print("\nNombre: ");
		String nombre = new Scanner(System.in).next();
		System.out.print("\nEmail: ");
		String email = new Scanner(System.in).next();
		
		controller.createAccountController(user, pass, nombre, email);
	}
	
}
