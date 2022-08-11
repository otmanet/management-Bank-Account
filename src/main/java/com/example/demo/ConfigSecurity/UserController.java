package com.example.demo.ConfigSecurity;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Repository.CompteRepository;
import com.example.demo.Repository.OperationRepository;
import com.example.demo.Service.EmailSendService;
import com.example.demo.Service.UserServer;
import com.example.demo.dto.UserRegisterService;
import com.example.demo.model.Compte;
import com.example.demo.model.CompteCourant;

import com.example.demo.model.Retrait;
import com.example.demo.model.Versement;

@Controller
public class UserController {

	@Autowired
	private CompteRepository compteRepository;

	@Autowired
	private OperationRepository operationRepository;

	@Autowired
	private UserServer userServer;
	@Autowired
	private EmailSendService emailSendService;

	public UserController(UserServer userServer) {
		super();
		this.userServer = userServer;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/GestionAccount")
	public String GestionAccount() {
		return "GestionAccount";
	}

	@GetMapping(value = "/SearchCompte")
	public String searchCompte(Model model, @RequestParam(name = "code") String code) {

		try {
			Compte c = compteRepository.findById(code).get();
			model.addAttribute("compte", c);
			return "GestionAccount";
		} catch (Exception e) {
			String message = "Compte Introuvable";
			model.addAttribute("message", message);
			return "GestionAccount";
		}
	}

	@RequestMapping(value = "/saveOperations/{code}", method = RequestMethod.POST)
	public String verser(Model model, @PathVariable("code") String code, double montant, String typeOperation,
			String versCompte, boolean deleted) {
		try {
			Compte cp = compteRepository.findById(code).get();
			if (typeOperation.equals("V")) {

				Versement v = new Versement(new Date(), montant, deleted,cp );
				operationRepository.save(v);
				cp.setSold(cp.getSold() + montant);
				compteRepository.save(cp);

			} else if (typeOperation.equals("R")) {
				double facil = 0;
				if (cp instanceof CompteCourant)
					facil = ((CompteCourant) cp).getDecouvert();
				if (cp.getSold() + facil < montant)
					throw new RuntimeException("sold ghadi isali");
				Retrait r = new Retrait(new Date(), montant, deleted,cp );
				operationRepository.save(r);
				cp.setSold(cp.getSold() - montant);
				compteRepository.save(cp);
			}
			if (typeOperation.equals("sendAnotherAccount")) {
				double facil = 0;
				if (cp instanceof CompteCourant)
					facil = ((CompteCourant) cp).getDecouvert();
				if (cp.getSold() + facil < montant)
					throw new RuntimeException("sold sala");

				Retrait r = new Retrait(new Date(), montant, deleted,cp );
				operationRepository.save(r);
				cp.setSold(cp.getSold() - montant);
				compteRepository.save(cp);
				Compte c2 = compteRepository.findById(versCompte).get();
				Versement v = new Versement(new Date(), montant, deleted,cp );
				if (cp.equals(c2))
					throw new RuntimeException("Impossible virement sur le meme compte");
				operationRepository.save(v);
				c2.setSold(c2.getSold() + montant);
				compteRepository.save(c2);
			}
			return searchCompte(model, cp.getCode());
		} catch (Exception e) {
			Compte cp = compteRepository.findById(code).get();
			model.addAttribute("soldsala", e);
			return searchCompte(model, cp.getCode());
		}

	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/403")
	public String notfound() {
		return "403";
	}

	@ModelAttribute("user")
	public UserRegisterService userRegisterService() {
		return new UserRegisterService();
	}

	@GetMapping("/registerform")
	public String showFormRegister() {
		return "register";
	}

	@PostMapping("/registerSave")
	public String RegisterUserAccount(
			@ModelAttribute("user") UserRegisterService registerService) {

		emailSendService.sendMail(registerService);
		userServer.save(registerService);
		return "redirect:/login";
	}
}
