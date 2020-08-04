package es.eoi.mundobancariofront.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.dto.MovimientoDto;
import es.eoi.mundobancario.dto.PrestamoDto;
import es.eoi.mundobancario.form.CreateCuentaForm;
import es.eoi.mundobancario.form.CreatePrestamoForm;
import es.eoi.mundobancario.form.MovimientoForm;
import es.eoi.mundobancario.form.UpdateCuentaForm;
import es.eoi.mundobancariofront.MundoBancarioFrontApplication;

public class CuentaController {

	private final String URL = "http://localhost:8080/micro-cuenta/cuentas";
	
	public List<CuentaDto> getAllCuentas(){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(URL, CuentaDto[].class);
			
			List<CuentaDto> cuentas = new ArrayList<CuentaDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				cuentas.add(response.getBody()[i]);
			}
			return cuentas;
		} catch (Exception e) {
			System.out.println("Error al recuperar las cuentas.");
			return null;
		}
	}
	
	public List<CuentaDto> getCuentasDeudoras(){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(URL.concat("/deudoras"), CuentaDto[].class);
			
			List<CuentaDto> cuentas = new ArrayList<CuentaDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				cuentas.add(response.getBody()[i]);
			}
			return cuentas;
		} catch (Exception e) {
			System.out.println("Error al recuperar las cuentas.");
			return null;
		}
	}
	
	public CuentaDto getCuentaById(Integer id) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<CuentaDto> response = restTemplate.getForEntity(URL.concat("/"+id), CuentaDto.class);
			
			return response.getBody();
		} catch (Exception e) {
			System.out.println("Error al recuperar la cuentas.");
			return null;
		}
	}
	
	public void createCuenta(CreateCuentaForm cuenta) {
		
		cuenta.setIdCliente(MundoBancarioFrontApplication.user.getId());
		cuenta.setSaldo(0.0);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CreateCuentaForm> cuentaForm = new HttpEntity<>(cuenta, headers);
	
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL,HttpMethod.POST, cuentaForm, String.class);
			
		} catch (Exception e) {
			System.out.println("Error al crear la cuenta.");
		}
	}
	
	public void updateCuenta(UpdateCuentaForm cuentaForm, Integer id) {
			
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UpdateCuentaForm> form = new HttpEntity<>(cuentaForm, headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/"+id), HttpMethod.PUT, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al actualizar el alias.");
		}
	}
	
	public List<MovimientoDto> getMovimientos(Integer id){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<MovimientoDto[]> response = restTemplate.getForEntity(URL.concat("/"+id+"/movimientos"), MovimientoDto[].class);
			
			List<MovimientoDto> cuentas = new ArrayList<MovimientoDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				cuentas.add(response.getBody()[i]);
			}
			return cuentas;
		} catch (Exception e) {
			System.out.println("Error al recuperar las cuentas.");
			return null;
		}
	}
	
	public List<PrestamoDto> getPrestamos(Integer id){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<PrestamoDto[]> response = restTemplate.getForEntity(URL.concat("/"+id+"/prestamos"), PrestamoDto[].class);
			
			List<PrestamoDto> cuentas = new ArrayList<PrestamoDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				cuentas.add(response.getBody()[i]);
			}
			return cuentas;
		} catch (Exception e) {
			System.out.println("Error al recuperar los prestamos.");
			return null;
		}
	}
	
	public List<PrestamoDto> getPrestamosVivos(Integer id){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<PrestamoDto[]> response = restTemplate.getForEntity(URL.concat("/"+id+"/prestamosVivos"), PrestamoDto[].class);
			
			List<PrestamoDto> cuentas = new ArrayList<PrestamoDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				cuentas.add(response.getBody()[i]);
			}
			return cuentas;
		} catch (Exception e) {
			System.out.println("Error al recuperar los prestamos.");
			return null;
		}
	}
	public List<PrestamoDto> getPrestamosAmortizados(Integer id){
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<PrestamoDto[]> response = restTemplate.getForEntity(URL.concat("/"+id+"/prestamosAmortizados"), PrestamoDto[].class);
			
			List<PrestamoDto> cuentas = new ArrayList<PrestamoDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				cuentas.add(response.getBody()[i]);
			}
			return cuentas;
		} catch (Exception e) {
			System.out.println("Error al recuperar los prestamos.");
			return null;
		}
	}
	
	public void createPrestamo(CreatePrestamoForm prestamo, Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CreatePrestamoForm> form = new HttpEntity<>(prestamo, headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/"+ id + "/prestamos"), HttpMethod.POST, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al crear el prestamo.");
		}
	}
	
	public void createIngreso(MovimientoForm movimiento, Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MovimientoForm> form = new HttpEntity<>(movimiento, headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/"+id + "/ingresos"), HttpMethod.POST, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al crear el ingreso.");
		}
	}
	
	public void createPago(MovimientoForm movimiento, Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<MovimientoForm> form = new HttpEntity<>(movimiento, headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/"+id + "/pagos"), HttpMethod.POST, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al crear el pago.");
		}
	}
	
	public void ejecutarAmortizaciones() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> form = new HttpEntity<>("", headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/"+MundoBancarioFrontApplication.user.getId() + "/ejecutarAmortizacionesDiarias"),HttpMethod.POST, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al ejecutar las amortizaciones diarias.");
		}
	}
}
