package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {
	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	
	private final OwnerRepository owners;
	
	public PetController(OwnerRepository owners) {
		this.owners = owners;
	}
	
	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.owners.findPetTypes();
	}
	
	@ModelAttribute("pet")
	public Pet findPet(@PathVariable("ownerId") int ownerId,
			@PathVariable(name = "petId", required = false) Integer petId) {
		
		if (petId == null) {
			return new Pet();
		}
		
		Owner owner =this.owners.findById(ownerId);
		if (owner == null) {
			throw new IllegalArgumentException("Owner ID not found: " + ownerId);
		}
		return owner.getPet(petId);
	}
	
	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}
	
	@GetMapping("/pets/new")
	public String initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		model.put("pet", pet);
		return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/pets/new")
	public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model,
			RedirectAttributes redirectAttributes) {
		if (StringUtils.hasText(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		
		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}
		
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		
		owner.addPet(pet);
		this.owners.save(owner);
		redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
		return "redirect:/owners/{ownersId}";
	}
	
	@GetMapping("/pet/{petId}/edit")
	public String processUpdateFrom(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model,
			RedirectAttributes redirectAttributes) {
		
		String petName = pet.getName();
		
		// checking if the pet name alrady exist for the owner
		if (StringUtils.hasText(petName)) {
			Pet existingPet = owner.getPet(petName.toLowerCase(), false);
			if (existingPet != null && existingPet.getId() != pet.getId()) {
				result.rejectValue("name",  "duplicate", "already exists");
			}
		}
		
		LocalDate currentDate = LocalDate.now();
		if (pet.getBirthDate() != null && pet.getBirthDate().isAfter(currentDate)) {
			result.rejectValue("birthDate", "typeMismatch.birthDate");
		}
		
		if (result.hasErrors()) {
			model.put("pet", pet);
			return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
		}
		
		owner.addPet(pet);
		this.owners.save(owner);
		redirectAttributes.addFlashAttribute("message", "Pet details has been edited");
		return "redirect:/owners/{ownerId}";
		
	}

}
