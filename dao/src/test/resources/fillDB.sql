INSERT INTO account (account_id, email, password, first_name, last_name, middle_name, birthday, photo, photo_file_name, skype, icq, reg_date, role)
VALUES (1, 'a@a.ru', '123', 'Alexey', 'Ershov', 'Urievich', '1988-07-22', null, null, 'aaaaa', 0, '2018-06-08', 1);
INSERT INTO account (account_id, email, password, first_name, last_name, middle_name, birthday, photo, photo_file_name, skype, icq, reg_date, role)
VALUES (2, 'b@b.ru', '123', 'Sergey', 'Semenov', null, '1990-01-01', null, null, 'bbbbb', null, '2018-06-13', 0);
INSERT INTO account (account_id, email, password, first_name, last_name, middle_name, birthday, photo, photo_file_name, skype, icq, reg_date, role)
VALUES (3, 'c@c.ru', '123', 'Ivan', 'Ivanov', 'Ivanovich', '1970-05-29', null, null, 'ccccc', 12345, '2018-06-13', 0);

INSERT INTO phone (account_id, phone_number, phone_type) VALUES (1, '900', 0);
INSERT INTO phone (account_id, phone_number, phone_type) VALUES (1, '901', 1);

INSERT INTO soc_group (group_id, name, photo, photo_file_name, create_date, info, user_creator_id)
VALUES (1, 'Group 1', null, null, '2018-06-07', 'Info 1', 1);
INSERT INTO soc_group (group_id, name, photo, photo_file_name, create_date, info, user_creator_id)
VALUES (2, 'Group 2', null, null, '2018-06-09', 'Info 2', 2);

INSERT INTO account_in_group (group_id, user_member_id, role, status) VALUES (1, 1, 2, 2);
INSERT INTO account_in_group (group_id, user_member_id, role, status) VALUES (1, 3, 1, 2);
INSERT INTO account_in_group (group_id, user_member_id, role, status) VALUES (2, 2, 2, 2);

INSERT INTO relationship (user_one_id, user_two_id, status, action_user_id) VALUES (1, 2, 0, 1);
INSERT INTO relationship (user_one_id, user_two_id, status, action_user_id) VALUES (1, 3, 0, 1);
INSERT INTO relationship (user_one_id, user_two_id, status, action_user_id) VALUES (2, 3, 1, 3);

INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (1, 1, 0, null, null, 'Text account 1', '2018-06-17', 1);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (2, 1, 0, null, null, 'Text account 1-2', '2018-06-17', 1);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (3, 2, 0, null, null, 'Text account 2', '2018-06-17', 1);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (4, 1, 1, null, null, 'Text account wall 1', '2018-06-17', 2);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (5, 2, 1, null, null, 'Text account wall 2', '2018-06-17', 2);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (6, 1, 2, null, null, 'Text group 1', '2018-06-17', 3);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (7, 2, 2, null, null, 'Text group 2', '2018-06-17', 3);
INSERT INTO message (message_id, assign_id, type, photo, photo_file_name, text, date_create, user_creator_id)
VALUES (8, 2, 2, null, null, 'Text group 2-2', '2018-06-17', 3);