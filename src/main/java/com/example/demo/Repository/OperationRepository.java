package com.example.demo.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

	@Query("select o from Operation o where o.compte.code like :x")
	public Page<Operation> SearchOperation(@Param("x") String mc, Pageable pageable);
}
