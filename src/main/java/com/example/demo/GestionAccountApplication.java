package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestionAccountApplication implements CommandLineRunner {

	// @Autowired
	// private ClientRepository clientRepository;
	// @Autowired
	// private CompteRepository compteRepository;
	// @Autowired
	// private OperationRepository operationRepository;

	public static void main(String[] args) {
		SpringApplication.run(GestionAccountApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		// clientRepository.save(new Client("otmane"));
		// clientRepository.save(new Client("twinsa"));
		// clientRepository.save(new Client("rabiaa"));

		// Long id=(long) 72;
		// Client c1=clientRepository.findById(id).get();
		//
		// System.out.println(c1.getNom());

		// compteRepository.save(new CompteCourant("c5", 9000, new Date(), c1, 880.0));
		// compteRepository.save(new CompteEpargne("c18", 7512, new Date(), c1,
		// 5.0,false));

		// Compte cp=compteRepository.findById("vvv").get();

		// System.out.println(cp.getClient().getId());
		// operationRepository.save(new Versement( new Date(),6000,cp));
		// operationRepository.save(new Versement(new Date(),7000,cp));
		// operationRepository.save(new Retrait(new Date(),5400,cp));

		System.out.println("***********************");
	}

}
