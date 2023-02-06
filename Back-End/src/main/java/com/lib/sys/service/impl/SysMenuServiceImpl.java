package com.lib.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lib.common.annotation.SystemLog;
import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysMenu;
import com.lib.sys.mapper.SysMenuMapper;
import com.lib.sys.model.SysMenuQueryDTO;
import com.lib.sys.service.ISysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;
    /**
     * Query Every Menu Info
     * @param dto
     * @return
     */
    @Override
    public PageUtils listPage(SysMenuQueryDTO dto) {
        // Query data of every first level menu, page is for these first level menus
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0)
                .like(StringUtils.isNotBlank(dto.getLabel()),"label",dto.getLabel())
                .orderByAsc("order_num");// Query every first level menu
        Page<SysMenu> page = this.page(dto.page(), wrapper);
        // Query second level of this first level
        List<SysMenu> list = page.getRecords();
        List<SysMenu> menus = list.stream().map(item -> {
            // Has children menu
            item.setHasChildren(true);

            Long menuId = item.getMenuId();
            // Query all second level menu by menuId
            QueryWrapper<SysMenu> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id",menuId).orderByAsc("order_num");;
            List<SysMenu> subMenus = this.baseMapper.selectList(wrapper1);
            item.setChildren(subMenus);
            return item;
        }).collect(Collectors.toList());
        page.setRecords(menus);
        return new PageUtils(page);
    }

    @Override
    public List<SysMenu> listParent() {
        List<SysMenu> list = this.baseMapper.selectList(new QueryWrapper<SysMenu>().eq("parent_id", 0));

        return list;

    }

    @SystemLog("Menu Update/Save")
    @Override
    public void saveOrUpdateMenu(SysMenu menu) {
        if(menu.getMenuId()>0){
            // Update
            this.updateById(menu);
        }else{
            if(StringUtils.isNotBlank(menu.getLabel()) && StringUtils.isBlank(menu.getName()))
                menu.setLabel(menu.getLabel());
            if(menu.getParentId() == null) menu.setParentId(0l);
            // Save
            this.save(menu);
        }
    }

    @Override
    public SysMenu queryMenuById(Long menuId) {
        return this.baseMapper.selectById(menuId);
    }

    @SystemLog("Delete Menu")
    @Override
    public String deleteMenuById(Long menuId) {
        // Determine if data can be deleted
        int count = menuMapper.canBeDeleted(menuId);
        if(count == 0){
            // Can delete
            this.baseMapper.deleteById(menuId);
            return "1";
        }
        return "0";
    }
}
