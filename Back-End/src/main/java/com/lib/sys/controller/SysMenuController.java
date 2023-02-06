package com.lib.sys.controller;

import com.lib.common.constant.SystemConstant;
import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysMenu;
import com.lib.sys.model.MenuUpdateDTO;
import com.lib.sys.model.SysMenuQueryDTO;
import com.lib.sys.service.ISysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
@RestController
@RequestMapping("/sys/sysMenu")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;
    @ApiOperation(value = "Query Menu Info",notes = "Query Menu Info")
    @GetMapping("/list")
    public PageUtils list(SysMenuQueryDTO dto){
        return menuService.listPage(dto);
    }

    @ApiOperation(value = "Query Parent Menu Info",notes = "Query Parent Menu Info")
    @GetMapping("/listParent")
    public List<SysMenu> listParent(){
        return menuService.listParent();
    }

    @ApiOperation(value = "Save Menu Data",notes = "Save/Update Menu Data")
    @PostMapping("/save")
    public String save(@RequestBody SysMenu menu){
        if(menu != null)
            menuService.saveOrUpdateMenu(menu);
        return SystemConstant.CHECK_SUCCESS;
    }

    @ApiOperation(value = "Query Menu Info by ID",notes = "Query Menu Info by ID")
    @GetMapping("/queryMenuById")
    public MenuUpdateDTO queryMenuById(Long menuId){
        SysMenu sysMenu = menuService.queryMenuById(menuId);
        List<SysMenu> parents = menuService.listParent();
        return new MenuUpdateDTO(parents,sysMenu);
    }

    @ApiOperation(value = "Delete Menu",notes = "Delete Menu")
    @GetMapping("/deleteMenu")
    public String deleteMenu(Long menuId){
        // return 0 if cannot delete
        return menuService.deleteMenuById(menuId);
    }
}
