package com.lib.sys.service;

import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.sys.model.SysMenuQueryDTO;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
public interface ISysMenuService extends IService<SysMenu> {

    PageUtils listPage(SysMenuQueryDTO dto);

    List<SysMenu> listParent();
}
