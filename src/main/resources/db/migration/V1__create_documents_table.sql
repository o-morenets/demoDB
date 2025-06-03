CREATE TABLE documents
(
    id      SERIAL PRIMARY KEY,
    content TEXT NOT NULL
);

INSERT INTO documents (content)
SELECT repeat(md5(random()::text), 20)
FROM generate_series(1, 10_000_000);
