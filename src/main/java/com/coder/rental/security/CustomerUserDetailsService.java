package com.coder.rental.security;

import com.coder.rental.entity.Permission;
import com.coder.rental.entity.User;
import com.coder.rental.service.IPermissionService;
import com.coder.rental.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Component
public class CustomerUserDetailsService implements UserDetailsService {
    @Resource
    private IUserService userService;

    @Resource
    private IPermissionService permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<Permission> permissions = permissionService.selectPermissionListByUserId(user.getId());
        user.setPermissionList(permissions);
        String[] array = permissions.stream().filter(Objects::nonNull).map(Permission::getPermissionCode).filter(Objects::nonNull).toArray(String[]::new);
        List<GrantedAuthority> authorities= AuthorityUtils.createAuthorityList(array);
        user.setAuthorities(authorities);
        System.out.println(user.toString());
        return user;
    }
}
