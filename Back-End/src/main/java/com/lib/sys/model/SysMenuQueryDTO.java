package com.lib.sys.model;

import com.lib.common.model.PageDTO;
import io.swagger.annotations.ApiModelProperty;

public class SysMenuQueryDTO extends PageDTO {
    @ApiModelProperty("Menu Description")
    private String label;


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
