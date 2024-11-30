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
	// @InitBinder バインド処理が行われる前にこのアノテーションを付与したメソッドが呼び出される
	@InitBinder
	public void setAllowedFind(WebDataBinder dataBinder) {
		// バインドを許可しないパラメータを引数に設定する
		dataBinder.setDisallowedFields("id");
	}
	
	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable(name = "ownerId", required = false) Integer ownerId) {
		// ownerIdがnullならOwnerインスタンスを生成し、nullでなければ該当のOwner情報を取得する
		return ownerId == null ? new Owner() : this.owners.findById(ownerId);
	}
	
	@GetMapping("/owners/find")
	public String initFindForm() {
		return "owners/findOwners";
	}
	

}
