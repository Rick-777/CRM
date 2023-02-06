package com.lib.sys.mapper;

import com.lib.sys.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    int canBeDeleted(Long menuId);
}
