package es.eoi.mundobancariofront;


import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancariofront.controller.ClienteController;
import es.eoi.mundobancariofront.controller.CuentaController;
import es.eoi.mundobancariofront.controller.MainMenuController;
import es.eoi.mundobancariofront.controller.ReportsController;
import es.eoi.mundobancariofront.view.MainMenu;

public class MundoBancarioFrontApplication {

	public static ClienteController clienteController = new ClienteController();
	public static MainMenuController menuController = new MainMenuController();
	public static CuentaController cuentaController = new CuentaController();
	public static ReportsController reportsController = new ReportsController();
	public static ClienteDto user;
	
	public static void main(String[] args) {
		
		System.out.println("*********************************");
		System.out.println("*     Bienvenido a EOI Bank     *");
		System.out.println("*********************************");
		MainMenu.initMenu();
	}

}