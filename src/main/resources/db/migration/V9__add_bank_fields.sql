-- Add bank fields to doctors
ALTER TABLE doctors ADD COLUMN IF NOT EXISTS bank_code VARCHAR(20);
ALTER TABLE doctors ADD COLUMN IF NOT EXISTS bank_agency VARCHAR(20);
ALTER TABLE doctors ADD COLUMN IF NOT EXISTS bank_account VARCHAR(20);
