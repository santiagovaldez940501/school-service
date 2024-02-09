package com.viaro.school.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viaro.school.model.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long>{

}
