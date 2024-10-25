package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.Patient;
import com.example.demo.repositories.PatientRepo;



@Controller
public class PatientController {

	@Autowired
	PatientRepo prepo;
	
	@GetMapping("/")
	public String addPatient()
	{
		return "add.html";
	}
	
	@PostMapping("/patient/add")
	public String addPatient(Patient p)
	{
		prepo.save(p);
		return "redirect:/patient/display";
	}
	
	@GetMapping("/patient/display")
	public String displayPatient(Model model)
	{	
		List<Patient> p_list=(List<Patient>)prepo.findAll();
		model.addAttribute("patients",p_list);
		return "display";
	}
	
	@GetMapping("/patient/delete/{id}")
	public String deletePatient(@PathVariable Integer id)
	{	
		prepo.deleteById(id);
		return "redirect:/patient/display";
		
	}
	
	@GetMapping("/patient/edit/{id}")
	public String editPatient(@PathVariable Integer id, Model model)
	{	
		Patient ps =	prepo.findById(id).get();
		
		model.addAttribute("patient",ps);
		return "edit";
	}
	@PostMapping("/patient/edit")
	public String editPatient(Patient patient,Model model)
	{	
		Integer id=patient.getId();
		String name=patient.getName();
		String disease=patient.getDisease();
		String admissionDate=patient.getAdmissionDate();
		
		Patient pDB =	prepo.findById(id).get();
		
		pDB.setName(name);
		pDB.setDisease(disease);
		pDB.setAdmissionDate(admissionDate);
		
		prepo.save(pDB);
		
		model.addAttribute("patient",patient);
		
		return "redirect:/patient/display";
	}
	
	
	
	
}
