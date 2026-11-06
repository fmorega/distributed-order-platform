CREATE TABLE order_sagas (
    id             UUID PRIMARY KEY,
    order_id       UUID NOT NULL UNIQUE,
    status         VARCHAR(32) NOT NULL,
    failure_reason TEXT,
    created_at     TIMESTAMPTZ NOT NULL,
    updated_at     TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_order_sagas_order
        FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE outbox_messages (
    id             UUID PRIMARY KEY,
    aggregate_id   UUID NOT NULL,
    saga_id        UUID,
    correlation_id UUID NOT NULL,
    causation_id   UUID,
    message_type   VARCHAR(100) NOT NULL,
    destination    VARCHAR(100) NOT NULL,
    message_key    VARCHAR(100) NOT NULL,
    version        INTEGER NOT NULL DEFAULT 1,
    payload        JSONB NOT NULL,
    created_at     TIMESTAMPTZ NOT NULL,
    published_at   TIMESTAMPTZ,
    attempts       INTEGER NOT NULL DEFAULT 0,

    CONSTRAINT chk_outbox_version CHECK (version > 0),
    CONSTRAINT chk_outbox_attempts CHECK (attempts >= 0)
);

CREATE INDEX idx_outbox_pending
    ON outbox_messages (created_at, id)
    WHERE published_at IS NULL;
