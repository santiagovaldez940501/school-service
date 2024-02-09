package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import com.viaro.school.model.Profesor;

public interface IProfesorService {

	public List<Profesor> findAll();
	
	public void save(Profesor profesor);
	
	public Optional<Profesor> findById(Long id);
	
	public void delete(Long id);
}
