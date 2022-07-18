package com.example.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Compte;
import com.example.demo.model.CompteCourant;
import com.example.demo.model.CompteEpargne;

@Repository
public interface CompteRepository extends JpaRepository<Compte, String> {
	// use join for use all data i want
	@Query("select cc from CompteCourant cc where cc.code like:x")
	public Page<CompteCourant> SearchCompteCourante(@Param("x") String mc, Pageable pageable);

	@Query("select ce from CompteEpargne ce where ce.code like:x")
	public Page<CompteEpargne> SearchCompteEpargne(@Param("x") String mc, Pageable pageable);

}
