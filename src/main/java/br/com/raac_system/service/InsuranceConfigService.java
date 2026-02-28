package br.com.raac_system.service;

import br.com.raac_system.dto.request.InsuranceConfigRequest;
import br.com.raac_system.dto.response.InsuranceConfigResponse;
import br.com.raac_system.entity.InsuranceConfig;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.exception.BusinessException;
import br.com.raac_system.mapper.InsuranceConfigMapper;
import br.com.raac_system.repository.InsuranceConfigRepository;
import br.com.raac_system.security.TenantContext;
import br.com.raac_system.util.CryptoUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InsuranceConfigService {

    private final InsuranceConfigRepository repository;
    private final InsuranceConfigMapper mapper;

    @Value("${raac.encryption.key}")
    private String encryptionKey;

    public InsuranceConfigService(InsuranceConfigRepository repository, InsuranceConfigMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<InsuranceConfigResponse> listConfigs(Pageable pageable) {
        return repository.findAllByTenantIdAndDeletedAtIsNull(getTenantId(), pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public InsuranceConfigResponse createConfig(InsuranceConfigRequest request) {
        InsuranceConfig config = mapper.toEntity(request);

        Tenant tenantRef = new Tenant();
        tenantRef.setId(getTenantId());
        config.setTenant(tenantRef);

        if (request.getCredentials() != null && !request.getCredentials().isBlank()) {
            config.setCredentialsEncrypted(CryptoUtil.encrypt(request.getCredentials(), encryptionKey));
        }

        if (request.getCertPassword() != null && !request.getCertPassword().isBlank()) {
            config.setCertPasswordEncrypted(CryptoUtil.encrypt(request.getCertPassword(), encryptionKey));
        }

        config = repository.save(config);
        return mapper.toDto(config);
    }

    public InsuranceConfigResponse getConfig(UUID id) {
        InsuranceConfig config = repository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("InsuranceConfig not found"));
        return mapper.toDto(config);
    }

    @Transactional
    public void deleteConfig(UUID id) {
        InsuranceConfig config = repository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("InsuranceConfig not found"));
        config.setDeletedAt(LocalDateTime.now());
        repository.save(config);
    }

    private UUID getTenantId() {
        String tenantIdStr = TenantContext.getTenantId();
        if (tenantIdStr == null || tenantIdStr.isBlank()) {
            throw new BusinessException("Tenant ID is missing in context");
        }
        return UUID.fromString(tenantIdStr);
    }
}
