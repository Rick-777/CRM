package com.lib.sys.service;

import com.lib.common.util.PageUtils;
import com.lib.sys.entity.SysLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lib.sys.model.SysLogQueryDTO;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author 波哥
 * @since 2022-11-27
 */
public interface ISysLogService extends IService<SysLog> {

    PageUtils listPage(SysLogQueryDTO dto);
}
