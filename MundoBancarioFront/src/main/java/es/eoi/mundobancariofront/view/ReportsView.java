package es.eoi.mundobancariofront.view;

import java.text.DateFormat;
import java.util.List;
import java.util.Scanner;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.ReportsAmortizacionDto;
import es.eoi.mundobancario.dto.ReportsClienteDto;
import es.eoi.mundobancario.dto.ReportsCuentaDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.controller.ReportsController;

public class ReportsView {

	private static ReportsController controller = MundoBancarioFrontApplication.reportsController;
	private static DateFormat dateFormat = DateFormat.getDateInstance();

	public static void menu() {
		System.out.println("1- Generar reporte del cliente " + MundoBancarioFrontApplication.user.getNombre()+ ".");
		System.out.println("2- Generar PDF del cliente " + MundoBancarioFrontApplication.user.getNombre() + ".");
		System.out.println("3- Generar reporte de un prestamo.");
		System.out.println("4- Generar PDF de un prestamo.");
		System.out.println("5- Listado de prestamos vivos.");
		System.out.println("6- Listado de prestamos amortizados.");
		System.out.println("7- Volver");
		
		controller.menuController(new Scanner(System.in).nextInt());
	}
	
	public static void getClienteReport() {
		ReportsClienteDto cliente = controller.getReportFromCliente();
		List<ReportsCuentaDto> cuentas = cliente.getCuentas();
		System.out.println("************ Reporte de usuario ************");

		System.out.println("- Nombre: " + cliente.getNombre() + " | Email: " + cliente.getEmail() + " | Usuario: "
				+ cliente.getUsuario());
		for (ReportsCuentaDto cuenta : cuentas) {
			List<MovimientoDto> movimientos = cuenta.getMovimientos();

			System.out.println("	->Cuenta: " + cuenta.getAlias() + " | Saldo: " + cuenta.getSaldo());
			for (MovimientoDto movimiento : movimientos) {
				System.out.println("		>Movimiento: " + movimiento.getDescripcion() + " | Importe: "
						+ movimiento.getImporte() + " | Fecha: " + dateFormat.format(movimiento.getFecha())
						+ " | Tipo: " + movimiento.getTipo().getTipo());
			}

		}
	}

	public static void createClienteReportPDF() {
		controller.createClienteReportPDF();
	}

	public static void createPrestamoReportPDF() {
		System.out.print("\nId del prestamo: ");
		Integer id = new Scanner(System.in).nextInt();

		controller.createPrestamoReportPDF(id);
	}

	public static void getPrestamoReport() {
		System.out.print("\nId del prestamo: ");
		Integer id = new Scanner(System.in).nextInt();

		ReportsPrestamosDto prestamo = controller.getPrestamo(id);
		ClienteDto cliente = prestamo.getCliente();
		List<ReportsAmortizacionDto> amortizaciones = prestamo.getAmortizaciones();

		System.out.println("- Cliente: " + cliente.getNombre() + " | Email: " + cliente.getEmail() + " | Usuario: "
				+ cliente.getUsuario());
		
		System.out.println("	->Prestamo: " + prestamo.getDescripcion() + " | Importe: " + prestamo.getImporte()
				+ " | Plazos: " + prestamo.getPlazos() + " | Fecha: " + dateFormat.format(prestamo.getFecha()));
		
		for (ReportsAmortizacionDto amortizacion : amortizaciones) {
			System.out.println("		>Importe: " + amortizacion.getImporte() + " | Fecha: " + dateFormat.format(amortizacion.getFecha()));
		}
	}
	
	public static void getPrestamosVivos() {
		List<ReportsListPrestamoDto> prestamos = controller.getPrestamosVivos();
		
		for (ReportsListPrestamoDto prestamo : prestamos) {
			ClienteDto cliente = prestamo.getCliente();

			System.out.println("- Cliente: " + cliente.getNombre() + " | Email: " + cliente.getEmail() + " | Usuario: "
					+ cliente.getUsuario());
			
			System.out.println("	->Prestamo: " + prestamo.getDescripcion() + " | Importe: " + prestamo.getImporte()
					+ " | Plazos: " + prestamo.getPlazos() + " | Fecha: " + dateFormat.format(prestamo.getFecha()));
			
		}
	}
	
	public static void getPrestamosAmortizados() {
		List<ReportsListPrestamoDto> prestamos = controller.getPrestamosAmortizados();
		
		for (ReportsListPrestamoDto prestamo : prestamos) {
			ClienteDto cliente = prestamo.getCliente();

			System.out.println("- Cliente: " + cliente.getNombre() + " | Email: " + cliente.getEmail() + " | Usuario: "
					+ cliente.getUsuario());
			
			System.out.println("	->Prestamo: " + prestamo.getDescripcion() + " | Importe: " + prestamo.getImporte()
					+ " | Plazos: " + prestamo.getPlazos() + " | Fecha: " + dateFormat.format(prestamo.getFecha()));
		}
	}
}
