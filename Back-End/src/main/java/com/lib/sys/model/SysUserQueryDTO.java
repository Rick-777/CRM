package com.lib.sys.model;

import com.lib.common.model.PageDTO;
import com.lib.sys.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserQueryDTO extends PageDTO {
    @ApiModelProperty("username")
    private String username;
}
