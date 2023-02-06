package com.lib.sys.model;

import com.lib.sys.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuUpdateDTO {
    private List<SysMenu> parent;
    private SysMenu menu;

}
