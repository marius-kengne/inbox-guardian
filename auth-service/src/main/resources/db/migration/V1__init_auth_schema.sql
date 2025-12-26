-- Extensions utiles (optionnel)
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1) users
CREATE TABLE IF NOT EXISTS users (
    id              BIGSERIAL PRIMARY KEY,
    email           VARCHAR(255) NOT NULL UNIQUE,
    password_hash   VARCHAR(255) NOT NULL,
    role            VARCHAR(50)  NOT NULL DEFAULT 'USER',
    is_enabled      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

-- 2) subscriptions (Stripe)
CREATE TABLE IF NOT EXISTS subscriptions (
    id                      BIGSERIAL PRIMARY KEY,
    user_id                 BIGINT NOT NULL UNIQUE,
    plan                    VARCHAR(50) NOT NULL DEFAULT 'FREE',
    status                  VARCHAR(50) NOT NULL DEFAULT 'INACTIVE',
    stripe_customer_id      VARCHAR(255),
    stripe_subscription_id  VARCHAR(255),
    current_period_start    TIMESTAMPTZ,
    current_period_end      TIMESTAMPTZ,
    created_at              TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_sub_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_subscriptions_customer ON subscriptions(stripe_customer_id);
CREATE INDEX IF NOT EXISTS idx_subscriptions_subscription ON subscriptions(stripe_subscription_id);

-- 3) user_limits (quotas)
CREATE TABLE IF NOT EXISTS user_limits (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT NOT NULL UNIQUE,
    daily_email_limit   INT NOT NULL DEFAULT 3,
    daily_sms_limit     INT NOT NULL DEFAULT 3,
    daily_url_limit     INT NOT NULL DEFAULT 3,
    used_email_today    INT NOT NULL DEFAULT 0,
    used_sms_today      INT NOT NULL DEFAULT 0,
    used_url_today      INT NOT NULL DEFAULT 0,
    last_reset_date     DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_limits_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4) refresh_tokens (pour JWT refresh)
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    token       VARCHAR(512) NOT NULL UNIQUE,
    expires_at  TIMESTAMPTZ NOT NULL,
    revoked     BOOLEAN NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_refresh_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_refresh_tokens_user ON refresh_tokens(user_id);

-- 5) Table pour suivre les migrations (Flyway la crée souvent tout seul)
-- Flyway créera flyway_schema_history automatiquement.
