package org.springframework.samples.petclinic.owner;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/owners")
	public String processFindForm(@RequestParam(defaultValue = "1") int page, Owner owner, BindingResult result, Model model) {
		if (owner.getLastName() == null) {
			owner.setLastName("");
		}
		
		Page<Owner> ownersResults = findPaginatedForOwnersLastName(page, owner.getLastName());
		if (ownersResults.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		}
		
		if (ownersResults.getTotalElements() == 1) {
			owner = ownersResults.iterator().next();
			return "redirect:/owners/" + owner.getId();
		}
		
		return addPaginationModel(page, model, ownersResults);
	}
	
	private String addPaginationModel(int page, Model model, Page<Owner> paginated) {
		List<Owner> listOwners = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listOwners", listOwners);
		return "owners/ownersList";
	}
	
	private Page<Owner> findPaginatedForOwnersLastName(int page, String lastname) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page -1, pageSize);
		return owners.findByLastName(lastname, pageable);
	}
	

}
