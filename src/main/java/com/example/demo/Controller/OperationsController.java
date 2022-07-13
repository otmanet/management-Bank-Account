package com.example.demo.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Repository.CompteRepository;
import com.example.demo.Repository.OperationRepository;
import com.example.demo.model.Compte;
import com.example.demo.model.CompteCourant;
import com.example.demo.model.Operation;
import com.example.demo.model.Retrait;
import com.example.demo.model.Versement;

//@RestController
@Controller
public class OperationsController {
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private CompteRepository compteRepository;

	@RequestMapping(value = "/Operations", method = RequestMethod.GET)
	public String Operations(Model model, @RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		Page<Operation> pageOperation = operationRepository.SearchOperation("%" + motCle + "%",
				PageRequest.of(page, size));
		model.addAttribute("Operations", pageOperation);
		model.addAttribute("pageCourante", page);
		model.addAttribute("mc", motCle);
		int[] pages = new int[pageOperation.getTotalPages()];
		for (int i = 0; i < pages.length; i++)
			pages[i] = i;
		model.addAttribute("pages", pages);
		return "Operation";
	}

	@GetMapping(value = "/formOperation")
	public String formOperation(Model model) {
		List<Compte> listCompte = compteRepository.findAll();
		model.addAttribute("listCompte", listCompte);
		return "SaveOperation";
	}

	@PostMapping(value = "/saveOp")
	public String saveOperation(Model model, Compte compte, double montant, String typeOperation, boolean deleted,
			@RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		try {
			if (typeOperation.equals("V")) {
				Versement v = new Versement(new Date(), montant, compte, deleted);
				operationRepository.save(v);
				compte.setSold(compte.getSold() + montant);
				compteRepository.save(compte);

			} else if (typeOperation.equals("R")) {
				double facil = 0;
				if (compte instanceof CompteCourant)
					facil = ((CompteCourant) compte).getDecouvert();
				if (compte.getSold() + facil < montant)
					throw new RuntimeException("sold ghadi isali");
				Retrait r = new Retrait(new Date(), montant, compte, deleted);
				operationRepository.save(r);
				compte.setSold(compte.getSold() - montant);
				compteRepository.save(compte);
			}
			return Operations(model, motCle, page, size);
		} catch (Exception e) {
			List<Compte> listCompte = compteRepository.findAll();
			model.addAttribute("listCompte", listCompte);
			model.addAttribute("soldsala", e);
			return "SaveOperation";
		}
	}

	@GetMapping(value = "/Operation/delete")
	public String DeleteOperation(String code, Long numero, Model model, String type,
			@RequestParam(name = "mc", defaultValue = "") String motCle,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size) {
		if (type.equals("Versement")) {
			Operation o = operationRepository.findById(numero).get();
			Compte c = compteRepository.findById(code).get();
			c.setSold(c.getSold() - o.getMontant());
			compteRepository.save(c);
			operationRepository.deleteById(numero);
		} else if (type.equals("Retrait")) {
			Operation o = operationRepository.findById(numero).get();
			Compte c = compteRepository.findById(code).get();
			c.setSold(c.getSold() + o.getMontant());
			compteRepository.save(c);
			operationRepository.deleteById(numero);
		}
		return Operations(model, motCle, page, size);
	}

}
