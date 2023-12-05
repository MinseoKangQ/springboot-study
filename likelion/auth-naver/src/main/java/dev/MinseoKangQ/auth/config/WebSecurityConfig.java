package dev.MinseoKangQ.auth.config;

import dev.MinseoKangQ.auth.infra.CustomUserDetailsService;
import dev.MinseoKangQ.auth.infra.NaverOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity // security 를 custom 할 준비가 됨
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final NaverOAuth2Service naverOAuth2Service;

    public WebSecurityConfig(@Autowired CustomUserDetailsService customUserDetailsService,
                             @Autowired NaverOAuth2Service naverOAuth2Service) {
        this.userDetailsService = customUserDetailsService;
        this.naverOAuth2Service = naverOAuth2Service;
    }

    // 사용자 관리
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                    .authorizeRequests()
                    .antMatchers("/home/**", "/user/signup/**")
                    .anonymous()
                    .anyRequest() // 나머지 URL에 대해 설정하는 것이므로 항상 마지막에 작성
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/home")
                    .permitAll()
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                        .userService(this.naverOAuth2Service)
                    .and()
                        .defaultSuccessUrl("/home")
                .and()
                    .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/home")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll();
    }

}
