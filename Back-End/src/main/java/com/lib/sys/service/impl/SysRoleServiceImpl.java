package com.lib.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.common.annotation.SystemLog;
import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysRole;
import com.lib.sys.mapper.SysRoleMapper;
import com.lib.sys.model.SysRoleQueryDTO;
import com.lib.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageUtils queryPage(SysRoleQueryDTO queryDTO) {
        QueryWrapper<SysRole> wrapper = new QueryWrapper<SysRole>().like(
                StringUtils.isNotEmpty(queryDTO.getRoleName()),"role_name",queryDTO.getRoleName()
        );
        Page<SysRole> page = this.page(queryDTO.page(), wrapper);

        return new PageUtils(page);
    }

    @SystemLog("Save Or Update Role")
    @Override
    public void saveOrUpdateRole(SysRole role) {
        // Determine if roleId exists, update if exists, else save
        if(role.getRoleId() != null && role.getRoleId() != 0){
            // 表示更新操作
            this.update(role);

        }else{
            this.saveRole(role);
        }
    }

    public void saveRole(SysRole role){
        role.setCreateTime(LocalDateTime.now());
        this.save(role);
    }

    @Override
    public void update(SysRole role) {
        this.baseMapper.updateById(role);
    }

    @Override
    public void deleteBatch(Long[] roleIds) {

    }

    /**
     *
     * @param roleName
     * @return
     *    true exist
     *    false no exist
     */
    @Override
    public boolean checkRoleName(String roleName) {
        if(StringUtils.isEmpty(roleName)){
            return false;
        }
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>().eq("role_name",roleName);
        int count = this.count(queryWrapper);
        return count > 0;
    }

    @SystemLog("Delete Role")
    @Override
    public boolean deleteRoleById(Long roleId) {
        // Delete role info
        // If this role is allocated to user, or it binds to a menu, it is not allowed to delete the role
        // Check If this role is allocated to user
        int count = this.baseMapper.checkRoleCanDelete(roleId);
        if(count == 0){
            // Is ok to delete
            this.baseMapper.deleteById(roleId);
        }
        return count == 0;
    }

    @Override
    public List<SysRole> queryByUserId(Long userId) {

        return sysRoleMapper.queryByUserId(userId);
    }
}
