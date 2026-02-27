CREATE TABLE authorization_requests (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    patient_id UUID NOT NULL REFERENCES patients(id),
    doctor_id UUID NOT NULL REFERENCES doctors(id),
    insurance_config_id UUID NOT NULL REFERENCES insurance_configs(id),
    procedure_code VARCHAR(50) NOT NULL,
    cid VARCHAR(10),
    xml_sent TEXT,
    xml_response TEXT,
    status VARCHAR(50) NOT NULL,
    auth_number VARCHAR(100),
    protocol VARCHAR(100),
    rejection_reason TEXT,
    sent_at TIMESTAMP,
    responded_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE TABLE billing_guides (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    appointment_id UUID NOT NULL REFERENCES appointments(id),
    auth_request_id UUID REFERENCES authorization_requests(id),
    guide_type VARCHAR(50) NOT NULL,
    guide_number VARCHAR(100),
    procedures JSONB,
    total_value DECIMAL(12,2),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE TABLE billing_batches (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    insurance_config_id UUID NOT NULL REFERENCES insurance_configs(id),
    competence_month INT NOT NULL,
    competence_year INT NOT NULL,
    total_value DECIMAL(12,2),
    xml_batch TEXT,
    status VARCHAR(50) NOT NULL,
    submitted_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE TABLE billing_batch_guides (
    batch_id UUID NOT NULL REFERENCES billing_batches(id),
    guide_id UUID NOT NULL REFERENCES billing_guides(id),
    PRIMARY KEY(batch_id, guide_id)
);

CREATE TABLE audit_logs (
    id UUID PRIMARY KEY,
    user_id UUID,
    action VARCHAR(255) NOT NULL,
    resource_type VARCHAR(100) NOT NULL,
    resource_id VARCHAR(100) NOT NULL,
    payload JSONB,
    ip VARCHAR(50),
    user_agent VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);
