/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import uzblog.web.controller.BaseController;

/**
 * @author langhsu
 *
 */
@Controller
public class AboutController extends BaseController {
	
	@RequestMapping("/about")
	public String about() {
		return view("/about/about");
	}
	
	@RequestMapping("/joinus")
	public String joinus() {
		return view("/about/joinus");
	}
	
	@RequestMapping("/faqs")
	public String faqs() {
		return view("/about/faqs");
	}

}
