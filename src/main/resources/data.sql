INSERT INTO Usuario(id, email,nombreUsuario, password, rol, activo,monedas,urlAvatar)
VALUES(null, 'test@unlam.edu.ar', 'jugador123', 'test', 'ADMIN',true,100,'img/avatar1.png');


--Avatares con imagenes
INSERT INTO Avatar (id, nombre, urlImagen, iconoHexadecimal, precio)
VALUES
    (1, 'Hombre Elegante',   '/img/emoji_hombre.png',         NULL, 20),
    (2, 'Esmoquin',          '/img/emoji_esmoquin.png',       NULL, 30),
    (3, 'Harry Potter',      '/img/emoji_potter.png',         NULL, 40),
    (4, 'Detective',         '/img/emojis_detective.png',     NULL, 50),
    (5, 'Mago Mistico',      '/img/emojis_mago.png',          NULL, 100),
    (6, 'Pirata',            '/img/emojis_pirata.png',        NULL, 100),
    (7, 'Mujer Bufanda',     '/img/emoji_mujer.png',          NULL, 20),
    (8, 'Mujer Baquera',     '/img/emoji_mujer_baquera.png',  NULL, 50);

--Avatares gratuitos con iconos hexadecimales
INSERT INTO Avatar (id, nombre, urlImagen, iconoHexadecimal, precio)
VALUES
    (9, 'Emoji Sonriente',    NULL, '&#x1F600;', 0),
    (10, 'Alien',              NULL, '&#x1F47E;', 0),
    (11, 'Pelota',             NULL, '&#x26BD;', 0),
    (12, 'Policía',            NULL, '&#x1F46E;', 0),
    (13, 'Bombero',            NULL, '&#x1F469;&#x200D;&#x1F692;', 0),
    (14, 'Profesional',        NULL, '&#x1F468;&#x200D;&#x1F4BC;', 0),
    (15, 'Desarrollador',      NULL, '&#x1F469;&#x200D;&#x1F4BB;', 0),
    (16, 'Gamer',              NULL, '&#x1F3AE;', 0),
    (17, 'Inteligente',        NULL, '&#x1F9E0;', 0),
    (18, 'Estrella',           NULL, '&#x2B50;', 0),
    (19, 'Campeón',            NULL, '&#x1F3C6;', 0);


--Al registrarse el usuario obtiene los avatares gratuitos
INSERT INTO Usuario_Avatar (usuario_id, avatar_id,en_uso)
VALUES
    (1, 9,false),
    (1, 10,false),
    (1, 11,false),
    (1, 12,false),
    (1, 13,false),
    (1, 14,false),
    (1, 15,false),
    (1, 16,false),
    (1, 17,false),
    (1, 18,false),
    (1, 19,false);



-- Partidas de prueba
INSERT INTO Partida (id, usuario_id, dificultad, tiempoSegundos, puntaje, pistasUsadas, ganada, fechaHoraInicio)
VALUES
    (1, 1, 'FACIL', 120, 150, 0, TRUE, '2025-10-15 14:30:00'),
    (2, 1, 'MEDIA', 240, 200, 1, TRUE, '2025-10-30 10:20:00'),
    (3, 1, 'DIFICIL', 300, 250, 2, FALSE, '2025-10-17 18:45:00'),
    (4, 1, 'FACIL', 90, 180, 0, TRUE, '2025-10-17 20:10:00'),
    (5, 1, 'MEDIA', 210, 220, 1, TRUE, '2025-10-18 09:00:00');


