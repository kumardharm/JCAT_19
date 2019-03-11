package com.cg.jcat.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.cg.jcat.api.dao.ApplicationModel;
import com.cg.jcat.api.exception.ApplicationIdNotFoundException;
import com.cg.jcat.api.exception.SystemExceptions;

@RestController
@RequestMapping("/application")
public interface IApplicationController {
	
	@GetMapping("/getAll")
	public List<ApplicationModel> getApplications() throws SystemExceptions;
	
	@GetMapping("/getApplication/{aid}")
	public ApplicationModel getApplicationByApplicationId(@PathVariable String aid) throws ApplicationIdNotFoundException;
	
	@PostMapping("/create")
	public boolean save(@RequestBody ApplicationModel application) throws SystemExceptions;

	@DeleteMapping("/delete/{aid}")
	public boolean deleteApplicationById(@PathVariable int aid)  throws ApplicationIdNotFoundException, SystemExceptions;
	
	@PutMapping("/deactivate/{aid}")
	public boolean deactivateApplicationById(@PathVariable int aid) throws ApplicationIdNotFoundException, SystemExceptions;

	@PutMapping("/update")
	public boolean updateApplication(@RequestBody ApplicationModel application) throws ApplicationIdNotFoundException, SystemExceptions;

	
	@PostMapping(value="/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	@RequestMapping("/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void importfile(@RequestParam("file") MultipartFile file);
	
//	@PostMapping(value="/import")
//	public void importfile();
}
