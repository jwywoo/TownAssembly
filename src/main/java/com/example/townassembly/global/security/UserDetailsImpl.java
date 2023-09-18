package com.example.townassembly.global.security;


import com.example.townassembly.domain.user.entity.UserRoleEnum;
import com.example.townassembly.domain.user.entity.VoterUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
//    private final User user;
    private final VoterUser voterUser;

    public UserDetailsImpl(VoterUser voterUser) { this.voterUser = voterUser; }

    public VoterUser getVoterUser() {return voterUser;}

    @Override
    public String getPassword() { return voterUser.getPassword();}

    @Override
    public String getUsername() { return voterUser.getUsername();}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRoleEnum role = voterUser.getRole();
        String authority = role.getAuthority();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}