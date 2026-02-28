CREATE TABLE doctors (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    user_id UUID REFERENCES users(id),
    name VARCHAR(255) NOT NULL,
    crm VARCHAR(50) NOT NULL,
    uf_crm VARCHAR(2) NOT NULL,
    rqe VARCHAR(50),
    specialty VARCHAR(100),
    signature_url VARCHAR(255),
    bank_account_encrypted TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE TABLE patients (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(14),
    cns VARCHAR(50),
    birth_date DATE,
    sex VARCHAR(20),
    gender VARCHAR(50),
    marital_status VARCHAR(50),
    phone VARCHAR(20),
    email VARCHAR(255),
    address_street VARCHAR(255),
    address_number VARCHAR(20),
    address_complement VARCHAR(100),
    address_neighborhood VARCHAR(100),
    address_city VARCHAR(100),
    address_state VARCHAR(2),
    address_zip_code VARCHAR(20),
    allergies TEXT,
    blood_type VARCHAR(5),
    prontuario_number VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP,
    CONSTRAINT unique_prontuario_per_tenant UNIQUE (tenant_id, prontuario_number)
);

CREATE TABLE patient_insurances (
    id UUID PRIMARY KEY,
    tenant_id UUID NOT NULL REFERENCES tenants(id),
    patient_id UUID NOT NULL REFERENCES patients(id),
    operator_name VARCHAR(255) NOT NULL,
    card_number VARCHAR(100) NOT NULL,
    plan VARCHAR(100),
    validity DATE,
    accommodation VARCHAR(50),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);
