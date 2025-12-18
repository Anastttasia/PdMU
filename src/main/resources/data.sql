--мигранты
INSERT INTO migrants (name, passport, citizenship) VALUES ('Богданчук Николай Николаевич', 'МР 1234567', 'Беларусь')
INSERT INTO migrants (name, passport, citizenship) VALUES ('Ахметова Айгул Николаевич', 'N 1234567', 'Казахстан')

--админы
--50643 - хэш от 333
INSERT INTO admins (email, password_hash) VALUES ('testadmin@gmail.com', 50643)