DROP TABLE IF EXISTS sequence;

-- 建sequence表，指定seq列为无符号大整型，可支持无符号值：0(default)到18446744073709551615（0到2^64–1）。
CREATE TABLE sequence (
                          name       VARCHAR(50) NOT NULL,
                          current_value   BIGINT UNSIGNED NOT NULL DEFAULT 0,
                          increment     INT NOT NULL DEFAULT 1,
                          PRIMARY KEY (name)  -- 不允许重复seq的存在。
) ENGINE=InnoDB;

set global log_bin_trust_function_creators=1;



DROP FUNCTION IF EXISTS currval;


CREATE FUNCTION currval(seq_name VARCHAR(50)) RETURNS BIGINT BEGIN    DECLARE value BIGINT; SELECT current_value INTO value FROM sequence WHERE upper(name) = upper(seq_name); RETURN value; END;

DROP FUNCTION IF EXISTS nextval;

CREATE FUNCTION nextval (seq_name VARCHAR(50)) RETURNS BIGINT BEGIN   DECLARE value BIGINT; UPDATE sequence SET current_value = current_value + increment WHERE upper(name) = upper(seq_name); RETURN currval(seq_name); END;

DROP FUNCTION IF EXISTS setval;

CREATE FUNCTION setval (seq_name VARCHAR(50), value BIGINT) RETURNS BIGINT BEGIN UPDATE sequence SET current_value = value WHERE upper(name) = upper(seq_name); RETURN currval(seq_name);END;
