package com.example.townassembly.global.security;

import com.example.townassembly.domain.user.entity.VoterUser;
import com.example.townassembly.domain.user.repository.VoterUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final VoterUserRepository voterUserRepository;

    @Override
    public UserDetails loadUserByUsername(String voterId) throws UsernameNotFoundException {
        VoterUser voterUser = voterUserRepository.findById(voterId)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + voterId));

        return new UserDetailsImpl(voterUser);
    }
}