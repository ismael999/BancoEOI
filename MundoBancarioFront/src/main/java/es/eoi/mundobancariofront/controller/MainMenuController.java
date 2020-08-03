package es.eoi.mundobancariofront.controller;

import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.view.AccountView;
import es.eoi.mundobancariofront.view.ClienteView;
import es.eoi.mundobancariofront.view.CuentaView;
import es.eoi.mundobancariofront.view.MainMenu;
import es.eoi.mundobancariofront.view.ReportsView;

public class MainMenuController {

	private ClienteController clienteController = MundoBancarioFrontApplication.clienteController;
	
	public void initMenuController(Integer option) {
		switch (option) {
		case 1:
			AccountView.createAccount();
			MainMenu.mainMenu();
			break;
		case 2:
			AccountView.login();
			MainMenu.mainMenu();
			break;
		case 3:
			System.out.println("Adiós!");
			break;
		default:
			System.out.println("Opción no valida.");
			MainMenu.initMenu();
			break;
		}
	}
	
	public void mainMenuController(Integer option) {
		switch (option) {
		case 1:
			ClienteView.clienteMenu();
			break;
		case 2:
			ClienteView.showCuentasList();
			MainMenu.mainMenu();
			break;
		case 3:
			CuentaView.createPrestamo();
			MainMenu.mainMenu();
			break;
		case 4:
			CuentaView.createIngreso();
			MainMenu.mainMenu();
			break;
		case 5:
			CuentaView.createPago();
			MainMenu.mainMenu();
			break;
		case 6:
			CuentaView.ejecutarAmortizaciones();
			MainMenu.mainMenu();
			break;
		case 7:
			ReportsView.menu();
			MainMenu.mainMenu();
			break;
		case 8:
			clienteController.getAllClientes();
			MainMenu.mainMenu();
			break;
		case 9:
			CuentaView.showCuentasList();
			MainMenu.mainMenu();
			break;
		case 10:
			CuentaView.showCuentasDeudoras();
			MainMenu.mainMenu();
			break;
		case 11:
			CuentaView.showCuentaById();
			MainMenu.mainMenu();
			break;
		case 12:
			System.out.println("Adios!");
			break;
		default:
			System.out.println("Opción no valida.");
			MainMenu.mainMenu();
			break;
		}
	}
	
}
