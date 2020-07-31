package kr.co.fastcampus.eatgo;

import kr.co.fastcampus.eatgo.filters.JwtAuthenticationFilter;
import kr.co.fastcampus.eatgo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
    //private Logger logger = LoggerFactory.getLogger(SecurityJavaConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Filter filter = new JwtAuthenticationFilter(authenticationManager(), jwtUtil());

        http
            .cors().disable()
            .csrf().disable()
            .formLogin().disable()
            .headers().frameOptions().disable()
            .and()
            .addFilter(filter)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Value("${jwt.secret-key}")
    private String secret;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtUtil jwtUtil(){
        //logger.info("secret===>"+ secret);
        return new JwtUtil(secret);
    }
}
