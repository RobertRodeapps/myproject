package com.problem1.myproject.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/loginuser");
        // We don't need CSRF for this example
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.authorizeRequests().antMatchers( "/loginuser","/register","/menu/refreshtoken").permitAll();
        http.authorizeRequests().antMatchers(GET, "/menu/users/**").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().antMatchers(DELETE, "/menu/users/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/menu/users/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/menu/users/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/menu/coins/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST, "/menu/coins/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/menu/coins/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/menu/coins/**").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().anyRequest().authenticated();
        // dont authenticate this particular request
               /* .authorizeRequests().antMatchers("/authenticate", "/register").permitAll()
                 // all other requests need to be authenticated
                        anyRequest().authenticated().and().
                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
*/
        // Add a filter to validate the tokens with every request
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
