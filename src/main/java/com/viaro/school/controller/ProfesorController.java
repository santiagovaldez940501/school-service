package com.viaro.school.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.viaro.school.model.Profesor;
import com.viaro.school.service.ProfesorServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/views/profesores")
public class ProfesorController {

	@Autowired
	private ProfesorServiceImpl service;
	
	List<Profesor> profesores;
	
	@GetMapping("/")
	public String listarProfesores(Model model) {
	    profesores = service.findAll();
		model.addAttribute("titulo", "Lista de Profesores");
		model.addAttribute("profesores", profesores);
		
		return "/views/profesores/listar";
	}
	
	@GetMapping("/create")
	public String crear(Model model) {

		Profesor profesor = new Profesor();

		model.addAttribute("titulo", "Nuevo Profesor");
		model.addAttribute("profesor", profesor);

		return "/views/profesores/crear";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Profesor profesor, BindingResult result,
			Model model, RedirectAttributes attribute) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo Profesor");
			model.addAttribute("profesor", profesor);
			return "/views/profesores/crear";
		}

		service.save(profesor);
		attribute.addFlashAttribute("success", "Profesor guardado con exito!");
		return "redirect:/views/profesores/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long idProfesor, Model model, RedirectAttributes attribute) {
			
		Optional<Profesor> profesor = null;
		
		if (idProfesor > 0) {
			profesor = service.findById(idProfesor);
			
			if (profesor == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del profesor no existe!");
				return "redirect:/views/profesores/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del profesor");
			return "redirect:/views/profesores/";
		}

		model.addAttribute("titulo", "Editar Profesor");
		model.addAttribute("profesor", profesor.get());

		return "/views/profesores/crear";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long idProfesor, RedirectAttributes attribute) {

		Optional<Profesor> profesor = null;
		if (idProfesor > 0) {
			profesor = service.findById(idProfesor);
			
			if (profesor == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del profesor no existe!");
				return "redirect:/views/profesores/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del Profesor!");
			return "redirect:/views/profesores/";
		}		
		
		service.delete(idProfesor);
		attribute.addFlashAttribute("warning", "Profesor Eliminado con Exito!");

		return "redirect:/views/profesores/";
	}
	
}
