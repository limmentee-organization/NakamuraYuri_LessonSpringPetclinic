package org.springframework.samples.petclinic.owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OwnerController {
	
	private final OwnerRepository owners;
	
	public OwnerController(OwnerRepository clinicService) {
		this.owners = clinicService;
	}
	
	@InitBinder
	public void setAllowedFind(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable(name = "ownerId", required = false) Integer ownerId) {
		return ownerId == null ? new Owner() : this.owners.findById(ownerId);
	}
	
	@GetMapping("/owners/find")
	public String initFindForm() {
		return "owners/findOwners";
	}
	

}
