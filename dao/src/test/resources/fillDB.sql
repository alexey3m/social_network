INSERT INTO accounts (username, password) VALUES ('alex1', '123');
INSERT INTO accounts (username, password) VALUES ('ivan1', '1234');
INSERT INTO accounts (username, password) VALUES ('sergey1', '12345');

INSERT INTO account_info (account_id, first_name, last_name, middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, icq, skype, extra) VALUES (1, 'Alexey', 'Ershov', 'Urievich', '22.07.1988', '+79230000000', '+739121231212', 'Krasnoyarsk, Karla Marksa str.', 'Krasnoyarsk, Svobodny pr.', 'a@a.ru', 1234567890, 'aaaaa', 'extra information');
INSERT INTO account_info (account_id, first_name, last_name, middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, icq, skype, extra) VALUES (2, 'Ivan', 'Ivanov', 'Alexeyevich', '27.01.1993', '+79230000001', '+739121231212', 'Moskow', 'Moskow 2', 'a2@a.ru', 1234500000, 'bbbb', 'extra information');
INSERT INTO account_info (account_id, first_name, last_name, middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, icq, skype, extra) VALUES (3, 'Sergey', 'Nosov', 'Ivanovych', '02.09.1987', '+79230000002', '+739121231212', 'Sankt Petersburg', 'Sankt Petersburg 2', 'a3@a.ru', 1234500001, 'ccccc', 'extra information');

INSERT INTO groups (name, info, account_id_admin) VALUES ('Group 1', 'Info 1', 1);
INSERT INTO groups (name, info, account_id_admin) VALUES ('Group 2', 'Info 2', 2);

INSERT INTO account_in_group (account_id, group_id) VALUES(1, 1);
INSERT INTO account_in_group (account_id, group_id) VALUES(2, 2);
INSERT INTO account_in_group (account_id, group_id) VALUES(3, 1);
