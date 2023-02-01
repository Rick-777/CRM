package com.lib.sys.controller;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
@CrossOrigin
@RestController
@RequestMapping("/sys/sysUser")
public class SysUserController {

    @Autowired
    private ISysUserService userService;

    @ApiOperation(value = "查询系统用户",notes = "查询用户")
    @GetMapping("/list")
    public List<SysUser> list(){
        return userService.list();
    }

    @IgnoredResultWrapper
    @ApiOperation(value = "查询系统用户1",notes = "查询用户1")
    @GetMapping("/list1")
    public List<SysUser> list1(){
        return userService.list();
    }

    @ApiOperation(value = "查询系统用户2",notes = "查询用户2")
    @GetMapping("/list2")
    public ResultWrapper list2(){
        ResultWrapper wrapper = new ResultWrapper();
        wrapper.setCode("200");
        wrapper.setData(userService.list());
        wrapper.setMessage("Self define Result Wrapper");
        return wrapper;
    }
}
