package org.springframework.samples.petclinic.owner;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OwnerController {
	
	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	
	@GetMapping("/owners/new")
	public String initCreationForm(Map<String, Object> model) {
//		Owner owner = new Owner();
//		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}
	
	@GetMapping("owners/find")
	public String initFindForm() {
		return "owners/findOwners";
	}

}
