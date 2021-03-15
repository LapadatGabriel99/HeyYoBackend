package com.chat.HeyYo.security;

import com.chat.HeyYo.exception.AuthenticationEntryPointJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.chat.HeyYo.security.SecurityConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter{

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPointJwt authenticationEntryPointJwt;

    @Autowired
    private JwtTokenVerifier jwtTokenVerifier;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v*/user/login").permitAll()
                .antMatchers("/api/v*/user/register").permitAll()
                .antMatchers("/api/v*/user/confirm").permitAll()
                .antMatchers("/api/v*/user/forgot-password").permitAll()
                .antMatchers("/api/v*/user/reset-password").permitAll()
                .antMatchers("/api/v*/user/update-password").permitAll()
                .antMatchers("/api/v*/test/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {

        super.configure(web);
        web.ignoring().antMatchers(HttpMethod.POST,FORGOT_PASSWORD_URL);
        web.ignoring().antMatchers(HttpMethod.POST,LOGIN_URL);
        web.ignoring().antMatchers(HttpMethod.POST,REGISTER_URL);
        web.ignoring().antMatchers(HttpMethod.GET,MAIL_CONFIRMATION_URL);
        web.ignoring().antMatchers(HttpMethod.GET,RESET_PASSWORD_URL);
        web.ignoring().antMatchers(HttpMethod.POST,UPDATE_PASSWORD_URL);
        web.ignoring().antMatchers( "/api/v*/test/**");
        web.ignoring().antMatchers("/error/**");
    }
}
