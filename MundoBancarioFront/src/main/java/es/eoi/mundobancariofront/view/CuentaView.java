package es.eoi.mundobancariofront.view;

import java.text.DateFormat;
import java.util.List;
import java.util.Scanner;

import es.eoi.mundobancario.dto.AmortizacionDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.form.CreateCuentaForm;
import es.eoi.mundobancario.form.CreatePrestamoForm;
import es.eoi.mundobancario.form.MovimientoForm;
import es.eoi.mundobancario.form.UpdateCuentaForm;
import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.controller.CuentaController;

public class CuentaView {

	private static CuentaController controller = MundoBancarioFrontApplication.cuentaController;
	private static DateFormat dateFormat = DateFormat.getDateInstance();

	public static void showCuentasList() {
		List<CuentaDto> cuentas = controller.getAllCuentas();

		System.out.println("*********** Lista de cuentas ***********");
		for (CuentaDto cuenta : cuentas) {
			System.out.println(
					"> Cliente: " + cuenta.getCliente().getNombre() + " | Email: " + cuenta.getCliente().getEmail());
			System.out.println("	-> Cuenta: " + cuenta.getAlias() + " | Saldo: " + cuenta.getSaldo());
		}
	}

	public static void showCuentasDeudoras() {
		List<CuentaDto> cuentas = controller.getCuentasDeudoras();

		System.out.println("*********** Lista de cuentas deudoras ***********");
		for (CuentaDto cuenta : cuentas) {
			System.out.println(
					"> Cliente: " + cuenta.getCliente().getNombre() + " | Email: " + cuenta.getCliente().getEmail());
			System.out.println("	-> Cuenta: " + cuenta.getAlias() + " | Saldo: " + cuenta.getSaldo());
		}
	}

	public static void showCuentaById() {
		System.out.println("Escribe el id de la cuenta: ");
		CuentaDto cuenta;
		try {
			Integer id = new Scanner(System.in).nextInt();
			cuenta = controller.getCuentaById(id);

			System.out.println(
					"> Cliente: " + cuenta.getCliente().getNombre() + " | Email: " + cuenta.getCliente().getEmail());
			System.out.println("	-> Cuenta: " + cuenta.getAlias() + " | Saldo: " + cuenta.getSaldo());

		} catch (Exception e) {
			System.out.println("Id introducido no valido.");
		}
	}

	public static void createCuentaView() {
		CreateCuentaForm cuenta = new CreateCuentaForm();
		System.out.print("\nAlias de la cuenta: ");
		cuenta.setAlias(new Scanner(System.in).next());

		controller.createCuenta(cuenta);
	}

	public static void updateCuenta() {
		UpdateCuentaForm form = new UpdateCuentaForm();

		System.out.print("\nId de la cuenta: ");
		Integer id = new Scanner(System.in).nextInt();

		System.out.print("\nNuevo Alias: ");
		form.setAlias(new Scanner(System.in).nextLine());

		controller.updateCuenta(form, id);
	}

	public static void showMovimientos() {
		System.out.print("\nId de la cuenta:");
		Integer id = new Scanner(System.in).nextInt();

		List<MovimientoDto> movimientos = controller.getMovimientos(id);
		System.out.println("\n******* Movimientos *******");
		for (MovimientoDto movimiento : movimientos) {
			System.out.println("Descripción: " + movimiento.getDescripcion() + " | Importe: " + movimiento.getImporte()
					+ " | Fecha: " + dateFormat.format(movimiento.getFecha()) + " | Tipo: "
					+ movimiento.getTipo().getTipo());
		}
	}

	public static void showPrestamos() {
		System.out.print("\nId de la cuenta:");
		Integer id = new Scanner(System.in).nextInt();

		List<PrestamoDto> prestamos = controller.getPrestamos(id);
		System.out.println("\n******* Prestamos *******");
		for (PrestamoDto prestamo : prestamos) {
			String pagado = "";

			if (prestamo.isPagado()) {
				pagado = "Si";
			} else {
				pagado = "No";
			}
			List<AmortizacionDto> amortizaciones = prestamo.getAmortizaciones();

			System.out.println("- Descripción: " + prestamo.getDescripcion() + " | Fecha: "
					+ dateFormat.format(prestamo.getFecha()) + " | Importe: " + prestamo.getImporte() + " | Plazos: "
					+ prestamo.getPlazos() + " | Pagado: " + pagado);

			for (AmortizacionDto amortizacion : amortizaciones) {
				if (amortizacion.isPagado()) {
					pagado = "Si";
				} else {
					pagado = "No";
				}
				System.out.println("	> Fecha: " + dateFormat.format(amortizacion.getFecha()) + " | Importe: "
						+ amortizacion.getImporte() + " | Pagado: " + pagado);
			}
		}
	}

	public static void showPrestamosVivos() {
		System.out.print("\nId de la cuenta:");
		Integer id = new Scanner(System.in).nextInt();

		List<PrestamoDto> prestamos = controller.getPrestamosVivos(id);

		System.out.println("\n******* Prestamos Vivos *******");
		for (PrestamoDto prestamo : prestamos) {
			String pagado = "";

			if (prestamo.isPagado()) {
				pagado = "Si";
			} else {
				pagado = "No";
			}
			List<AmortizacionDto> amortizaciones = prestamo.getAmortizaciones();

			System.out.println("- Descripción: " + prestamo.getDescripcion() + " | Fecha: "
					+ dateFormat.format(prestamo.getFecha()) + " | Importe: " + prestamo.getImporte() + " | Plazos: "
					+ prestamo.getPlazos() + " | Pagado: " + pagado);

			for (AmortizacionDto amortizacion : amortizaciones) {
				if (amortizacion.isPagado()) {
					pagado = "Si";
				} else {
					pagado = "No";
				}
				System.out.println("	> Fecha: " + dateFormat.format(amortizacion.getFecha()) + " | Importe: "
						+ amortizacion.getImporte() + " | Pagado: " + pagado);
			}
		}
	}

	public static void showPrestamosAmortizados() {
		System.out.print("\nId de la cuenta:");
		Integer id = new Scanner(System.in).nextInt();

		List<PrestamoDto> prestamos = controller.getPrestamosAmortizados(id);

		System.out.println("\n******* Prestamos Amortizados *******");
		for (PrestamoDto prestamo : prestamos) {
			String pagado = "";

			if (prestamo.isPagado()) {
				pagado = "Si";
			} else {
				pagado = "No";
			}
			List<AmortizacionDto> amortizaciones = prestamo.getAmortizaciones();

			System.out.println("- Descripción: " + prestamo.getDescripcion() + " | Fecha: "
					+ dateFormat.format(prestamo.getFecha()) + " | Importe: " + prestamo.getImporte() + " | Plazos: "
					+ prestamo.getPlazos() + " | Pagado: " + pagado);

			for (AmortizacionDto amortizacion : amortizaciones) {
				if (amortizacion.isPagado()) {
					pagado = "Si";
				} else {
					pagado = "No";
				}
				System.out.println("	> Fecha: " + dateFormat.format(amortizacion.getFecha()) + " | Importe: "
						+ amortizacion.getImporte() + " | Pagado: " + pagado);
			}
		}
	}

	public static void createPrestamo() {
		CreatePrestamoForm form = new CreatePrestamoForm();

		System.out.println("********* Solicitar prestamo *********");
		System.out.print("\nId de la cuenta: ");
		Integer id = new Scanner(System.in).nextInt();
		
		System.out.print("\nDescripción: ");
		form.setDescripcion(new Scanner(System.in).nextLine());

		System.out.print("\nImporte: ");
		form.setImporte(new Scanner(System.in).nextDouble());

		System.out.print("\nPlazos: ");
		form.setPlazos(new Scanner(System.in).nextInt());

		controller.createPrestamo(form, id);
	}

	public static void createIngreso() {
		MovimientoForm form = new MovimientoForm();

		System.out.println("********* Crear Ingreso *********");
		System.out.print("\nId de la cuenta: ");
		Integer id = new Scanner(System.in).nextInt();
		
		System.out.print("\nDescripción: ");
		form.setDescripcion(new Scanner(System.in).nextLine());

		System.out.print("\nImporte: ");
		form.setImporte(new Scanner(System.in).nextDouble());

		controller.createIngreso(form, id);

	}

	public static void createPago() {
		MovimientoForm form = new MovimientoForm();

		System.out.println("********* Crear Ingreso *********");
		System.out.print("\nId de la cuenta: ");
		Integer id = new Scanner(System.in).nextInt();
		
		System.out.print("\nDescripción: ");
		form.setDescripcion(new Scanner(System.in).nextLine());

		System.out.print("\nImporte: ");
		form.setImporte(new Scanner(System.in).nextDouble());

		controller.createPago(form, id);
	}

	public static void ejecutarAmortizaciones() {
		controller.ejecutarAmortizaciones();
	}
	
}
