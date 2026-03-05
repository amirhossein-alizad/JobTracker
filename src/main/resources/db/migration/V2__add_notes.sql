CREATE TABLE notes
(
    id             BIGSERIAL PRIMARY KEY,
    application_id BIGINT        NOT NULL,
    text           VARCHAR(2000) NOT NULL,
    created_at     TIMESTAMP     NOT NULL,
    CONSTRAINT fk_notes_application
        FOREIGN KEY (application_id) REFERENCES applications (id)
            ON DELETE CASCADE
);

CREATE INDEX idx_application_notes_application_id
    ON notes (application_id);