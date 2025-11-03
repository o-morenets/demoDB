-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS high_load;

-- Create table only if it doesn't exist
CREATE TABLE IF NOT EXISTS high_load.documents
(
    id SERIAL PRIMARY KEY,
    content TEXT NOT NULL
);

-- Insert data only if table is empty
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM high_load.documents) THEN
            INSERT INTO high_load.documents (content)
            SELECT repeat(md5(random()::text), 20)
            FROM generate_series(1, 10_000_000);
        END IF;
    END $$;
