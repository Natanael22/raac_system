CREATE TABLE insurance_configs (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    operator_name VARCHAR(255) NOT NULL,
    ans_code VARCHAR(50),
    ws_url VARCHAR(255),
    environment VARCHAR(20) NOT NULL,
    credentials_encrypted TEXT,
    tiss_version VARCHAR(20),
    cert_path VARCHAR(255),
    cert_password_encrypted TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    patient_id UUID NOT NULL REFERENCES patients(id),
    doctor_id UUID NOT NULL REFERENCES doctors(id),
    insurance_id UUID REFERENCES patient_insurances(id),
    type VARCHAR(50) NOT NULL,
    start_dt TIMESTAMP NOT NULL,
    end_dt TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    room VARCHAR(50),
    origin VARCHAR(50),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);
