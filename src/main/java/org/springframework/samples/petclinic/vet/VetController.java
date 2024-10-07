package org.springframework.samples.petclinic.vet;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class VetController {
	
	private final VetRepository vetRepository;
	
	public VetController(VetRepository clinicService) {
		this.vetRepository = clinicService;
	}
	
	// 獣医一覧画面を表示する処理
	@GetMapping("/vets.html")
	//@RequestParam:ページ番号のデフォルト値を1で設定している
	public String showVetList(@RequestParam(defaultValue = "1") int page, Model model) {
		Vets vets = new Vets();
		// findPaginatedを呼び出しpaginatedに格納する
		Page<Vet> paginated = findPaginated(page);
		// 
		vets.getVetList().addAll(paginated.toList());
		return addPaginationModel(page, paginated, model);
	}
	
	private String addPaginationModel(int page, Page<Vet> paginated, Model model) {
		List<Vet> listVets = paginated.getContent();
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", paginated.getTotalPages());
		model.addAttribute("totalItems", paginated.getTotalElements());
		model.addAttribute("listVets", listVets);
		return "vets/vetList";
	}
	
	private Page<Vet> findPaginated(int page) {
		int pageSize = 5;
		Pageable pageable = PageRequest.of(page -1, pageSize);
		return vetRepository.findAll(pageable);
	}
	
	@GetMapping({"/vets"})
	// @ResponseBody:指定したメソッドの戻り値をコンテンツとしてそのまま表示できる
	public @ResponseBody Vets showResouecesVetList() {
		Vets vets = new Vets();
		vets.getVetList().addAll(this.vetRepository.findAll());
		return vets;
	}

}
