package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import com.viaro.school.model.Alumno;

public interface IAlumnoService {

	public List<Alumno> findAll();
	
	public void save(Alumno alumno);
	
	public Optional<Alumno> findById(Long id);
	
	public void delete(Long id);
}
