package com.chat.HeyYo.service;

import com.chat.HeyYo.document.ERole;
import com.chat.HeyYo.document.Role;
import com.chat.HeyYo.document.User;
import com.chat.HeyYo.repository.UserRepository;
import com.chat.HeyYo.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.chat.HeyYo.security.SecurityConstants.*;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    public Optional<User> authenticateUser(User user, HttpServletResponse response) {

        var authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = jwtUtils.generateJwtToken(authentication);

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);
        response.addCookie(createHttpOnlyCookie(HEADER_STRING, jwtToken));

        var userDetails = (UserDetailsImpl)authentication.getPrincipal();

        var userRoles = userDetails.getAuthorities()
                .stream()
                .map(item -> {

                    var role = new Role();

                    switch (item.getAuthority()) {

                        case "ROLE_USER":
                            role.setName(ERole.ROLE_USER);

                            return role;

                        case "ROLE_ADMIN":
                            role.setName(ERole.ROLE_ADMIN);

                            return role;
                    }

                    return null;
                })
                .collect(Collectors.toSet());

        user.setRoles(userRoles);

        user.setEmail(userDetails.getEmail());

        user.setId(userDetails.getId());

        user.setFirstname(userDetails.getFirstname());

        user.setLastname(userDetails.getLastname());

        user.setRegistrationDate(userDetails.getRegistrationDate());

        user.setEnabled(userDetails.isEnabled());

        return Optional.of(user);
    }

    public void removeCurrentJwtTokenAuthentication(HttpServletResponse response) {

        response.addCookie(deleteHttpOnlyCookie(HEADER_STRING));
    }

    @Transactional
    public User getCurrentUser() {
        var principal = (UserDetailsImpl)SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public Cookie createHttpOnlyCookie(String name, String content) {

        var cookie = new Cookie(name, content);
        cookie.setMaxAge(10*24*60*60);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    public Cookie deleteHttpOnlyCookie(String name) {

        var cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}
