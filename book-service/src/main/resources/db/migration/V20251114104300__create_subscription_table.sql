CREATE TABLE IF NOT EXISTS public.subscription
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    email           TEXT NOT NULL,
    author          TEXT,
    genre           TEXT,
    rating_avg      NUMERIC(9,2),
    type            TEXT NOT NULL,
    CONSTRAINT unique_subscription UNIQUE (author, genre, rating_avg, type)
)