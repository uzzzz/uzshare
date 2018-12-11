/*
+--------------------------------------------------------------------------
|   
|   ========================================
|   Copyright (c) 2014, 2015 mtons. All Rights Reserved
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.blog.data;

import uzblog.modules.blog.entity.Feeds;

/**
 * 订阅
 * @author langhsu
 *
 */
public class FeedsVO extends Feeds {
	private PostVO post;

	public PostVO getPost() {
		return post;
	}

	public void setPost(PostVO post) {
		this.post = post;
	}

}
