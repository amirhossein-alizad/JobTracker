ALTER TABLE notes
    ADD COLUMN username VARCHAR(100);

ALTER TABLE notes
    ADD CONSTRAINT fk_notes_user
        FOREIGN KEY (username) REFERENCES users (username);