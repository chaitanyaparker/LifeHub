package com.example.LifeHub.Security.Service;

import com.example.LifeHub.Entity.User;
import com.example.LifeHub.Repository.UserRepository;
import com.example.LifeHub.Security.custom.CustomUserDetail;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public @NullMarked UserDetails loadUserByUsername(String email){

        System.out.println("Email received: " + email);

        User user = userRepository.findByEmail(email).orElse(null);

        System.out.println(user);

        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        }

        System.out.println("User Found");
        return new CustomUserDetail(user);

//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email " + email));
//
//        System.out.println("User Found: " + user.getEmail());
//
//        return new CustomUserDetail(user);
    }
}
