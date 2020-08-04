package es.eoi.mundobancariofront.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import es.eoi.mundobancario.dto.ReportsClienteDto;
import es.eoi.mundobancario.dto.ReportsListPrestamoDto;
import es.eoi.mundobancario.dto.ReportsPrestamosDto;
import es.eoi.mundobancariofront.MundoBancarioFrontApplication;
import es.eoi.mundobancariofront.view.MainMenu;
import es.eoi.mundobancariofront.view.ReportsView;

public class ReportsController {

	private final String URL = "http://localhost:8080/micro-report/reports";

	
	public void menuController(Integer option) {
		switch (option) {
		case 1:
			ReportsView.getClienteReport();
			break;
		case 2:
			ReportsView.createClienteReportPDF();
			break;
		case 3:
			ReportsView.getPrestamoReport();
			break;
		case 4:
			ReportsView.createPrestamoReportPDF();
			break;
		case 5:
			ReportsView.getPrestamosVivos();
			break;
		case 6:
			ReportsView.getPrestamosAmortizados();
			break;
		case 7:
			MainMenu.mainMenu();
			break;
		default:
			System.out.println("Opción no valida.");
			ReportsView.menu();
			break;
		}
	}
	
	public ReportsClienteDto getReportFromCliente() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ReportsClienteDto> response = restTemplate.getForEntity(URL.concat("/clientes/"+MundoBancarioFrontApplication.user.getId()), ReportsClienteDto.class);
			
			return response.getBody();
		} catch (Exception e) {
			System.out.println("Error al generar el reporte.");
			return null;
		}
	}
	
	public void createClienteReportPDF() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> form = new HttpEntity<>("", headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/clientes/"+MundoBancarioFrontApplication.user.getId()),HttpMethod.POST, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al crear el PDF.");
		}
	}
	
	public ReportsPrestamosDto getPrestamo(Integer id) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ReportsPrestamosDto> response = restTemplate.getForEntity(URL.concat("/prestamos/"+id), ReportsPrestamosDto.class);
			
			return response.getBody();
		} catch (Exception e) {
			System.out.println("Error al generar el reporte.");
			return null;
		}
	}
	
	public void createPrestamoReportPDF(Integer id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> form = new HttpEntity<>("", headers);
		
		try {
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.exchange(URL.concat("/prestamos/"+id),HttpMethod.POST, form, String.class);
		} catch (Exception e) {
			System.out.println("Error al crear el PDF.");
		}
	}
	
	public List<ReportsListPrestamoDto> getPrestamosVivos() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ReportsListPrestamoDto[]> response = restTemplate.getForEntity(URL.concat("/prestamosVivos"), ReportsListPrestamoDto[].class);
			
			List<ReportsListPrestamoDto> prestamos = new ArrayList<ReportsListPrestamoDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				prestamos.add(response.getBody()[i]);
			}
			
			return prestamos;
		} catch (Exception e) {
			System.out.println("Error al obtener los prestamos.");
			return null;
		}
	}
	
	public List<ReportsListPrestamoDto> getPrestamosAmortizados() {
		try {
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ReportsListPrestamoDto[]> response = restTemplate.getForEntity(URL.concat("/prestamosAmortizados"), ReportsListPrestamoDto[].class);
			
			List<ReportsListPrestamoDto> prestamos = new ArrayList<ReportsListPrestamoDto>();
			for (int i = 0; i < response.getBody().length; i++) {
				prestamos.add(response.getBody()[i]);
			}
			
			return prestamos;
		} catch (Exception e) {
			System.out.println("Error al obtener los prestamos.");
			return null;
		}
	}
}
