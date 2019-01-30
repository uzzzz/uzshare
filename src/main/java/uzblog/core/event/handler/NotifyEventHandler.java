package uzblog.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import uzblog.base.lang.Consts;
import uzblog.core.event.NotifyEvent;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.service.PostCacheableService;
import uzblog.modules.user.data.NotifyVO;
import uzblog.modules.user.service.NotifyService;

/**
 *  on 2015/8/31.
 */
@Component
public class NotifyEventHandler implements ApplicationListener<NotifyEvent> {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private PostCacheableService postService;

    @Async
    @Override
    public void onApplicationEvent(NotifyEvent event) {
        NotifyVO nt = new NotifyVO();
        nt.setPostId(event.getPostId());
        nt.setFromId(event.getFromUserId());
        nt.setEvent(event.getEvent());

        switch (event.getEvent()) {
            case Consts.NOTIFY_EVENT_FAVOR_POST:
                PostVO p = postService.get(event.getPostId());
                nt.setOwnId(p.getAuthorId());
                break;
            case Consts.NOTIFY_EVENT_COMMENT:
            case Consts.NOTIFY_EVENT_COMMENT_REPLY:
                PostVO p2 = postService.get(event.getPostId());
                nt.setOwnId(p2.getAuthorId());

                // 自增评论数
                postService.identityComments(event.getPostId());
                break;
            default:
                nt.setOwnId(event.getToUserId());
        }

        notifyService.send(nt);
    }
}
