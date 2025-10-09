INSERT INTO Usuario(id, email,nombreUsuario, password, rol, activo,monedas,urlAvatar)
VALUES(null, 'test@unlam.edu.ar', 'jugador123', 'test', 'ADMIN',true,100,'img/avatar1.png');



INSERT INTO Avatar (id, nombre, urlImagen, iconoHexadecimal, precio)
VALUES
    (NULL, 'Hombre Elegante',   '/img/emoji_hombre.png',         NULL, 20),
    (NULL, 'Esmoquin',          '/img/emoji_esmoquin.png',       NULL, 30),
    (NULL, 'Harry Potter',      '/img/emoji_potter.png',         NULL, 40),
    (NULL, 'Detective',         '/img/emojis_detective.png',     NULL, 50),
    (NULL, 'Mago Mistico',      '/img/emojis_mago.png',          NULL, 100),
    (NULL, 'Pirata',            '/img/emojis_pirata.png',        NULL, 100),
    (NULL, 'Mujer Bufanda',     '/img/emoji_mujer.png',          NULL, 20),
    (NULL, 'Mujer Baquera',     '/img/emoji_mujer_baquera.png',  NULL, 50);
