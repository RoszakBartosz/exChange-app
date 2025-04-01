CREATE TABLE exchangerate(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    currency VARCHAR(255),
    code VARCHAR(4),
    mid DECIMAL
    );

CREATE TABLE exchangehistorylog(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    from_amount_operation DECIMAL,
    to_amount_operation DECIMAL,
    mid_in_these_time_from DECIMAL,
    mid_in_these_time_to DECIMAL,
    chosen_currency_from VARCHAR(255),
    chosen_currency_to VARCHAR(255),
    date_time_from_operation TIMESTAMP
);


