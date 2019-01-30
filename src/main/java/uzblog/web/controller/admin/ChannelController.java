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
import uzblog.base.lang.Consts;
import uzblog.boot.ContextStartup;
import uzblog.modules.blog.entity.Channel;
import uzblog.modules.blog.service.ChannelService;
import uzblog.web.controller.BaseController;

/**
 * 
 *
 */
@Controller("adminChannelController")
@RequestMapping("/admin/channel")
public class ChannelController extends BaseController {
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ContextStartup contextStartup;
	
	@RequestMapping("/list")
	@RequiresPermissions("channel:list")
	public String list(ModelMap model) {
		model.put("list", channelService.findAll(Consts.IGNORE));
		return "/admin/channel/list";
	}
	
	@RequestMapping("/view")
	public String view(Integer id, ModelMap model) {
		if (id != null) {
			Channel view = channelService.getById(id);
			model.put("view", view);
		}
		return "/admin/channel/view";
	}
	
	@RequestMapping("/update")
	@RequiresPermissions("channel:update")
	public String update(Channel view) {
		if (view != null) {
			channelService.update(view);

			contextStartup.resetChannels();
		}
		return "redirect:/admin/channel/list";
	}
	
	@RequestMapping("/delete")
	@RequiresPermissions("channel:delete")
	public @ResponseBody Data delete(Integer id) {
		Data data = Data.failure("操作失败");
		if (id != null) {
			try {
				channelService.delete(id);
				data = Data.success("操作成功", Data.NOOP);

				contextStartup.resetChannels();
			} catch (Exception e) {
				data = Data.failure(e.getMessage());
			}
		}
		return data;
	}
	
}
