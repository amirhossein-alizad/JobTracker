CREATE TABLE applications (
                              id BIGSERIAL PRIMARY KEY,
                              company VARCHAR(120) NOT NULL,
                              role_title VARCHAR(160) NOT NULL,
                              location VARCHAR(160),
                              status VARCHAR(40) NOT NULL,
                              source VARCHAR(60),
                              applied_date DATE,
                              job_url TEXT,
                              salary_min INTEGER,
                              salary_max INTEGER,
                              created_at TIMESTAMPTZ NOT NULL,
                              updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_applications_status ON applications(status);
CREATE INDEX idx_applications_company ON applications(company);
CREATE INDEX idx_applications_applied_date ON applications(applied_date);