package es.eoi.mundobancariofront.view;

import java.util.List;
import java.util.Scanner;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.controller.ClienteController;

public class ClienteView {

	private static ClienteController controller = MundoBancarioFrontApplication.clienteController;
	
	public static void clienteMenu() {
		System.out.println("\n************ Menu de cliente ************");
		System.out.println("1- Datos personales");
		System.out.println("2- Modificar email");
		System.out.println("3- Volver");
		Integer option = new Scanner(System.in).nextInt();
		
		controller.clienteMenuController(option);
	}
	
	public static void showClientesList(List<ClienteDto> clientes) {
		System.out.println("\n************ Lista de usuarios ************");
		for (ClienteDto cliente : clientes) {
			System.out.println("> Nombre: " + cliente.getNombre() + " | Usuario: " + cliente.getUsuario() + " | Email: " + cliente.getEmail());
		}
		System.out.println("*******************************************");
	}
	
	
	public static void showCurrentClient(ClienteDto cliente) {
		System.out.println("\n************ Datos Personales ************");
		System.out.println("> Nombre: " + cliente.getNombre() + " | Usuario: " + cliente.getUsuario() + " | Email: " + cliente.getEmail());
		System.out.println("********************************************");
	}
	
	public static void updateEmail() {
		System.out.println("\n************ Modificar Email ************");
		System.out.print("\nEmail: ");
		String email = "";
		try {
			email = new Scanner(System.in).next();
		} catch (Exception e) {
			System.out.println("Opción no valida");
			updateEmail();
		}
		
		controller.updateEmail(email);
	}
	
	public static void showCuentasList() {
		List<CuentaDto> cuentas = controller.getCuentas();
		
		System.out.println("\n************ Tus cuentas ************");
		for (CuentaDto cuenta : cuentas) {
			System.out.println(" >Id: "+cuenta.getNum_cuenta()+" | Alias: " + cuenta.getAlias() + " | Saldo: " + cuenta.getSaldo());
		}
		System.out.println("1- Añadir cuenta");
		System.out.println("2- Actualizar cuenta");
		System.out.println("3- Ver movimientos");
		System.out.println("4- Ver prestamos");
		System.out.println("5- Ver prestamos vivos");
		System.out.println("6- Ver prestamos amortizados");
		System.out.println("7- Volver");
		try {
			Integer option = new Scanner(System.in).nextInt();
			controller.clienteCuentasController(option);
		} catch (Exception e) {
			System.out.println("Opción no valida.");
		}
	}
}
