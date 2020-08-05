package es.eoi.mundobancario.serviceInterfaces;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import es.eoi.mundobancario.dto.ClienteDto;
import es.eoi.mundobancario.dto.ReportsClienteDto;

public interface ClienteService {

	public List<ClienteDto> findAll();
	
	public ReportsClienteDto findByIdReport(Integer id);
	
	public ClienteDto findById(Integer id);
	
	public ClienteDto findByUsuarioAndPass(String usuario, String pass);
	
	public void updateEmail(String email, Integer id);
	
	public void create(ClienteDto cliente);
	
	public void findByIdReportPDF(Integer id) throws IOException, MessagingException;
	
}