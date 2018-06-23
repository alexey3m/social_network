CREATE TABLE account (
    account_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    birthday DATE,
    photo MEDIUMBLOB,
    photo_file_name VARCHAR(255),
    skype VARCHAR(255),
    icq INT(10),
    reg_date DATE,
    role TINYINT,
    PRIMARY KEY (account_id)
);

CREATE TABLE phone (
    account_id INT NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    phone_type TINYINT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE relationship (
    user_one_id INT NOT NULL,
    user_two_id INT NOT NULL,
    status TINYINT NOT NULL,
    action_user_id INT NOT NULL,
    FOREIGN KEY (user_one_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (user_two_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (action_user_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    UNIQUE KEY (user_one_id, user_two_id)
);

CREATE TABLE soc_group (
    group_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    photo MEDIUMBLOB,
    photo_file_name VARCHAR(255),
    create_date DATE,
    info TEXT NOT NULL,
    user_creator_id INT NOT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (user_creator_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE account_in_group (
    group_id INT NOT NULL,
    user_member_id INT NOT NULL,
    role TINYINT NOT NULL,
    status TINYINT NOT NULL,
    FOREIGN KEY (group_id) REFERENCES soc_group (group_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (user_member_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE message (
    message_id INT NOT NULL AUTO_INCREMENT,
    assign_id INT NOT NULL,
    type TINYINT NOT NULL,
    photo MEDIUMBLOB,
    photo_file_name VARCHAR(255),
    text TEXT,
    date_create DATE NOT NULL,
    user_creator_id INT NOT NULL,
    PRIMARY KEY (message_id),
    FOREIGN KEY (user_creator_id) REFERENCES account (account_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);