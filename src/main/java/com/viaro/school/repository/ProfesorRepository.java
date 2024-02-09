package com.viaro.school.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.viaro.school.model.Profesor;

@Repository
public interface ProfesorRepository extends CrudRepository<Profesor, Long> {

}
