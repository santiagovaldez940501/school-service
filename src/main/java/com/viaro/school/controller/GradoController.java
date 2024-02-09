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

import com.viaro.school.model.Grado;
import com.viaro.school.model.Profesor;
import com.viaro.school.service.GradoServiceImpl;
import com.viaro.school.service.ProfesorServiceImpl;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/views/grados")
public class GradoController {

	@Autowired
	private GradoServiceImpl service;
	
	@Autowired
	private ProfesorServiceImpl profesorService;
	
	List<Grado> grados;
	
	@GetMapping("/")
	public String listarGrados(Model model) {
	    grados = service.findAll();
		model.addAttribute("titulo", "Lista de Grados");
		model.addAttribute("grados", grados);
		
		return "/views/grados/listar";
	}
	
	@GetMapping("/create")
	public String crear(Model model) {

		Grado grado = new Grado();

		List<Profesor> profesores = profesorService.findAll();
		
		model.addAttribute("titulo", "Nuevo Grado");
		model.addAttribute("grado", grado);
		model.addAttribute("profesores", profesores);

		return "/views/grados/crear";
	}
	
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute Grado grado, BindingResult result,
			Model model, RedirectAttributes attribute) {
		
		List<Profesor> profesores = profesorService.findAll();
		
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Nuevo Grado");
			model.addAttribute("grado", grado);
			model.addAttribute("profesores", profesores);
			
			return "/views/grados/crear";
		}
		
		if(service.isProfesorAssignedToGrado(grado.getProfesor().getId())) {
			
			attribute.addFlashAttribute("error", "ATENCION: El profesor ya está asignado a un grado, "
					+ "favor de seleccionar uno distinto");
			
			return "redirect:/views/grados/";	
		}

		service.save(grado);
		attribute.addFlashAttribute("success", "Grado guardado con exito!");
		
		return "redirect:/views/grados/";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long idGrado, Model model, RedirectAttributes attribute) {
			
		Optional<Grado> grado = null;
		
		if (idGrado > 0) {
			grado = service.findById(idGrado);
			
			if (grado == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del grado no existe!");
				return "redirect:/views/grados/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del grado");
			return "redirect:/views/grados/";
		}

		List<Profesor> profesores = profesorService.findAll();
		
		model.addAttribute("titulo", "Editar Grado");
		model.addAttribute("grado", grado.get());
		model.addAttribute("profesores", profesores);

		return "/views/grados/crear";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long idGrado, RedirectAttributes attribute) {

		Optional<Grado> grado = null;
		if (idGrado > 0) {
			grado = service.findById(idGrado);
			
			if (grado == null) {
				attribute.addFlashAttribute("error", "ATENCION: El ID del grado no existe!");
				return "redirect:/views/grados/";
			}
		}else {
			attribute.addFlashAttribute("error", "ATENCION: Error con el ID del Grado!");
			return "redirect:/views/grados/";
		}		
		
		service.delete(idGrado);
		attribute.addFlashAttribute("warning", "Grado Eliminado con Exito!");

		return "redirect:/views/grados/";
	}
	
}
