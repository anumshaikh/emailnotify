package com.example.emailnotify.appuser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.emailnotify.registration.token.ConfirmationToken;
import com.example.emailnotify.registration.token.ConfirmationTokenService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = " user with email id %s not found";
    private final AppUserRepository appUserRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return appUserRepository.findByEmailid(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser){

        boolean userExists =  appUserRepository.findByEmailid(appUser.getEmailid()).isPresent();
        if(userExists)
        throw new IllegalStateException("Email id already registered :( ");

        String encryptpassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encryptpassword);
        
        appUserRepository.save(appUser);

        //generate token 

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser);
        
            System.out.println(" ********************* Object Created ");

       confirmationTokenService.saveConfirmationToken(confirmationToken);

       //to do send mail
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
