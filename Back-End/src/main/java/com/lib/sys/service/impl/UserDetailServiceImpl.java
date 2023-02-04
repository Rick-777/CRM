package com.lib.sys.service.impl;

import com.lib.sys.entity.SysRole;
import com.lib.sys.entity.SysUser;
import com.lib.sys.service.ISysRoleService;
import com.lib.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Verify service
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysRoleService sysRoleService;

    /**
     * Complete verification of account
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Verify according to account
        List<SysUser> list = sysUserService.queryByUserName(username);
        if(list != null && list.size() == 1){
            // Account exists
            SysUser sysUser = list.get(0);
            // According to current login account, check relevant role
            List<GrantedAuthority> listRole = new ArrayList<>();
            // Bind role to current user
            listRole.add(new SimpleGrantedAuthority("ADMIN"));
            return new User(sysUser.getUsername(), sysUser.getPassword(), listRole);
        }
//        if("admin".equals(username)){
//            List<GrantedAuthority> list = new ArrayList<>();
//            // Bind role to current user
//            list.add(new SimpleGrantedAuthority("ADMIN"));
//
//            UserDetails userDetails = new User("admin",
//                    "$2a$10$XzC8eJauN8Zc6bVPuouU5e7fOLLCx2sQ7VKJLdUsdYOVW3G1tN8ja",
//                    list);
//            return userDetails;
//        }
        return null;
    }
}
