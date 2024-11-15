package one.digitalinnovation.gof.controller;

import one.digitalinnovation.gof.model.CpfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.service.ClienteService;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author falvojr
 */
@RestController
@RequestMapping("/clientes")
public class ClienteRestController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private CpfRepository cpfRepository;

	@GetMapping
	public ResponseEntity<Iterable<Cliente>> buscarTodos() {
		return ResponseEntity.ok(clienteService.buscarTodos());
	}

	/**
	* O Metodo buscarPorIdOuCpf retorna dados do cliente passando id ou CPF.
	* O CPF precisa conter caracteres separadores dos digitos
	* para se caracterizar como uma String!
	*
	* @author Lucas-Severo96
	*/
	@GetMapping("/{idOuCpf}")
	public ResponseEntity<Cliente> buscarPorIdOuCpf(@PathVariable String idOuCpf) {
		try {
			// Tenta converter o valor para Long (indicando uma busca por ID)
			Long id = Long.parseLong(idOuCpf);
			return ResponseEntity.ok(clienteService.buscarPorId(id));
		} catch (NumberFormatException e) {
			// Se ocorrer exceção, então o valor é CPF
			return clienteService.buscarPorCpf(idOuCpf)
					.map(ResponseEntity::ok)
					.orElse(ResponseEntity.notFound().build());
		}
	}

	/*Obrigatorio passar o CEP do cliente para o ViaCEP retornar as informacoes corretas*/
	@PostMapping
	public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) {
		clienteService.inserir(cliente);
		return ResponseEntity.ok(cliente);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
		clienteService.atualizar(id, cliente);
		return ResponseEntity.ok(cliente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		clienteService.deletar(id);
		return ResponseEntity.ok().build();
	}


}
