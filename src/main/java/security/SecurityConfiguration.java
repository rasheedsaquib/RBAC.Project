package security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import filter.CustomAuthentiacationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;




@Configuration @EnableWebSecurity 
public class SecurityConfiguration{
	
	private final UserDetailsService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public SecurityConfiguration(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception  {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        CustomAuthentiacationFilter customAuthentiacationFilter = new CustomAuthentiacationFilter(authenticationManager);
        customAuthentiacationFilter.setFilterProcessesUrl("/api/login");

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/login/**").permitAll() 
                .requestMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER") 
                .requestMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN") 
                .anyRequest().authenticated()
            )
            .addFilterBefore(customAuthentiacationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
    
}

/*public final UserDetailsService userDetailsService;

public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())

        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/login").  
            .anyRequest().authenticated()          
        )
        
        .formLogin(form -> form
            .loginPage("/login") 
            .permitAll()       
        );  
    http.addFilter((Filter) authenticationManager(null));

    return http.build();
}

public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}


@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception  {
	return authenticationConfiguration.getAuthenticationManager();
}
*/


