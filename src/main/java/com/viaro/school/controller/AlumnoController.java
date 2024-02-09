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

import com.viaro.school.model.Alumno;
import com.viaro.school.service.AlumnoServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/views/alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoServiceImpl service;
	
	List<Alumno> alumnos;
	
	@GetMapping("/")
	public String listarAlumnos(Model model) {
	    alumnos = service.findAll();
		model.addAttribute("titulo", "Lista de Alumnos");
		model.addAttribute("alumnos", alumnos);
		
		return "/views/alumnos/listar";
	}
	
	@GetMapping("/create")
	public String crear(Model model) {

		Alumno alumno = new Alumno();

		model.addAttribute("titulo", "Nuevo Alumno");
		model.addAttribute("alumno", alumno);

		return "/views/alumnos/crear";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Alumno alumno, BindingResult result,
			Model model, RedirectAttributes attribute) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo Alumno");
			model.addAttribute("alumno", alumno);
			System.out.println("Existieron errores en el formulario");			
			return "/views/alumnos/crear";
		}

		service.save(alumno);
		attribute.addFlashAttribute("success", "Alumno guardado con exito!");
		return "redirect:/views/alumnos/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long idAlumno, Model model, RedirectAttributes attribute) {
			
		Optional<Alumno> alumno = null;
		
		if (idAlumno > 0) {
			alumno = service.findById(idAlumno);
			
			if (alumno == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del alumno no existe!");
				return "redirect:/views/alumnos/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del alumno");
			return "redirect:/views/alumnos/";
		}

		model.addAttribute("titulo", "Editar Alumno");
		model.addAttribute("alumno", alumno.get());

		return "/views/alumnos/crear";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long idAlumno, RedirectAttributes attribute) {

		Optional<Alumno> alumno = null;
		
		if (idAlumno > 0) {
			alumno = service.findById(idAlumno);
			
			if (alumno == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del alumno no existe!");
				return "redirect:/views/alumnos/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del Alumno!");
			return "redirect:/views/alumnos/";
		}		
		
		service.delete(idAlumno);
		attribute.addFlashAttribute("warning", "Alumno Eliminado con Exito!");

		return "redirect:/views/alumnos/";
	}
	
}
