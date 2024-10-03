package org.springframework.samples.petclinic.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class WelcomeController {
	// ホーム画面を表示する処理
	@GetMapping("/")
	public String welcome() {
		// welcome.htmlを表示する
		return "welcome";
	}
}
