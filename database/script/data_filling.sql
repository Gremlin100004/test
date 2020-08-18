USE car_service;
INSERT INTO masters VALUES
(1, 'Petya', false),
(2, 'Vasya', false),
(3, 'Georgiy', false),
(4, 'Antonina', false),
(5, 'Igor', false),
(6, 'Dmitriy', false),
(7, 'Aleksandr', false),
(8, 'Jon', false),
(9, 'Konstantin', false),
(10, 'Andrey', false),
(11, 'Pasha', false),
(12, 'Egor', false),
(13, 'Slava', false),
(14, 'Valera', false),
(15, 'Vitalik', false),
(16, 'Sergei', false),
(17, 'Danial', false),
(18, 'Ivan', false),
(19, 'Nikolai', false),
(20, 'Patrik', false),
(21, 'Semen', false),
(22, 'Uriy', false);

INSERT INTO places VALUES
(1, 1, false, false),
(2, 2, false, false),
(3, 3, false, false),
(4, 4, false, false),
(5, 5, false, false),
(6, 6, false, false),
(7, 7, false, false),
(8, 8, false, false),
(9, 9, false, false),
(10, 10, false, false),
(11, 11, false, false),
(12, 12, false, false);

INSERT INTO orders VALUES
(1, NOW(), '2020-09-11 10:00', '2020-09-11 18:00', 'Lexus', 'LS', '1234 AB-7', '360.99', 'WAIT', false, 1),
(2, NOW(), '2020-09-11 10:00', '2020-09-11 18:00', 'Mercedes', 'A 200', '3234 AB-7', '600.23', 'WAIT', false, 2),
(3, NOW(), '2020-09-12 14:00', '2020-09-13 10:00', 'BMW', 'X6', '2222 AB-7', '457.34', 'WAIT', false, 3),
(4, NOW(), '2020-09-13 15:00', '2020-09-14 18:00', 'Audi', 'A6', '1111 AB-7', '10020.99', 'WAIT', false, 4),
(5, NOW(), '2020-09-14 15:00', '2020-09-15 10:00', 'Opel', 'Insignia', '4444 AB-7', '443.65', 'WAIT', false, 5),
(6, NOW(), '2020-09-15 15:00', '2020-09-16 10:00', 'Audi', 'A4', '5555 AB-7', '321.67', 'WAIT', false, 6),
(7, NOW(), '2020-09-16 15:00', '2020-09-17 10:00', 'Ford', 'Mondeo', '6666 AB-7', '367.46', 'WAIT', false, 7),
(8, NOW(), '2020-09-16 15:00', '2020-09-18 10:00', 'KIA', 'Optima', '7777 AB-7', '642.12', 'WAIT', false, 8),
(9, NOW(), '2020-09-16 15:00', '2020-09-19 10:00', 'Infinity', 'QX30', '8888 AB-7', '735.78', 'WAIT', false, 9),
(10, NOW(), '2020-09-16 15:00', '2020-09-20 10:00', 'Mazda', 'RX7', '9999 AB-7', '135.39', 'WAIT', false, 10),
(11, NOW(), '2020-09-16 15:00', '2020-09-20 10:00', 'Chevrolet', 'Epica', '8484 AB-7', '335.62', 'WAIT', false, 11),
(12, NOW(), '2020-09-16 15:00', '2020-09-20 10:00', 'Lexus', 'LS', '1919 AB-7', '867.31', 'WAIT', false, 12);

INSERT INTO orders_masters VALUES
(1, 1), (1, 2), (2, 3), (2, 4),
(3, 5), (3, 6), (4, 7), (4, 8),
(5, 9), (5, 10), (6, 11), (6, 12),
(7, 13), (7, 14), (8, 15), (8, 16),
(9, 17), (9, 18), (10, 19), (10, 20),
(11, 21), (11, 22), (12, 1), (12, 2);