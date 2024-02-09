package com.viaro.school.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viaro.school.model.Grado;

@Repository
public interface GradoRepository extends CrudRepository<Grado, Long> {

	Optional<Grado> findByProfesorId(Long profesorId);
}
