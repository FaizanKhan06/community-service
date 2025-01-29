CREATE TABLE community
(
    community_id INT
    AUTO_INCREMENT PRIMARY KEY,
    community_name VARCHAR
    (255) NOT NULL,
    community_head VARCHAR
    (255),
    current_amount DOUBLE,
    rule_id INT,
    is_public BOOLEAN,
    remaining_term_period INT,
    next_contribution_date DATETIME,
    is_active BOOLEAN,
    is_deleted BOOLEAN
);
