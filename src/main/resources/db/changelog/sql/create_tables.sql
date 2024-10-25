-- Create a user table
CREATE TABLE users (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL
);

-- Creating a reminder table
CREATE TABLE reminders (
                           id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           title VARCHAR(255) NOT NULL,
                           description TEXT,
                           remind TIMESTAMP WITH TIME ZONE NOT NULL,
                           user_id BIGINT NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create an index to speed up queries by user
CREATE INDEX idx_reminders_user_id ON reminders(user_id);
