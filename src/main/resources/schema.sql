CREATE TABLE IF NOT EXISTS anime (
    id UUID PRIMARY KEY,
    title VARCHAR(100),
    image_url VARCHAR(255),
    synopsis TEXT,
    episodes INT,
    url VARCHAR(255)
);