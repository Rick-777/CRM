package com.lib.sys.controller;

import com.lib.common.constant.SystemConstant;
import com.lib.common.execption.BizException;
import com.lib.common.result.IgnoredResultWrapper;
import com.lib.common.result.ResultWrapper;
import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysUser;
import com.lib.sys.model.SysUserQueryDTO;
import com.lib.sys.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author 波哥
 * @since 2022-11-18
 */
@Api(tags = "系统用户",value = "SysUser")
@RestController
@RequestMapping("/sys/sysUser")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @ApiOperation(value = "查询系统用户",notes = "查询用户")
    @GetMapping("/list")
    public PageUtils list(SysUserQueryDTO dto){
        return userService.queryPage(dto);
    }

    @ApiOperation(value = "Check if username exists",notes = "Check username")
    @GetMapping("/checkUserName")
    public String checkUserName(String username){
        // flag == true username exists
        boolean flag = userService.checkUserName(username);
        return flag? SystemConstant.CHECK_SUCCESS :SystemConstant.CHECK_FAIL;
    }

    @ApiOperation(value = "Save Account",notes = "Save Account")
    @PostMapping("/save")
    public String save(@RequestBody SysUser sysUser){
        userService.saveOrUpdateUser(sysUser);
        return "success";
    }
}
