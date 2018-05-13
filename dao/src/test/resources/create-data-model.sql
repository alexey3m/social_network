CREATE TABLE accounts (
    account_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (account_id)
);

CREATE TABLE account_info (
    account_id INT NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255) NOT NULL,
    birthday VARCHAR(255) NOT NULL,
    phone_pers VARCHAR(255) NOT NULL,
    phone_work VARCHAR(255) NOT NULL,
    address_pers VARCHAR(255) NOT NULL,
    address_work VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    icq INT(10) NOT NULL,
    skype VARCHAR(255) NOT NULL,
    extra VARCHAR(255) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE groups (
    group_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    info VARCHAR(255) NOT NULL,
    account_id_admin INT NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (account_id_admin) REFERENCES accounts (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE account_in_group (
    account_id INT NOT NULL,
    group_id INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES accounts (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups (group_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);