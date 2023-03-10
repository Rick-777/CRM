package com.lib.sys.service;

import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.sys.model.SysRoleQueryDTO;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
public interface ISysRoleService extends IService<SysRole> {

    PageUtils queryPage(SysRoleQueryDTO queryDTO);

    void saveOrUpdateRole(SysRole role);

    void update(SysRole role);

    void deleteBatch(Long[] roleIds);

    boolean checkRoleName(String roleName);

    boolean deleteRoleById(Long roleId);

    List<SysRole> queryByUserId(Long userId);
}
