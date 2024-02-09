package com.viaro.school.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viaro.school.model.AlumnoGrado;

@Repository
public interface AlumnoGradoRepository extends CrudRepository<AlumnoGrado, Long>{

	Optional<AlumnoGrado> findByAlumnoId(Long alumnoId);
	
	Optional<AlumnoGrado> findByGradoId(Long gradoId);

}
