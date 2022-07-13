package com.example.demo.Controller;

import java.util.Date;
import java.util.List;


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
import com.example.demo.Repository.CompteRepository;
import com.example.demo.model.Client;
import com.example.demo.model.Compte;
import com.example.demo.model.CompteCourant;
import com.example.demo.model.CompteEpargne;


//for return code json
////@RestController

@Controller
public class CompteController {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private ClientRepository clientRepository;
	
	
	
	@RequestMapping(value="/compteCourant",method = RequestMethod.GET)
     public String compte(Model model,@RequestParam(name="mc",defaultValue = "") String motCle
			,@RequestParam(name="page", defaultValue = "0") int page
			,@RequestParam(name="size",defaultValue = "4") int size) {
		
		Page<CompteCourant> pageCompte=compteRepository.SearchCompteCourante("%"+motCle+"%",PageRequest.of(page, size));	
		model.addAttribute("pageCompte",pageCompte);
		model.addAttribute("pageCourante",page);
		model.addAttribute("mc",motCle);
		int[] pages=new int[pageCompte.getTotalPages()];
		for (int i=0;i<pages.length;i++)
			pages[i]=i;
		model.addAttribute("pages",pages);
		return "compte";
	}
	
	@RequestMapping(value="/compteEpargne",method = RequestMethod.GET)
	public String compte1(Model model,@RequestParam(name="mc",defaultValue = "") String motCle
			,@RequestParam(name="page", defaultValue = "0") int page
			,@RequestParam(name="size",defaultValue = "4") int size) {
		
		Page<CompteEpargne> pageCompte=compteRepository.SearchCompteEpargne("%"+motCle+"%",PageRequest.of(page, size));	
		model.addAttribute("pageCompte",pageCompte);
		model.addAttribute("pageCourante",page);
		model.addAttribute("mc",motCle);
		int[] pages=new int[pageCompte.getTotalPages()];
		for (int i=0;i<pages.length;i++)
			pages[i]=i;
		model.addAttribute("pages",pages);
		return "compteEpargne";
	}
	
	@RequestMapping(value="/formCompteC")
	public String formCompteC(Model model) {
        CompteCourant comptecourant=new CompteCourant();
        List<Client> Listclient=clientRepository.findAll();
		model.addAttribute("cc",comptecourant);
    	model.addAttribute("Listclient",Listclient);
		return "CompteCourant";
	}
	
	
	@RequestMapping(value="/save/CompteCourant",method = RequestMethod.POST)
	public String saveCompteCourant(@Valid @ModelAttribute("cc") CompteCourant comptecourant,BindingResult result,Model model
		    ,@RequestParam(name="mc",defaultValue = "") String motCle
			,@RequestParam(name="page", defaultValue = "0") int page
			,@RequestParam(name="size",defaultValue = "4") int size) {
		if(result.hasErrors()) {
			List<Client> Listclient =clientRepository.findAll();
		     model.addAttribute("Listclient",Listclient);
			return "CompteCourant";
		}else {
			comptecourant.setDateCreation(new Date());
			compteRepository.save(comptecourant);
	        return "redirect:/compteCourant?mc="+motCle+"&page="+page+"&size="+size;
		}
	}
	
	
	
	@GetMapping("/compteCourant/delete")
	public String CompteCourantDelete(String code,Model model
		    ,@RequestParam(name="mc",defaultValue = "") String motCle
			,@RequestParam(name="page", defaultValue = "0") int page
			,@RequestParam(name="size",defaultValue = "4") int size) {
		
		compteRepository.deleteById(code);
		return "redirect:/compteCourant?mc="+motCle+"&page="+page+"&size="+size;
		
	}
	
	@GetMapping("/compteCourant/edit")
	public String formEditCompteCourant (String code, Model model) {
		Compte cc = compteRepository.findById(code).get();
		List<Client> Listclient= clientRepository.findAll();
		model.addAttribute("Listclient",Listclient);
		model.addAttribute("cc",cc);
		return "editCompteCourant";
	}
	
	@RequestMapping(value="/formCompteE")
	public String formCompteE(Model model) {
		CompteEpargne ce=new CompteEpargne();
		List<Client> Listclient=clientRepository.findAll();
		model.addAttribute("Listclient",Listclient);
		model.addAttribute("ce",ce);
		return"AddCompteEpargne";
	}
	
	@RequestMapping(value="/save/CompteEpargne",method =RequestMethod.POST )
	public String saveCompteCourant(@Valid @ModelAttribute("ce") CompteEpargne ce,BindingResult errors,Model model
		    ,@RequestParam(name="mc",defaultValue = "") String motCle
			,@RequestParam(name="page", defaultValue = "0") int page
			,@RequestParam(name="size",defaultValue = "4") int size) {
		if(errors.hasErrors()) {
			List<Client> Listclient=clientRepository.findAll();
			model.addAttribute("Listclient",Listclient);
			return "AddCompteEpargne";
		}else {
			ce.setDateCreation(new Date());
			compteRepository.save(ce);
			return "redirect:/compteEpargne?mc="+motCle+"&page="+page+"&size="+size;
		}
		
		
		
	}
	
	@GetMapping(value="/CompteEpargne/delete")
	public String deleteCompteEpargne (String code,Model model
		    ,@RequestParam(name="mc",defaultValue = "") String motCle
			,@RequestParam(name="page", defaultValue = "0") int page
			,@RequestParam(name="size",defaultValue = "4") int size) {
		compteRepository.deleteById(code);
		return "redirect:/compteEpargne?mc="+motCle+"&page="+page+"&size="+size;
		
	}
	
	@GetMapping(value="/CompteEpargne/edit")
	public String editCompteEpargne (String code,Model model) {
		Compte ce=compteRepository.findById(code).get();
	    List<Client> Listclient=clientRepository.findAll();
	    model.addAttribute("Listclient",Listclient);
	    model.addAttribute("ce",ce);
	    return "EditCompteEpargne";
	}
	
}
