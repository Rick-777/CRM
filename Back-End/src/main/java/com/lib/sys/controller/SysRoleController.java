package com.lib.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lib.common.constant.SystemConstant;
import com.lib.common.model.PageDTO;
import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysRole;
import com.lib.sys.model.SysRoleQueryDTO;
import com.lib.sys.service.ISysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
@Api(tags = "系统角色",value = "SysRole")
@RestController
@RequestMapping("/sys/sysRole")
public class SysRoleController {

    @Autowired
    private ISysRoleService roleService;

    @ApiOperation(value = "查询分页角色",notes = "角色信息")
    @GetMapping("/list")
    public PageUtils list(@ApiParam(value = "查询的条件") SysRoleQueryDTO queryDTO){
        return roleService.queryPage(queryDTO);
    }

    @ApiOperation(value = "Add Role",notes = "Add Role")
    @GetMapping("/save")
    public String save(@RequestBody SysRole sysRole){
        roleService.saveOrUpdateRole(sysRole);

        return "success";
    }

    @GetMapping("/deleteRole")
    public String deleteRole(Long roleId){
        boolean flag = roleService.deleteRoleById(roleId);
        return flag?"1":"0";
    }

    /**
     * Check If Role Name Exists
     * @param roleName
     * @return
     *
     */
    @ApiOperation(value = "Check If Role Name Exists",notes = "Check If Role Name Exists")
    @GetMapping("/checkRoleName")
    public String checkRoleName(String roleName){
        boolean flag = roleService.checkRoleName(roleName);
        return flag? SystemConstant.CHECK_SUCCESS :SystemConstant.CHECK_FAIL;
    }
}
