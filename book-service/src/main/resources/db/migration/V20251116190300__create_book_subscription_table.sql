CREATE TABLE IF NOT EXISTS public.book_subscription
(
    book_id         UUID NOT NULL REFERENCES book(id),
    subscription_id UUID NOT NULL REFERENCES subscription(id),
    sent_at         TIMESTAMP DEFAULT now(),
    PRIMARY KEY (book_id, subscription_id)
)