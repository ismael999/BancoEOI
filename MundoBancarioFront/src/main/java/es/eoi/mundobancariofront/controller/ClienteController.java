package es.eoi.mundobancariofront.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.CuentaDto;
import es.eoi.mundobancario.form.Login;
import es.eoi.mundobancario.form.UpdateClienteForm;
import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.view.ClienteView;
import es.eoi.mundobancariofront.view.CuentaView;
import es.eoi.mundobancariofront.view.MainMenu;

public class ClienteController {

	private final String URL = "http://localhost:8080/micro-cliente/clientes";
	
	public void clienteMenuController(Integer option) {
		switch (option) {
		case 1:
			ClienteView.showCurrentClient(MundoBancarioFrontApplication.user);
			ClienteView.clienteMenu();
			break;
		case 2:
			ClienteView.updateEmail();
			ClienteView.clienteMenu();
			break;
		case 3:
			MainMenu.mainMenu();
			break;
		default:
			System.out.println("Opción no valida.");
			ClienteView.clienteMenu();
			break;
		}
	}
	
	public void clienteCuentasController(Integer option) throws Exception {
		switch (option) {
		case 1:
			CuentaView.createCuentaView();
			break;
		case 2:
			CuentaView.updateCuenta();
			break;
		case 3:
			CuentaView.showMovimientos();
			break;
		case 4:
			CuentaView.showPrestamos();
			break;
		case 5:
			CuentaView.showPrestamosVivos();
			break;
		case 6:
			CuentaView.showPrestamosAmortizados();
			break;
		case 7:
			MainMenu.mainMenu();
			break;
		default:
			throw new Exception("Invalid Option");
		}
	}
	
	public void loginController(String user, String pass) {
		Login loginForm = new Login();
		loginForm.setUsername(user);
		loginForm.setPassword(pass);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Login> loginEntity = new HttpEntity<>(loginForm, headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ClienteDto> response = restTemplate.exchange(URL.concat("/login"),HttpMethod.POST, loginEntity, ClienteDto.class);
			
			MundoBancarioFrontApplication.user = response.getBody();
		} catch (Exception e) {
			System.out.println("Usuario o contraseña incorrectos.");
		}
	}
	
	public void createAccountController(String user, String pass, String nombre, String email) {
		
		ClienteDto cliente = new ClienteDto();
		cliente.setEmail(email);
		cliente.setNombre(nombre);
		cliente.setPass(pass);
		cliente.setUsuario(user);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ClienteDto> loginEntity = new HttpEntity<>(cliente, headers);
		
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL, HttpMethod.POST, loginEntity, String.class);
			
			loginController(user, pass);
		} catch (Exception e) {
			System.out.println("Error al crear el usuario.");
		}
	}
	
	public void getAllClientes() {
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ClienteDto[]> response = restTemplate.getForEntity(URL, ClienteDto[].class);
			
			List<ClienteDto> clientes = new ArrayList<ClienteDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				clientes.add(response.getBody()[i]);
			}
			
			ClienteView.showClientesList(clientes);
		} catch (Exception e) {
			System.out.println("Error al recuperar los usuarios.");
		}
	}
	
	public void findByIf() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ClienteDto> response = restTemplate.getForEntity(URL.concat("/"+MundoBancarioFrontApplication.user.getId()), ClienteDto.class);
					
			ClienteView.showCurrentClient(response.getBody());
		} catch (Exception e) {
			System.out.println("Error al recuperar los usuarios.");
		}
	}
	
	public void updateEmail(String email) {
		UpdateClienteForm emailForm = new UpdateClienteForm();
		emailForm.setEmail(email);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UpdateClienteForm> form = new HttpEntity<>(emailForm, headers);
		
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/"+MundoBancarioFrontApplication.user.getId()), HttpMethod.PUT, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al actualizar el email.");
		}
	}
	
	public List<CuentaDto> getCuentas() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(URL.concat("/"+MundoBancarioFrontApplication.user.getId()+"/cuentas"), CuentaDto[].class);
			
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
}
