package br.com.raac_system.service;

import br.com.raac_system.dto.request.LoginRequest;
import br.com.raac_system.dto.response.LoginResponse;
import br.com.raac_system.entity.User;
import br.com.raac_system.repository.UserRepository;
import br.com.raac_system.security.CustomUserDetails;
import br.com.raac_system.security.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final UserRepository userRepository;
        private final StringRedisTemplate redisTemplate;

        @Value("${jwt.refreshExpirationMs}")
        private long refreshExpirationMs;

        public AuthService(AuthenticationManager authenticationManager, JwtService jwtService,
                        UserRepository userRepository, StringRedisTemplate redisTemplate) {
                this.authenticationManager = authenticationManager;
                this.jwtService = jwtService;
                this.userRepository = userRepository;
                this.redisTemplate = redisTemplate;
        }

        public LoginResponse authenticate(LoginRequest request) {
                Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

                User user = userRepository.findById(userDetails.getId())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                String accessToken = jwtService.generateToken(userDetails, user.getTenant().getId().toString(),
                                user.getRole().name());
                String refreshToken = UUID.randomUUID().toString();

                redisTemplate.opsForValue().set(
                                "refresh:" + user.getId().toString(),
                                refreshToken,
                                Duration.ofMillis(refreshExpirationMs));

                return LoginResponse.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .user(LoginResponse.UserDto.fromEntity(user))
                                .build();
        }

        public void logout(String userId) {
                redisTemplate.delete("refresh:" + userId);
        }
}
