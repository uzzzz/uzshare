package uzblog.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

import uzblog.core.event.PostUpdateEvent;
import uzblog.modules.user.service.UserEventService;

/**
 * 更新文章统计
 *  
 */
public class PostCountEventHandler implements ApplicationListener<PostUpdateEvent> {
    @Autowired
    private UserEventService userEventService;

    @Async
    @Override
    public void onApplicationEvent(PostUpdateEvent event) {
        if (event == null) {
            return;
        }

        switch (event.getAction()) {
            case PostUpdateEvent.ACTION_PUBLISH:
                userEventService.identityPost(event.getUserId(), event.getPostId(), true);
                break;
            case PostUpdateEvent.ACTION_DELETE:
                userEventService.identityPost(event.getUserId(), event.getPostId(), false);
                break;
        }
    }
}
