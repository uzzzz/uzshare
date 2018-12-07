package uzblog.core.hook.event;

import org.springframework.stereotype.Component;

import uzblog.core.event.NotifyEvent;


/**
 * 处理LogEvent钩子
 *
 * @author Beldon 2015/10/29
 */
@Component
public class NotifyEventHook extends EventHookSupport<NotifyEvent> {
    @Override
    public void init() {
        this.plugins = getPlugins(NotifyEventListener.class);
    }

    @Override
    public void onApplicationEvent(NotifyEvent event) {
    }

    public interface NotifyEventListener extends EventListener<NotifyEvent> {

    }

}
