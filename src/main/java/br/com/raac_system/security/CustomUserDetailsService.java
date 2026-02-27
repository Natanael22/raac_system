package br.com.raac_system.security;

import br.com.raac_system.entity.User;
import br.com.raac_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username expected to be Email
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            throw new UsernameNotFoundException("Tenant ID is required to load user");
        }

        User user = userRepository.findByEmailAndTenantId(username, UUID.fromString(tenantId))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with email: " + username + " in tenant " + tenantId));

        return new CustomUserDetails(user);
    }
}
