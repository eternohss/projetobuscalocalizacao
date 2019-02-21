package br.com.credito.recurso.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.credito.recurso.model.Cliente;
import br.com.credito.recurso.model.ResponseModel;
import br.com.credito.recurso.service.ClienteService;

@CrossOrigin
@RestController
@RequestMapping("/service")
public class ClienteController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * SALVAR UM NOVO REGISTRO DE RISCO CLIENTE
	 * @param cliente
	 * @return
	 */
	@RequestMapping(value="/cliente/taxarisco", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel salvarTaxaRisco(@RequestBody Cliente cliente){
 
		try {
 
			this.clienteService.riscoCliente(cliente);
 
			return new ResponseModel(1,"Registro salvo com sucesso!");
 
		}catch(Exception e) {
 
			return new ResponseModel(0,e.getMessage());			
		}
	}
	
	/**
	 * SALVAR UM NOVO REGISTRO
	 * @param cliente
	 * @return
	 */
	@RequestMapping(value="/cliente", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel salvar(@RequestBody Cliente cliente){
 
		try {
			
			this.clienteService.save(cliente, request.getRemoteAddr());
 
			return new ResponseModel(1,"Registro salvo com sucesso!");
 
		}catch(Exception e) {
 
			return new ResponseModel(0,e.getMessage());			
		}
	}
	
	/**
	 * ATUALIZAR O REGISTRO DE UM CLIENTE
	 * @param cliente
	 * @return
	 */
	@RequestMapping(value="/cliente", method = RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel atualizar(@RequestBody Cliente cliente){
 
		try {
 
			this.clienteService.update(cliente);	
 
			return new ResponseModel(1,"Registro atualizado com sucesso!");
 
		}catch(Exception e) {
 
			return new ResponseModel(0,e.getMessage());
		}
	}
	
	/**
	 * CONSULTAR TODAS AS CLIENTES
	 * @return
	 */
	@RequestMapping(value="/cliente", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody List<Cliente> consultar(){
 
		return this.clienteService.findAll();
	}
 
	/**
	 * BUSCAR UMA CLIENTE PELO ID
	 * @param ID
	 * @return
	 */
	@RequestMapping(value="/cliente/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Cliente buscar(@PathVariable("id") Integer id){
 
		return this.clienteService.findId(id);
	}
	
	/***
	 * EXCLUIR UM REGISTRO PELO ID
	 * @param ID
	 * @return
	 */
	@RequestMapping(value="/cliente/{id}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseModel excluir(@PathVariable("id") Integer id){
 
		try {
			
			clienteService.delete(id);
 
			return new ResponseModel(1, "Registro excluido com sucesso!");
 
		}catch(Exception e) {
			return new ResponseModel(0, e.getMessage());
		}
	}
 

}
