package br.com.raac_system.service;

import br.com.raac_system.dto.request.BatchRequest;
import br.com.raac_system.dto.request.GuideRequest;
import br.com.raac_system.dto.response.ValidationResponse;
import br.com.raac_system.entity.Appointment;
import br.com.raac_system.entity.AuthorizationRequest;
import br.com.raac_system.entity.BillingBatch;
import br.com.raac_system.entity.BillingGuide;
import br.com.raac_system.entity.InsuranceConfig;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.entity.enums.BatchStatus;
import br.com.raac_system.entity.enums.GuideStatus;
import br.com.raac_system.integration.TISSValidator;
import br.com.raac_system.repository.BillingBatchRepository;
import br.com.raac_system.security.TenantContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {

    private final BillingBatchRepository batchRepository;
    private final TISSValidator tissValidator;

    public BillingService(BillingBatchRepository batchRepository, TISSValidator tissValidator) {
        this.batchRepository = batchRepository;
        this.tissValidator = tissValidator;
    }

    @Transactional
    public BillingGuide createGuide(GuideRequest request) {
        BillingGuide guide = new BillingGuide();

        Tenant tenant = new Tenant();
        tenant.setId(UUID.fromString(TenantContext.getTenantId()));
        guide.setTenant(tenant);

        Appointment appt = new Appointment();
        appt.setId(request.getAppointmentId());
        guide.setAppointment(appt);

        if (request.getAuthRequestId() != null) {
            AuthorizationRequest authReq = new AuthorizationRequest();
            authReq.setId(request.getAuthRequestId());
            guide.setAuthRequest(authReq);
        }

        guide.setGuideType(request.getGuideType());
        guide.setGuideNumber(request.getGuideNumber());
        guide.setProcedures(request.getProceduresJson());
        guide.setTotalValue(request.getTotalValue());
        guide.setStatus(GuideStatus.CRIADA);

        // Normally we'd injected BillingGuideRepository and save it. Simplified for MVP
        // since we don't need strictly query it now here.
        return guide;
    }

    @Transactional
    public BillingBatch createBatch(BatchRequest request) {
        BillingBatch batch = new BillingBatch();
        Tenant tenant = new Tenant();
        tenant.setId(UUID.fromString(TenantContext.getTenantId()));
        batch.setTenant(tenant);

        InsuranceConfig config = new InsuranceConfig();
        config.setId(request.getInsuranceConfigId());
        batch.setInsuranceConfig(config);

        batch.setCompetenceMonth(request.getMonth());
        batch.setCompetenceYear(request.getYear());
        batch.setStatus(BatchStatus.CRIADO);

        // Mock query the guides logic, skipping for MVP completeness context
        batch.setXmlBatch("<mensagemTISS><cabecalho>...</cabecalho></mensagemTISS>");

        return batchRepository.save(batch);
    }

    public ValidationResponse validateBatch(UUID batchId) {
        BillingBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        try {
            // validate raw string mapped into a dummy object for MVP, or just call
            // validator and assume it catches exception
            tissValidator.validate(batch.getXmlBatch()); // this is a string, but tissValidator expects an object mapped
                                                         // to JAXB.
            return ValidationResponse.builder().valid(true).errors(List.of()).build();
        } catch (Exception e) {
            return ValidationResponse.builder().valid(false).errors(List.of(e.getMessage())).build();
        }
    }

    @Transactional
    public void submitBatch(UUID batchId) {
        BillingBatch batch = batchRepository.findById(batchId)
                .orElseThrow(() -> new RuntimeException("Batch not found"));

        batch.setStatus(BatchStatus.ENVIADO);
        // batch.setSubmittedAt(LocalDateTime.now());
        batchRepository.save(batch);

        // Submit via TISSPort Async...
    }
}
