package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viaro.school.model.Grado;
import com.viaro.school.repository.GradoRepository;

@Service
public class GradoServiceImpl implements IGradoService{

	@Autowired
	private GradoRepository repository;
	
	@Override
	public List<Grado> findAll() {
		return (List<Grado>) repository.findAll();
	}

	@Override
	public void save(Grado grado) {
		repository.save(grado);
	}

	@Override
	public Optional<Grado> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Grado> findByProfesorId(Long profesorId) {
		return repository.findByProfesorId(profesorId);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}
	
	public boolean isProfesorAssignedToGrado(Long profesorId) {
		return findByProfesorId(profesorId).isPresent();
	}

}
