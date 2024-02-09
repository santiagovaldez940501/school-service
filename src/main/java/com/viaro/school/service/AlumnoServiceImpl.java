package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viaro.school.model.Alumno;
import com.viaro.school.repository.AlumnoRepository;

@Service
public class AlumnoServiceImpl implements IAlumnoService{

	@Autowired
	private AlumnoRepository repository;
	
	@Override
	public List<Alumno> findAll() {
		return (List<Alumno>) repository.findAll();
	}

	@Override
	public void save(Alumno alumno) {
		repository.save(alumno);
	}

	@Override
	public Optional<Alumno> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
