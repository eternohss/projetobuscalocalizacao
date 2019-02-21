package br.com.credito.recurso.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import br.com.credito.recurso.model.Cliente;

public interface ClienteRepository extends Repository<Cliente, Integer> {

	void save(Cliente cliente);

	void delete(Cliente cliente);

	List<Cliente> findAll();

	Cliente findById(Integer id);
}
