package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import com.viaro.school.model.AlumnoGrado;

public interface IAlumnoGradoService {

	public List<AlumnoGrado> findAll();
	
	public void save(AlumnoGrado grado);
	
	public Optional<AlumnoGrado> findById(Long id);
	
	public Optional<AlumnoGrado> findByAlumnoId(Long alumnoId);
	
	public void delete(Long id);
}
