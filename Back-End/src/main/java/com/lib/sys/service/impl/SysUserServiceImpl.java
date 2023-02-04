package com.lib.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lib.common.constant.SystemConstant;
import com.lib.sys.entity.SysUser;
import com.lib.sys.mapper.SysUserMapper;
import com.lib.sys.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;

import javax.management.Query;
import java.util.List;

public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService{
    @Override
    public List<SysUser> queryByUserName(String username){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(username),"username",username);
        wrapper.eq("status", SystemConstant.USER_STATUS_NORMAL);
        return this.baseMapper.selectList(wrapper);
    }
}
