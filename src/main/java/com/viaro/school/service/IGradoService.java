package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import com.viaro.school.model.Grado;

public interface IGradoService {

	public List<Grado> findAll();
	
	public void save(Grado grado);
	
	public Optional<Grado> findById(Long id);
	
	public Optional<Grado> findByProfesorId(Long profesorId);
	
	public void delete(Long id);
}
