package org.springframework.samples.petclinic.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 例外がスローされたときに何が起こるかを示すために使用されるコントローラー
 * @author Michael Isvy
 * <p/>
 * 「error」を解決するビューがどのように追加されたか (「error.html」) も参照してください。
 */
@Controller
class CrashController {
	
	@GetMapping("/oups")
	public String triggerException() {
		throw new RuntimeException(
				"Expected: controller used to showcase what " + "happens when an exception is thrown");
	}
}
