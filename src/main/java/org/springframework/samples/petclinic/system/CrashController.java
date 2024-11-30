package org.springframework.samples.petclinic.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class CrashController {
	
	// エラー確認画面を表示する処理
	@GetMapping("/oups")
	public String triggerException() {
		// 何かしらのeExceptionが発生すると下記メッセージを格納する
		throw new RuntimeException("Expected: controller used to showcase what " + "happens when an exception is thrown");
	}

}
