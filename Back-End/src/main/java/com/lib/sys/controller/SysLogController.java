package com.lib.sys.controller;

import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysLog;
import com.lib.sys.model.SysLogQueryDTO;
import com.lib.sys.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author 波哥
 * @since 2022-11-27
 */

@CrossOrigin
@Controller
@RequestMapping("/sys/sysLog")
public class SysLogController {

    @Autowired
    private ISysLogService sysLogService;

    @GetMapping("/list")
    public PageUtils list(SysLogQueryDTO dto){
        PageUtils pageUtils = sysLogService.listPage(dto);
        return pageUtils;
    }
}
