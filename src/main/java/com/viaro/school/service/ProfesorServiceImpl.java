package com.viaro.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viaro.school.model.Profesor;
import com.viaro.school.repository.ProfesorRepository;

@Service
public class ProfesorServiceImpl implements IProfesorService{

	@Autowired
	private ProfesorRepository repository;
	
	@Override
	public List<Profesor> findAll() {
		return (List<Profesor>) repository.findAll();
	}

	@Override
	public void save(Profesor profesor) {
		repository.save(profesor);
	}

	@Override
	public Optional<Profesor> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

}
