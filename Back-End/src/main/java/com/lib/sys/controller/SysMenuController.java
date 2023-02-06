package com.lib.sys.controller;

import com.lib.common.util.PageUtils;
import com.lib.sys.model.SysMenuQueryDTO;
import com.lib.sys.service.ISysMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
@Controller
@RequestMapping("/sys/sysMenu")
public class SysMenuController {
    @Autowired
    private ISysMenuService menuService;
    @ApiOperation(value = "Query Menu Info",notes = "Query Menu Info")
    @GetMapping("/list")
    public PageUtils list(SysMenuQueryDTO dto){
        return menuService.listPage(dto);
    }
}
