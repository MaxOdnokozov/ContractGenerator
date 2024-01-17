package com.maksym.odnokozov.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (auth) ->
                auth.requestMatchers("/", "/welcome", "/perform_logout").permitAll()
                    .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                    .requestMatchers("/login").anonymous()
                    .requestMatchers("/admin/organizations").hasRole("ADMIN")
                    .anyRequest().authenticated())
        .formLogin((form) -> form.loginPage("/login").successHandler(successHandler()).permitAll())
        .logout(
            logout ->
                logout
                    .logoutUrl("/perform_logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .permitAll());

    return http.build();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new SimpleUrlAuthenticationSuccessHandler() {
      @Override
      public void onAuthenticationSuccess(
          HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_TEMPLATE_MANAGER"))) {
          setDefaultTargetUrl("/template-manager/organizations");
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
          setDefaultTargetUrl("/admin/template-managers");
        } else {
          setDefaultTargetUrl("/welcome");
        }
        super.onAuthenticationSuccess(request, response, authentication);
      }
    };
  }
}
