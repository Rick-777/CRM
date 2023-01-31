package com.lib.sys.model;

import com.lib.common.model.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SysLogQueryDTO extends PageDTO {
    @ApiModelProperty("查询字段")
    private String msg;
}
