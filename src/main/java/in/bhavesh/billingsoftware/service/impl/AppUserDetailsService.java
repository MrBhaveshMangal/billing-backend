package in.bhavesh.billingsoftware.service.impl;

import in.bhavesh.billingsoftware.entity.UserEntity;
import in.bhavesh.billingsoftware.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.fasterxml.jackson.databind.type.LogicalType.Collection;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
         UserEntity existingUser=userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Email not found for the email:"+email));
         return new User(existingUser.getEmail(),existingUser.getPassword(), Collections.singleton(new SimpleGrantedAuthority(existingUser.getRole())));

    }
}
