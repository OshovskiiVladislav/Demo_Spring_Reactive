CREATE TABLE messages
(
    id   BIGSERIAL PRIMARY KEY,
    data VARCHAR(1024)
);

INSERT INTO messages (data) VALUES
('First message'),
('More then one message'),
('Third message'),
('Fourth message'),
('Fifth message');
