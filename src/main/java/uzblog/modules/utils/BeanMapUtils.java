/*
+--------------------------------------------------------------------------
|   
|   ========================================
|    
|   
|
+---------------------------------------------------------------------------
*/
package uzblog.modules.utils;

import org.springframework.beans.BeanUtils;

import uzblog.modules.blog.data.CommentVO;
import uzblog.modules.blog.data.FavorVO;
import uzblog.modules.blog.data.FeedsVO;
import uzblog.modules.blog.data.PostVO;
import uzblog.modules.blog.entity.Comment;
import uzblog.modules.blog.entity.Favor;
import uzblog.modules.blog.entity.Feeds;
import uzblog.modules.blog.entity.Post;
import uzblog.modules.user.data.AccountProfile;
import uzblog.modules.user.data.NotifyVO;
import uzblog.modules.user.data.UserVO;
import uzblog.modules.user.entity.Notify;
import uzblog.modules.user.entity.User;

/**
 * 
 *
 */
public class BeanMapUtils {
	public static String[] USER_IGNORE = new String[] { "password", "extend", "roles" };

	public static String[] POST_IGNORE_LIST = new String[] { "markdown", "content" };

	public static UserVO copy(User po, int level) {
		if (po == null) {
			return null;
		}
		UserVO ret = new UserVO();
		BeanUtils.copyProperties(po, ret, USER_IGNORE);
		return ret;
	}

	public static AccountProfile copyPassport(User po) {
		AccountProfile passport = new AccountProfile(po.getId(), po.getUsername());
		passport.setName(po.getName());
		passport.setAvatar(po.getAvatar());
		passport.setLastLogin(po.getLastLogin());
		passport.setStatus(po.getStatus());
		return passport;
	}

	public static CommentVO copy(Comment po) {
		CommentVO ret = new CommentVO();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

	public static PostVO copy(Post po, int level) {
		PostVO d = new PostVO();
		if (level > 0) {
			BeanUtils.copyProperties(po, d);
		} else {
			BeanUtils.copyProperties(po, d, POST_IGNORE_LIST);
		}
		return d;
	}

	public static FeedsVO copy(Feeds po) {
		FeedsVO ret = new FeedsVO();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

	public static NotifyVO copy(Notify po) {
		NotifyVO ret = new NotifyVO();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

	public static FavorVO copy(Favor po) {
		FavorVO ret = new FavorVO();
		BeanUtils.copyProperties(po, ret);
		return ret;
	}

}
