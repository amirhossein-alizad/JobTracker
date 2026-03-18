ALTER TABLE applications
    ADD COLUMN username VARCHAR(100);

ALTER TABLE applications
    ADD CONSTRAINT fk_applications_user
        FOREIGN KEY (username) REFERENCES users (username);