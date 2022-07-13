package com.example.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	@Query("select c from Client c where c.nom like:x")
	public Page<Client> SearchClient(@Param("x") String mc, Pageable pageable);
}
