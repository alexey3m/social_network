INSERT INTO account (account_id, email, password, first_name, last_name, middle_name, birthday, photo, skype, icq, reg_date, role)
VALUES (1, 'a@a.ru', '123', 'Alexey', 'Ershov', 'Urievich', '1988-07-22', null, 'aaaaa', 0, '2018-06-08', 'ADMIN');
INSERT INTO account (account_id, email, password, first_name, last_name, middle_name, birthday, photo, skype, icq, reg_date, role)
VALUES (2, 'b@b.ru', '123', 'Sergey', 'Semenov', '', '1990-01-01', null, 'bbbbb', 0, '2018-06-13', 'USER');
INSERT INTO account (account_id, email, password, first_name, last_name, middle_name, birthday, photo, skype, icq, reg_date, role)
VALUES (3, 'c@c.ru', '123', 'Ivan', 'Ivanov', 'Ivanovich', '1970-05-29', null, 'ccccc', 12345, '2018-06-13', 'USER');

INSERT INTO phone (phone_id, account_id, phone_number, phone_type) VALUES (1, 1, '900', 'MOBILE');
INSERT INTO phone (phone_id, account_id, phone_number, phone_type) VALUES (2, 1, '901', 'WORK');

INSERT INTO soc_group (group_id, name, photo, create_date, info, user_creator_id)
VALUES (1, 'Group 1', null, '2018-06-07', 'Info 1', 1);
INSERT INTO soc_group (group_id, name, photo, create_date, info, user_creator_id)
VALUES (2, 'Group 2', null, '2018-06-09', 'Info 2', 2);

INSERT INTO account_in_group (id, group_id, user_member_id, role, status) VALUES (1, 1, 1, 'ADMIN', 'ACCEPTED');
INSERT INTO account_in_group (id, group_id, user_member_id, role, status) VALUES (2, 1, 3, 'USER', 'ACCEPTED');
INSERT INTO account_in_group (id, group_id, user_member_id, role, status) VALUES (3, 2, 2, 'ADMIN', 'ACCEPTED');

INSERT INTO relationship (id, user_one_id, user_two_id, status, action_user_id) VALUES (1, 1, 2, 'PENDING', 1);
INSERT INTO relationship (id, user_one_id, user_two_id, status, action_user_id) VALUES (2, 1, 3, 'PENDING', 1);
INSERT INTO relationship (id, user_one_id, user_two_id, status, action_user_id) VALUES (3, 2, 3, 'ACCEPTED', 3);

INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (1, 1, 'ACCOUNT', null, 'Text account 1', '2018-06-17', 1);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (2, 1, 'ACCOUNT', null, 'Text account 1-2', '2018-06-17', 1);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (3, 2, 'ACCOUNT', null, 'Text account 2', '2018-06-17', 1);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (4, 1, 'ACCOUNT_WALL', null, 'Text account wall 1', '2018-06-17', 2);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (5, 2, 'ACCOUNT_WALL', null, 'Text account wall 2', '2018-06-17', 2);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (6, 1, 'GROUP_WALL', null, 'Text group 1', '2018-06-17', 3);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (7, 2, 'GROUP_WALL', null, 'Text group 2', '2018-06-17', 3);
INSERT INTO message (message_id, assign_id, type, photo, text, date_create, user_creator_id)
VALUES (8, 2, 'GROUP_WALL', null, 'Text group 2-2', '2018-06-17', 3);