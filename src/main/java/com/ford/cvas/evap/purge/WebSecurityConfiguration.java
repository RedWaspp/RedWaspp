package com.ford.cvas.evap.purge;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import static org.springframework.security.oauth2.jwt.JwtClaimNames.AUD;
import static org.springframework.security.oauth2.jwt.JwtClaimNames.SUB;


import java.util.Arrays;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@Order(10)
public class WebSecurityConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfiguration.class.getSimpleName());

    public static class ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Value("#{'${audience-id}'}")
        String audience;


        @Qualifier("jwtDecoderByIssuerUri")
        @Autowired
        JwtDecoder jwtDecoder;

        protected void configure(HttpSecurity http) throws Exception {
            JwtDecoder newJwtDecoder = wrapJwtDecoderWithAudienceCheck(this.jwtDecoder, audience);
            http
                    .cors()
                    .and()
                    .authorizeRequests().antMatchers("/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/v2/api-docs").permitAll()
                    .requestMatchers(EndpointRequest.to("info", "health")).permitAll().and()
                    .antMatcher("/api/**").authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .oauth2ResourceServer()
                    .jwt().decoder(newJwtDecoder)
            ;


        }

        static JwtDecoder wrapJwtDecoderWithAudienceCheck(JwtDecoder jwtDecoder, String audience) {

            return (token) -> {
                LOG.info("token = " + token);
                Jwt jwt = jwtDecoder.decode(token);

                if (jwt.containsClaim(AUD) && !jwt.getClaimAsStringList(AUD).contains(audience)) {
                    LOG.info("Failed to authenticate, check for valid claim and that user is authorized");
                    throw new JwtValidationException("Audience field does not match: " + audience, Arrays.asList(new OAuth2Error("invalid_aud")));
                }
                LOG.info("JWT Aud = " + jwt.getClaim(AUD));
                LOG.info("JWT Sub = " + jwt.getClaim(SUB));
                return jwt;
            };
        }
    }

}

