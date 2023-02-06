package com.lib.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.annotation.SystemLog;
import com.lib.common.constant.SystemConstant;
import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysUser;
import com.lib.sys.mapper.SysUserMapper;
import com.lib.sys.model.SysUserQueryDTO;
import com.lib.sys.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.management.Query;
import java.time.LocalDateTime;
import java.util.List;

public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService{
    @Override
    public List<SysUser> queryByUserName(String username){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(username),"username",username);
        wrapper.eq("status", SystemConstant.USER_STATUS_NORMAL);
        return this.baseMapper.selectList(wrapper);
    }
    @Override
    public PageUtils queryPage(SysUserQueryDTO dto){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(dto.getUsername()),"username",dto.getUsername());
        Page<SysUser> page = this.page(dto.page(), wrapper);
        return new PageUtils(page);
    }

    @Override
    public boolean checkUserName(String username) {
        List<SysUser> list = baseMapper.selectList(new QueryWrapper<SysUser>().eq("username", username));
        if(list != null && list.size() >0){
            return true; // username exists
        }
        return false;
    }

    @SystemLog("User Save/Update")
    @Override
    public void saveOrUpdateUser(SysUser sysUser) {
        if(sysUser.getUserId()>0){
            // Update
            this.updateById(sysUser);
        }else{
            // Add
            sysUser.setCreateTime(LocalDateTime.now());

            sysUser.setCreateUserId(this.getCurrentUserId());
            // Password needs to be encoded
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(sysUser.getPassword());
            sysUser.setPassword(password);
            this.save(sysUser);
        }
    }

    private List<SysUser> queryUser(SysUser user){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(user.getUsername()),"username",user.getUsername())
                .eq(StringUtils.isNotBlank(user.getEmail()),"email",user.getEmail())
                .eq(StringUtils.isNotBlank(user.getMobile()),"mobile",user.getMobile())
                .eq(user.getUserId() != null && user.getUserId()>0,"user_id",user.getUserId());
        return this.baseMapper.selectList(wrapper);
    }
    @Override
    public SysUser queryByUserId(Long userId) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        List<SysUser> list = this.queryUser(user);
        if (list != null && list.size()>0) {
            SysUser sysUser = list.get(0);
            sysUser.setPassword(null);
            return sysUser;
        }
        return null;
    }

    public Long getCurrentUserId(){
        // Set up account to add data
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String userName = (String) authentication.getPrincipal();
        List<SysUser> list = this.baseMapper.selectList(new QueryWrapper<SysUser>().eq("username", userName));
        if(list != null && list.size() == 1){
            SysUser sysUser = list.get(0);
            return sysUser.getUserId();
        }
        return null;
    }
}
