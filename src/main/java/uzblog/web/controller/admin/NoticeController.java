/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.web.controller.admin;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uzblog.base.data.Data;
import uzblog.modules.blog.entity.Notice;
import uzblog.modules.blog.service.NoticeService;
import uzblog.web.controller.BaseController;

/**
 * 
 *
 */
@Controller("adminNoticeController")
@RequestMapping("/admin/notice")
public class NoticeController extends BaseController {

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/list")
	@RequiresPermissions("channel:list")
	public String list(ModelMap model) {
		model.put("list", noticeService.findAll());
		return "/admin/notice/list";
	}
	
	@RequestMapping("/view")
	public String view(Integer id, ModelMap model) {
		if (id != null) {
			Notice view = noticeService.findById(id);
			model.put("view", view);
		}
		return "/admin/notice/view";
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("channel:update")
	public String update(Notice view) {
		if (view != null) {
			noticeService.update(view);
		}
		return "redirect:/admin/notice/list";
	}
	
	@RequestMapping("/delete")
	@RequiresPermissions("channel:delete")
	public @ResponseBody Data delete(Integer id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				noticeService.delete(id);
				data = Data.success("操作成功", Data.NOOP);
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
}
