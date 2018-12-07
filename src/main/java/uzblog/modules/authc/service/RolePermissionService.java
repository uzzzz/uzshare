package uzblog.modules.authc.service;

import java.util.List;
import java.util.Set;

import uzblog.modules.authc.entity.Permission;
import uzblog.modules.authc.entity.RolePermission;

/**
 * @author - langhsu
 * @create - 2018/5/18
 */
public interface RolePermissionService {
    List<Permission> findPermissions(long roleId);
    void deleteByRoleId(long roleId);
    void add(Set<RolePermission> rolePermissions);

}
