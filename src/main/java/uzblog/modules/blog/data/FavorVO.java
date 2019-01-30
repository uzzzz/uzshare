package uzblog.modules.blog.data;

import uzblog.modules.blog.entity.Favor;

/**
 *  on 2015/8/31.
 */
public class FavorVO extends Favor {
    // extend
    private PostVO post;

    public PostVO getPost() {
        return post;
    }

    public void setPost(PostVO post) {
        this.post = post;
    }
}
