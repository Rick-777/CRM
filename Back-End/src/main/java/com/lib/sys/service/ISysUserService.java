package com.lib.sys.service;

import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.sys.model.SysUserQueryDTO;

import java.util.List;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> queryByUserName(String username);

    PageUtils queryPage(SysUserQueryDTO dto);
}
