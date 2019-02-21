package br.com.credito.recurso.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import br.com.credito.recurso.model.Cliente;
import br.com.credito.recurso.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public void save(Cliente cliente, String ip) throws IOException, GeoIp2Exception, Exception{
		
		File database = new File(getClass().getResource("GeoLite2-City.mmdb").getFile());
		DatabaseReader dbReader = new DatabaseReader.Builder(database).build();
		         
		InetAddress ipAddress = InetAddress.getByName(ip);
		CityResponse response = dbReader.city(ipAddress);
		
		Double latitude = response.getLocation().getLatitude();
		
		Double longitude = response.getLocation().getLongitude();
		
		String json_str = json_str(latitude, longitude);
		
		JSONObject my_obj = new JSONObject(json_str);
		
		JSONObject jsonObject = my_obj.getJSONObject("weather");
		
	    String temperatura = jsonObject.getString("main");
	    
	    cliente.setTemperatura(temperatura);
		
		clienteRepository.save(cliente);
	}
	
	 public String json_str(Double latitude, Double longitude ) throws Exception {
	      StringBuilder result = new StringBuilder();
	      
	      String urlString = "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&appid=9e305a41be9379cb679ece450324f811";
	      
	      URL url = new URL(urlString);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
	
	public void update(Cliente cliente){
		Cliente clienteAlterar = clienteRepository.findById(cliente.getId());
		clienteAlterar.setIdade(cliente.getIdade());
		clienteAlterar.setNome(cliente.getNome());
		clienteAlterar.setTemperatura(cliente.getTemperatura());
		clienteRepository.save(clienteAlterar);
	}


	public void delete(Integer id){
		Cliente cliente = clienteRepository.findById(id);
		clienteRepository.delete(cliente);
	}

	public List<Cliente> findAll(){
		return clienteRepository.findAll();
	}

	public Cliente findId(Integer id){
		
		Cliente cliente = clienteRepository.findById(id);
		
		return cliente;
	}
	
	public void riscoCliente(Cliente cliente){
		
		clienteRepository.save(cliente);
	}
	

}
