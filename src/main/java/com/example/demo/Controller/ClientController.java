package com.example.demo.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.Repository.ClientRepository;
import com.example.demo.model.Client;

@Controller
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;
	
	@RequestMapping(value="/client/index", method = RequestMethod.GET )
	public String clientIndex(Model model, @RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		Page<Client> pageClient=clientRepository.SearchClient("%"+motCle+"%", PageRequest.of(page, size));
		model.addAttribute("pageClient",pageClient);
		model.addAttribute("pageCourante",page);
		model.addAttribute("mc",motCle);
		int[] pages=new int[pageClient.getTotalPages()];
		for (int i=0;i<pages.length;i++)
			pages[i]=i;
		model.addAttribute("pages",pages);
		return "client";
		
	}
	@RequestMapping(value = "/formClient")
	public String formProduit(Model model) {
		Client client = new Client();
		model.addAttribute(client);
		return "formClient";
	}
	@RequestMapping(value="/save/client",method = RequestMethod.POST)
	public String saveClient(@Valid @ModelAttribute("client") Client client,BindingResult errors) {
		if(errors.hasErrors()) {
			return "formClient";
		}else {
			clientRepository.save(client);
			return "redirect:/client/index";
		}
	}
	@GetMapping("/client/delete")
	public String deleteClient(Long id,Model model, @RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		clientRepository.deleteById(id);
		return clientIndex(model,motCle,page,size);
	}
	
	@GetMapping("/client/edit")
	public String formEdit(Long id, Model model) {
		Client client = clientRepository.findById(id).get();
		model.addAttribute(client);
		return "editClient";
	}
}
