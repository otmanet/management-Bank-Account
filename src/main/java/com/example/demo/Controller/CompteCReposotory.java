package com.example.demo.Controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.CompteCourant;

public interface CompteCReposotory extends JpaRepository<CompteCourant, String> {

}
