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
import com.viaro.school.model.AlumnoGrado;
import com.viaro.school.model.Grado;
import com.viaro.school.service.AlumnoGradoServiceImpl;
import com.viaro.school.service.AlumnoServiceImpl;
import com.viaro.school.service.GradoServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/views/alumnogrados")
public class AlumnoGradoController {

	@Autowired
	private AlumnoGradoServiceImpl service;
	
	@Autowired
	private AlumnoServiceImpl alumnoService;
	
	@Autowired
	private GradoServiceImpl gradoService;
	
	List<AlumnoGrado> alumnoGrados;
	
	@GetMapping("/")
	public String listarGrupos(Model model) {
		alumnoGrados = service.findAll();
		model.addAttribute("titulo", "Lista de Alumno-Grado");
		model.addAttribute("alumnoGrados", alumnoGrados);
		
		return "/views/alumnogrados/listar";
	}
	
	@GetMapping("/create")
	public String crear(Model model) {

		AlumnoGrado alumnoGrado = new AlumnoGrado();

		List<Alumno> alumnos = alumnoService.findAll();
		List<Grado> grados = gradoService.findAll();
		
		model.addAttribute("titulo", "Nuevo Alumno-Grado");
		model.addAttribute("alumnoGrado", alumnoGrado);
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grados", grados);

		return "/views/alumnogrados/crear";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute AlumnoGrado alumnoGrado, BindingResult result,
			Model model, RedirectAttributes attribute) {
		
		List<Alumno> alumnos = alumnoService.findAll();
		List<Grado> grados = gradoService.findAll();
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo Alumno-Grado");
			model.addAttribute("alumnoGrado", alumnoGrado);
			model.addAttribute("alumnos", alumnos);
			model.addAttribute("grados", grados);
			return "/views/alumnogrados/crear";
		}
		
		if(service.isAlumnoAssignedToGrado(alumnoGrado.getAlumno().getId())) {
			
			attribute.addFlashAttribute("error", "ATENCION: El alumno ya está asignado a un grado, "
					+ "favor de seleccionar uno distinto");
			
			return "redirect:/views/alumnogrados/"; 
		}

		service.save(alumnoGrado);
		attribute.addFlashAttribute("success", "Alumno-Grado guardado con exito!");
		return "redirect:/views/alumnogrados/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long idAlumnoGrado, Model model, RedirectAttributes attribute) {
			
		Optional<AlumnoGrado> alumnoGrado = null;
		
		if (idAlumnoGrado > 0) {
			alumnoGrado = service.findById(idAlumnoGrado);
			
			if (alumnoGrado == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del alumno-grado no existe!");
				return "redirect:/views/alumnogrados/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del grado");
			return "redirect:/views/alumnogrados/";
		}

		List<Alumno> alumnos = alumnoService.findAll();
		List<Grado> grados = gradoService.findAll();

		model.addAttribute("titulo", "Editar Alumno-Grado");
		model.addAttribute("alumnoGrado", alumnoGrado.get());
		model.addAttribute("alumnos", alumnos);
		model.addAttribute("grados", grados);

		return "/views/alumnogrados/crear";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long idAlumnoGrado, RedirectAttributes attribute) {

		Optional<AlumnoGrado> alumnoGrado = null;
		if (idAlumnoGrado > 0) {
			alumnoGrado = service.findById(idAlumnoGrado);
			
			if (alumnoGrado == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del Alumno-Grado no existe!");
				return "redirect:/views/alumnogrados/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del Alumno-Grado!");
			return "redirect:/views/alumnogrados/";
		}		
		
		service.delete(idAlumnoGrado);
		attribute.addFlashAttribute("warning", "Alumno-Grado Eliminado con Exito!");

		return "redirect:/views/alumnogrados/";
	}
	
}
