package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viaro.school.model.AlumnoGrado;
import com.viaro.school.repository.AlumnoGradoRepository;

@Service
public class AlumnoGradoServiceImpl implements IAlumnoGradoService{

	@Autowired
	private AlumnoGradoRepository repository;
	
	@Override
	public List<AlumnoGrado> findAll() {
		return (List<AlumnoGrado>) repository.findAll();
	}

	@Override
	public void save(AlumnoGrado alumnoGrado) {
		repository.save(alumnoGrado);
		
	}

	@Override
	public Optional<AlumnoGrado> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Optional<AlumnoGrado> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public boolean isAlumnoAssignedToGrado(Long alumnoId) {
		return findByAlumnoId(alumnoId).isPresent();
	}
}
