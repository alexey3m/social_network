CREATE TABLE account (
  account_id  INT          NOT NULL AUTO_INCREMENT,
  email       VARCHAR(255) NOT NULL,
  password    VARCHAR(255) NOT NULL,
  first_name  VARCHAR(255) NOT NULL,
  last_name   VARCHAR(255) NOT NULL,
  middle_name VARCHAR(255) NOT NULL,
  birthday    DATE,
  photo       MEDIUMBLOB,
  skype       VARCHAR(255),
  icq         VARCHAR(255),
  reg_date    DATE,
  role        enum ('ROLE_USER', 'ROLE_ADMIN'),
  PRIMARY KEY (account_id)
);

CREATE TABLE phone (
  phone_id     INT                             NOT NULL AUTO_INCREMENT,
  account_id   INT                             NOT NULL,
  phone_number VARCHAR(15)                     NOT NULL,
  phone_type   enum ('MOBILE', 'WORK', 'HOME') NOT NULL,
  PRIMARY KEY (phone_id),
  FOREIGN KEY (account_id) REFERENCES account (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE relationship (
  id             INT                                                NOT NULL AUTO_INCREMENT,
  user_one_id    INT                                                NOT NULL,
  user_two_id    INT                                                NOT NULL,
  status         enum ('UNKNOWN', 'PENDING', 'ACCEPTED', 'DECLINE') NOT NULL,
  action_user_id INT                                                NOT NULL,
  PRIMARY KEY (id),
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
  group_id        INT          NOT NULL AUTO_INCREMENT,
  name            VARCHAR(255) NOT NULL,
  photo           MEDIUMBLOB,
  create_date     DATE,
  info            TEXT         NOT NULL,
  user_creator_id INT          NOT NULL,
  PRIMARY KEY (group_id),
  FOREIGN KEY (user_creator_id) REFERENCES account (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE account_in_group (
  id             INT                                                NOT NULL AUTO_INCREMENT,
  group_id       INT                                                NOT NULL,
  user_member_id INT                                                NOT NULL,
  role           enum ('UNKNOWN', 'USER', 'ADMIN')                  NOT NULL,
  status         enum ('UNKNOWN', 'PENDING', 'ACCEPTED', 'DECLINE') NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (group_id) REFERENCES soc_group (group_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  FOREIGN KEY (user_member_id) REFERENCES account (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);

CREATE TABLE message (
  message_id      INT                                            NOT NULL AUTO_INCREMENT,
  assign_id       INT                                            NOT NULL,
  type            enum ('ACCOUNT', 'ACCOUNT_WALL', 'GROUP_WALL') NOT NULL,
  photo           MEDIUMBLOB,
  text            TEXT,
  date_create     DATE                                           NOT NULL,
  user_creator_id INT                                            NOT NULL,
  PRIMARY KEY (message_id),
  FOREIGN KEY (user_creator_id) REFERENCES account (account_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);