INSERT INTO hotel(id, name, prefecture) VALUES
(1, 'Condo Yu-Yu Katsuura', 'Chiba'),
(2, 'Business Hotel Royal Lake New Building', 'Kanagawa'),
(3, 'Ryokan Royal Oamishirasato', 'Chiba'),
(4, 'Hotel West Court', 'Tokyo'),
(5, 'Cheap Hotel Royal Spring', 'Tochigi'),
(6, 'Station Hotel Yu-Yu Street', 'Tokyo'),
(7, 'Capsule Hotel Royal Up', 'Kanagawa'),
(8, 'Capsule Hotel Grand System', 'Saitama');

INSERT INTO room(id, name, hotel_id) VALUES
(9, 'Single・Women Only', 1),
(10, 'Single・Non-smoking Room', 1),
(11, 'Queen・Women Only', 1),
(12, 'Double・Wide Room', 1),
(13, 'Semi-double・Large Room・WiFi', 2),
(14, 'King', 3),
(15, 'Single・Wide Room', 3),
(16, 'Double・Wide Room・Non-smoking Room', 3),
(17, 'Double・Wide Room・WiFi', 3),
(18, 'Semi-double・Wide Room', 3),
(19, 'King・WiFi', 4),
(20, 'Single・Large Room・WiFi', 5),
(21, 'Single・Large Room', 5),
(22, 'King', 5),
(23, 'Double・Wide Room・WiFi', 5),
(24, 'Single・Large Room・Non-smoking Room・Women Only', 5),
(25, 'Single・Wide Room・WiFi', 6),
(26, 'Double・Women Only', 7),
(27, 'Single', 7),
(28, 'Double・Non-smoking Room', 8),
(29, 'Single・Large Room・WiFi', 8),
(30, 'Semi-double・Women Only', 8),
(31, 'Single・Non-smoking Room', 8),
(32, 'Single・Large Room・Women Only', 8);

select setval('hibernate_sequence', 32);
