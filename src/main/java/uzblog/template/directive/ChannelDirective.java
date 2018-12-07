package uzblog.template.directive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uzblog.modules.blog.service.ChannelService;
import uzblog.template.DirectiveHandler;
import uzblog.template.TemplateDirective;

@Component
public class ChannelDirective extends TemplateDirective {
    @Autowired
    private ChannelService channelService;

    @Override
    public String getName() {
        return "channel";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer id = handler.getInteger("id", 0);
        handler.put(RESULT, channelService.getById(id)).render();
    }
}