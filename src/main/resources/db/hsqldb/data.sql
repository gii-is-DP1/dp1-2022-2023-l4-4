-- CREATE TABLE user_games(id integer, orderPlayer integer ,accusations_number integer ,is_afk boolean ,suspect varchar(60),user_id integer,game_id integer);
 
-- One admin user, named admin1 with passwor 4dm1n and authority admin

INSERT INTO users(id,username,password,email,image_url,enabled,authority) VALUES (1,'1','1','1@gmail.com',null,1,'admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(id,username,password,email,image_url,enabled,authority) VALUES (2,'2','2','2@gmail.com',null,1,'user');

-- One vet user, named vet1 with passwor v3t
INSERT INTO users(id,username,password,email,image_url,enabled,authority) VALUES (3,'manuel333','1','user2@gmail.com',null,1,'user');




INSERT INTO celds(id,celd_type,position) VALUES  
(1, 'CORRIDOR',7), 
(2, 'CORRIDOR',8), 
(3, 'CORRIDOR',14), 
(4, 'CORRIDOR',21), 
(5, 'CORRIDOR',22), 
(6, 'CORRIDOR',31), 
(7, 'CORRIDOR',32), 
(8, 'CORRIDOR',38), 
(9, 'CORRIDOR',45), 
(10, 'CORRIDOR',46), 
(11, 'CORRIDOR',55), 
(12, 'CORRIDOR',56), 
(13, 'CORRIDOR',62), 
(14, 'CORRIDOR',69), 
(15, 'CORRIDOR',70), 
(16, 'CORRIDOR',79), 
(17, 'CORRIDOR',80), 
(18, 'CORRIDOR',86), 
(19, 'CORRIDOR',93), 
(20, 'CORRIDOR',94), 
(21, 'CORRIDOR',103), 
(22, 'CORRIDOR',104), 
(23, 'CORRIDOR',110), 
(24, 'CORRIDOR',117), 
(25, 'CORRIDOR',118), 
(26, 'CORRIDOR',127), 
(27, 'CORRIDOR',128), 
(28, 'CORRIDOR',134), 
(29, 'CORRIDOR',141), 
(30, 'CORRIDOR',142), 
(31, 'CORRIDOR',150), 
(32, 'CORRIDOR',151), 
(33, 'CORRIDOR',152), 
(34, 'CORRIDOR',158), 
(35, 'CORRIDOR',165), 
(36, 'CORRIDOR',166), 
(37, 'CORRIDOR',174), 
(38, 'CORRIDOR',175), 
(39, 'CORRIDOR',176), 
(40, 'CORRIDOR',182), 
(41, 'CORRIDOR',189), 
(42, 'CORRIDOR',190), 
(43, 'CORRIDOR',193), 
(44, 'CORRIDOR',194), 
(45, 'CORRIDOR',195), 
(46, 'CORRIDOR',196), 
(47, 'CORRIDOR',197), 
(48, 'CORRIDOR',198), 
(49, 'CORRIDOR',199), 
(50, 'CORRIDOR',200), 
(51, 'CORRIDOR',201), 
(52, 'CORRIDOR',202), 
(53, 'CORRIDOR', 203), 
(54, 'CORRIDOR', 204), 
(55, 'CORRIDOR', 205), 
(56, 'CORRIDOR', 206), 
(57, 'CORRIDOR', 207), 
(58, 'CORRIDOR', 211), 
(59, 'CORRIDOR', 212), 
(60, 'CORRIDOR',213), 
(61, 'CORRIDOR',214), 
(62, 'CORRIDOR',215), 
(63, 'CORRIDOR',216), 
(64, 'CORRIDOR',217), 
(65, 'CORRIDOR',218), 
(66, 'CORRIDOR',219), 
(67, 'CORRIDOR',220), 
(68, 'CORRIDOR',221), 
(69, 'CORRIDOR',222), 
(70, 'CORRIDOR',223), 
(71, 'CORRIDOR',224), 
(72, 'CORRIDOR',225), 
(73, 'CORRIDOR',226), 
(74, 'CORRIDOR',227), 
(75, 'CORRIDOR',228), 
(76, 'CORRIDOR',229), 
(77, 'CORRIDOR',230), 
(78, 'CORRIDOR',231), 
(79, 'CORRIDOR',232), 
(80, 'CORRIDOR',233), 
(81, 'CORRIDOR',234), 
(82, 'CORRIDOR',235), 
(83, 'CORRIDOR',236), 
(84, 'CORRIDOR',237), 
(85, 'CORRIDOR',238), 
(86, 'CORRIDOR',239), 
(87, 'CORRIDOR',240), 
(88, 'CORRIDOR',245), 
(89, 'CORRIDOR',246), 
(90, 'CORRIDOR',247), 
(91, 'CORRIDOR',248), 
(92, 'CORRIDOR',249), 
(93, 'CORRIDOR',250), 
(94, 'CORRIDOR',251), 
(95, 'CORRIDOR',252), 
(96, 'CORRIDOR',253), 
(97, 'CORRIDOR',254), 
(98, 'CORRIDOR',255), 
(99, 'CORRIDOR',256), 
(100, 'CORRIDOR',257), 
(101, 'CORRIDOR',258), 
(102, 'CORRIDOR',259), 
(103, 'CORRIDOR',260), 
(104, 'CORRIDOR',261), 
(105, 'CORRIDOR',262), 
(106, 'CORRIDOR',263), 
(107, 'CORRIDOR',264), 
(108, 'CORRIDOR',273), 
(109, 'CORRIDOR',274), 
(110, 'CORRIDOR',283), 
(111, 'CORRIDOR',297), 
(112, 'CORRIDOR',298), 
(113, 'CORRIDOR',307), 
(114, 'CORRIDOR',321), 
(115, 'CORRIDOR',322), 
(116, 'CORRIDOR',331), 
(117, 'CORRIDOR',345), 
(118, 'CORRIDOR',346), 
(119, 'CORRIDOR',355), 
(120, 'CORRIDOR',369), 
(121, 'CORRIDOR',370), 
(122, 'CORRIDOR',379), 
(123, 'CORRIDOR',393), 
(124, 'CORRIDOR',394), 
(125, 'CORRIDOR',403), 
(126, 'CORRIDOR',417), 
(127, 'CORRIDOR',418), 
(128, 'CORRIDOR',419), 
(129, 'CORRIDOR',420), 
(130, 'CORRIDOR',421), 
(131, 'CORRIDOR',422), 
(132, 'CORRIDOR',423), 
(133, 'CORRIDOR',424), 
(134, 'CORRIDOR',425), 
(135, 'CORRIDOR',426), 
(136, 'CORRIDOR',427), 
(137, 'CORRIDOR',437), 
(138, 'CORRIDOR',438), 
(139, 'CORRIDOR',439), 
(140, 'CORRIDOR',440), 
(141, 'CORRIDOR',441), 
(142, 'CORRIDOR',442), 
(143, 'CORRIDOR',443), 
(144, 'CORRIDOR',444), 
(145, 'CORRIDOR',445), 
(146, 'CORRIDOR',446), 
(147, 'CORRIDOR',447), 
(148, 'CORRIDOR',448), 
(149, 'CORRIDOR',449), 
(150, 'CORRIDOR',450), 
(151, 'CORRIDOR',451), 
(152, 'CORRIDOR',452), 
(153, 'CORRIDOR',453), 
(154, 'CORRIDOR',454), 
(155, 'CORRIDOR',455), 
(156, 'CORRIDOR',456), 
(157, 'CORRIDOR',457), 
(158, 'CORRIDOR',458), 
(159, 'CORRIDOR',459), 
(160, 'CORRIDOR',460), 
(161, 'CORRIDOR',461), 
(162, 'CORRIDOR',462), 
(163, 'CORRIDOR',463), 
(164, 'CORRIDOR',464), 
(165, 'CORRIDOR',465), 
(166, 'CORRIDOR',466), 
(167, 'CORRIDOR',473), 
(168, 'CORRIDOR',474), 
(169, 'CORRIDOR',475), 
(170, 'CORRIDOR',476), 
(171, 'CORRIDOR',477), 
(172, 'CORRIDOR',478), 
(173, 'CORRIDOR',479), 
(174, 'CORRIDOR',480), 
(175, 'CORRIDOR',481), 
(176, 'CORRIDOR',482), 
(177, 'CORRIDOR',483), 
(178, 'CORRIDOR',484), 
(179, 'CORRIDOR',485), 
(180, 'CORRIDOR',486), 
(181, 'CORRIDOR',487), 
(182, 'CORRIDOR',488), 
(183, 'CORRIDOR',489), 
(184, 'CORRIDOR',490), 
(185, 'CORRIDOR',497), 
(186, 'CORRIDOR',498), 
(187, 'CORRIDOR',499), 
(188, 'CORRIDOR',500), 
(189, 'CORRIDOR',501), 
(190, 'CORRIDOR',511), 
(191, 'CORRIDOR',512), 
(192, 'CORRIDOR',513), 
(193, 'CORRIDOR',514), 
(194, 'CORRIDOR',521), 
(195, 'CORRIDOR',522), 
(196, 'CORRIDOR',523), 
(197, 'CORRIDOR',524), 
(198, 'CORRIDOR',536), 
(199, 'CORRIDOR',537), 
(200, 'CORRIDOR',538), 
(201, 'CORRIDOR',545), 
(202, 'CORRIDOR',546), 
(203, 'CORRIDOR',547), 
(204, 'CORRIDOR',548), 
(205, 'CORRIDOR',560), 
(206, 'CORRIDOR',561), 
(207, 'CORRIDOR',570), 
(208, 'CORRIDOR',571), 
(209, 'CORRIDOR',572), 
(210, 'CORRIDOR',584), 
(211, 'CORRIDOR',585), 
(212, 'CORRIDOR',596), 
(213, 'CORRIDOR',597), 
(214, 'CORRIDOR',596), 
(215, 'CORRIDOR',608), 
(216, 'CORRIDOR',609), 
(217, 'CORRIDOR',618), 
(218, 'CORRIDOR',619), 
(219, 'CORRIDOR',620), 
(220, 'CORRIDOR',532), 
(221, 'CORRIDOR',633), 
(222, 'CORRIDOR',642), 
(223, 'CORRIDOR',643), 
(224, 'CORRIDOR',644), 
(225, 'CORRIDOR',656), 
(226, 'CORRIDOR',657), 
(227, 'CORRIDOR',666), 
(228, 'CORRIDOR',667), 
(229, 'CORRIDOR',668), 
(230, 'CORRIDOR',680), 
(231, 'CORRIDOR',681), 
(232, 'CORRIDOR',690), 
(233, 'CORRIDOR',691), 
(234, 'CORRIDOR',692), 
(235, 'SPA',51), 
(236, 'THEATRE',59), 
(237, 'LIVING_ROOM ',65), 
(238, 'OBSERVATORY',71), 
(239, 'YARD ',291), 
(240, 'HALL',310), 
(241, 'KITCHEN',555), 
(242, 'DINNING_HALL',541), 
(243, 'GUEST_ROOM',575), 
(244, 'CENTER',326);  

INSERT INTO connected_celds(id2,id1) VALUES
(2, 1),
 (5, 4),
 (6, 1),
 (7, 2),
 (7, 6),
 (8, 3),
 (9, 4),
 (10, 5),
 (10, 9),
 (11, 6),
 (12, 7),
 (12, 11),
 (13, 8),
 (14, 9),
 (15, 10),
 (15, 14),
 (16, 11),
 (17, 12),
 (17, 16),
 (18, 13),
 (19, 14),
 (20, 15),
 (20, 19),
 (21, 16),
 (22, 17),
 (22, 21),
 (23, 18),
 (24, 19),
 (25, 20),
 (25, 24),
 (26, 21),
 (27, 22),
 (27, 26),
 (28, 23),
 (29, 24),
 (30, 25),
 (30, 29),
 (32, 26),
 (32, 31),
 (33, 27),
 (33, 32),
 (34, 28),
 (35, 29),
 (36, 30),
 (36, 35),
 (37, 31),
 (38, 32),
 (38, 37),
 (39, 33),
 (39, 38),
 (40, 34),
 (41, 35),
 (42, 36),
 (42, 41),
 (44, 43),
 (45, 44),
 (46, 45),
 (47, 46),
 (48, 37),
 (48, 47),
 (49, 38),
 (49, 48),
 (50, 39),
 (50, 49),
 (51, 50),
 (52, 51),
 (53, 52),
 (54, 53),
 (55, 54),
 (56, 40),
 (56, 55),
 (57, 56),
 (59, 58),
 (60, 41),
 (60, 59),
 (61, 42),
 (61, 60),
 (62, 61),
 (63, 62),
 (64, 43),
 (64, 63),
 (65, 44),
 (65, 64),
 (66, 45),
 (66, 65),
 (67, 46),
 (67, 66),
 (68, 47),
 (68, 67),
 (69, 48),
 (69, 68),
 (70, 49),
 (70, 69),
 (71, 50),
 (71, 70),
 (72, 51),
 (72, 71),
 (73, 52),
 (73, 72),
 (74, 53),
 (74, 73),
 (75, 54),
 (75, 74),
 (76, 55),
 (76, 75),
 (77, 56),
 (77, 76),
 (78, 57),
 (78, 77),
 (79, 78),
 (80, 79),
 (81, 80),
 (82, 58),
 (82, 81),
 (83, 59),
 (83, 82),
 (84, 60),
 (84, 83),
 (85, 61),
 (85, 84),
 (86, 62),
 (86, 85),
 (87, 63),
 (87, 86),
 (88, 68),
 (89, 69),
 (89, 88),
 (90, 70),
 (90, 89),
 (91, 71),
 (91, 90),
 (92, 72),
 (92, 91),
 (93, 73),
 (93, 92),
 (94, 74),
 (94, 93),
 (95, 75),
 (95, 94),
 (96, 76),
 (96, 95),
 (97, 77),
 (97, 96),
 (98, 78),
 (98, 97),
 (99, 79),
 (99, 98),
 (100, 80),
 (100, 99),
 (101, 81),
 (101, 100),
 (102, 82),
 (102, 101),
 (103, 83),
 (103, 102),
 (104, 84),
 (104, 103),
 (105, 85),
 (105, 104),
 (106, 86),
 (106, 105),
 (107, 87),
 (107, 106),
 (108, 92),
 (109, 93),
 (109, 108),
 (110, 102),
 (111, 108),
 (112, 109),
 (112, 111),
 (113, 110),
 (114, 111),
 (115, 112),
 (115, 114),
 (116, 113),
 (117, 114),
 (118, 115),
 (118, 117),
 (119, 116),
 (120, 117),
 (121, 118),
 (121, 120),
 (122, 119),
 (123, 120),
 (124, 121),
 (124, 123),
 (125, 122),
 (126, 123),
 (127, 124),
 (127, 126),
 (128, 127),
 (129, 128),
 (130, 129),
 (131, 130),
 (132, 131),
 (133, 132),
 (134, 133),
 (135, 134),
 (136, 125),
 (136, 135),
 (138, 137),
 (139, 138),
 (140, 139),
 (141, 126),
 (141, 140),
 (142, 127),
 (142, 141),
 (143, 128),
 (143, 142),
 (144, 129),
 (144, 143),
 (145, 130),
 (145, 144),
 (146, 131),
 (146, 145),
 (147, 132),
 (147, 146),
 (148, 133),
 (148, 147),
 (149, 134),
 (149, 148),
 (150, 135),
 (150, 149),
 (151, 136),
 (151, 150),
 (152, 151),
 (153, 152),
 (154, 153),
 (155, 154),
 (156, 155),
 (157, 156),
 (158, 157),
 (159, 158),
 (160, 159),
 (161, 137),
 (161, 160),
 (162, 138),
 (162, 161),
 (163, 139),
 (163, 162),
 (164, 140),
 (164, 163),
 (165, 141),
 (165, 164),
 (166, 142),
 (166, 165),
 (167, 149),
 (168, 150),
 (168, 167),
 (169, 151),
 (169, 168),
 (170, 152),
 (170, 169),
 (171, 153),
 (171, 170),
 (172, 154),
 (172, 171),
 (173, 155),
 (173, 172),
 (174, 156),
 (174, 173),
 (175, 157),
 (175, 174),
 (176, 158),
 (176, 175),
 (177, 159),
 (177, 176),
 (178, 160),
 (178, 177),
 (179, 161),
 (179, 178),
 (180, 162),
 (180, 179),
 (181, 163),
 (181, 180),
 (182, 164),
 (182, 181),
 (183, 165),
 (183, 182),
 (184, 166),
 (184, 183),
 (185, 167),
 (186, 168),
 (186, 185),
 (187, 169),
 (187, 186),
 (188, 170),
 (188, 187),
 (189, 171),
 (189, 188),
 (190, 181),
 (191, 182),
 (191, 190),
 (192, 183),
 (192, 191),
 (193, 184),
 (193, 192),
 (194, 185),
 (195, 186),
 (195, 194),
 (196, 187),
 (196, 195),
 (197, 188),
 (197, 196),
 (198, 191),
 (199, 192),
 (199, 198),
 (200, 193),
 (200, 199),
 (201, 194),
 (202, 195),
 (202, 201),
 (203, 196),
 (203, 202),
 (204, 197),
 (204, 203),
 (205, 198),
 (206, 199),
 (206, 205),
 (207, 202),
 (208, 203),
 (208, 207),
 (209, 204),
 (209, 208),
 (210, 205),
 (211, 206),
 (211, 210),
 (212, 209),
 (213, 212),
 (213, 212),
 (212, 209),
 (215, 210),
 (216, 211),
 (216, 215),
 (218, 217),
 (219, 212),
 (219, 212),
 (219, 218),
 (221, 216),
 (222, 217),
 (223, 218),
 (223, 222),
 (224, 219),
 (224, 223),
 (226, 221),
 (226, 225),
 (227, 222),
 (228, 223),
 (228, 227),
 (229, 224),
 (229, 228),
 (230, 225),
 (231, 226),
 (231, 230),
 (232, 227),
 (233, 228),
 (233, 232),
 (234, 229),
 (234, 233),
 (235,31),
 (236,13),
 (236,53),
 (237,28),
 (237,80),
 (238,42),
 (239,89),
 (239,111),
 (239,123),
 (239,138),
 (240,116),
 (240,119),
 (240,106),
 (241,190),
 (242,145),
 (242,194),
 (243,189),
 (243,235),
 (241,238),
 (244,98),
 (244,128),
 (244,135);

--game 1 host 1, lobbysize 4, public, finished
INSERT INTO games(id,host_id,lobby_size,is_private,status) VALUES (1,1,4,0,2);

INSERT INTO lobbies(game_id,user_id) VALUES
(1,1),
(1,2);

INSERT INTO user_games(id,order_user, accusations_number, is_afk,suspect,user_id,game_id) VALUES 
(1,0,1,1,1,1,1),
(2,1,1,1,2,2,1);

INSERT INTO turns(user_game_id, round, dice_result, initial_celd_id,phase) VALUES (1,1,5,1,3);

INSERT INTO players(game_id,user_game_id) VALUES 
(1,1),
(1,2);

--game 2, host 2, size, 3, private, ingame
INSERT INTO games(id,host_id,lobby_size,is_private,status) VALUES (2,2,3,1,2);

INSERT INTO lobbies(game_id,user_id) VALUES 
(2,1),
(2,2),
(2,3);

INSERT INTO user_games(id,order_user, accusations_number, is_afk,suspect,user_id,game_id) VALUES
(3,0,1,1,1,1,2),
(4,1,1,0,2,2,2),
(5,2,1,0,3,3,2);

INSERT INTO turns(user_game_id, round, dice_result, initial_celd_id,phase) VALUES (3,1,5,1,5);

INSERT INTO players(game_id,user_game_id) VALUES 
(2,3),
(2,4),
(2,5);



--game 3 host 3, size 6, public, lobby
INSERT INTO games(id,host_id,lobby_size,is_private,status) VALUES (3,3,6,0,0);


INSERT INTO lobbies(game_id,user_id) VALUES 
(3,1),
(3,2),
(3,3);

INSERT INTO user_games(id,order_user, accusations_number, is_afk,suspect,user_id,game_id) VALUES
(6,2,1,0,1,1,3),
(7,1,1,1,2,2,3),
(8,0,0,0,3,3,3);

INSERT INTO turns(user_game_id, round, dice_result, initial_celd_id,phase) VALUES
(8,1,5,1,5),
(8,1,11,1,4);

INSERT INTO players(game_id,user_game_id) VALUES 
(3,6),
(3,7),
(3,8);
