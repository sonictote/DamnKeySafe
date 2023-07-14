-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-07-2023 a las 14:25:02
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectofinal`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales_usuario`
--

CREATE TABLE `credenciales_usuario` (
  `id_contrasenya` int(11) NOT NULL,
  `nom_usuario` varchar(50) DEFAULT NULL,
  `usuario_guardar` varchar(100) NOT NULL,
  `contrasenya_guardar` varchar(255) NOT NULL,
  `nombre_credencial` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `credenciales_usuario`
--

INSERT INTO `credenciales_usuario` (`id_contrasenya`, `nom_usuario`, `usuario_guardar`, `contrasenya_guardar`, `nombre_credencial`) VALUES
(7, 'usuario1', 'johndoe', 'guardar4', 'Telegram'),
(8, 'usuario2', 'prueba', 'PrU12', 'EstoEsUnaPrueba'),
(9, 'usuario3', 'alexwilson', 'guardar6', 'Primo Alex'),
(10, 'usuario1', 'emilyjones', 'guardar7', 'Netflix'),
(11, 'usuario2', 'mikesmith', 'guardar8', 'Contraseña BBDD'),
(12, 'usuario3', 'sarahwilson', 'guardar9', 'Pc casa'),
(13, 'usuario1', 'johndoe123', 'guardar30', 'Twitter'),
(15, 'usuario2', 'alexwilson789', 'guardar32', 'Steam'),
(16, 'usuario2', 'emilyjones321', 'guardar33', 'Wordpress'),
(17, 'usuario3', 'mikesmith654', 'guardar34', 'Amazon'),
(18, 'usuario3', 'sarahwilson987', 'guardar35', 'Amazon prime video'),
(20, 'usuario1', 'janedoe456', 'guardar37', 'Orange TV'),
(21, 'usuario2', 'chrismiller789', 'guardar38', 'Movistar TV'),
(22, 'usuario2', 'annawalker321', 'guardar39', 'Usuario temporal'),
(23, 'usuario3', 'brianroberts654', 'guardar40', 'World of tanks'),
(24, 'usuario3', 'jennyclark987', 'guardar41', 'Waylet'),
(25, 'usuario1', 'kevinharris123', 'guardar42', 'Tinder'),
(26, 'usuario1', 'amycampbell456', 'guardar43', 'Consum app'),
(27, 'usuario2', 'ryanmartin789', 'guardar44', 'JuegosGratis.com'),
(28, 'usuario2', 'laurasmith321', 'guardar45', 'No se que poner'),
(29, 'usuario3', 'samueljones654', 'guardar46', 'Este no creo que lo use'),
(30, 'usuario3', 'chloewilson987', 'guardar47', 'SI'),
(31, 'usuario1', 'jasondavis123', 'guardar48', 'HBO'),
(33, 'usuario3', 'brianroberts654', 'guardar40', 'Instagram brian');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_aplicacion`
--

CREATE TABLE `usuarios_aplicacion` (
  `nom_usuario` varchar(50) NOT NULL,
  `contrasenya` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios_aplicacion`
--

INSERT INTO `usuarios_aplicacion` (`nom_usuario`, `contrasenya`) VALUES
('presentacion', '12345678'),
('usuario', '1223434345767875878'),
('usuario1', '12345678'),
('usuario2', 'password2'),
('usuario3', 'password3');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `credenciales_usuario`
--
ALTER TABLE `credenciales_usuario`
  ADD PRIMARY KEY (`id_contrasenya`),
  ADD KEY `nom_usuario` (`nom_usuario`);

--
-- Indices de la tabla `usuarios_aplicacion`
--
ALTER TABLE `usuarios_aplicacion`
  ADD PRIMARY KEY (`nom_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales_usuario`
--
ALTER TABLE `credenciales_usuario`
  MODIFY `id_contrasenya` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `credenciales_usuario`
--
ALTER TABLE `credenciales_usuario`
  ADD CONSTRAINT `credenciales_usuario_ibfk_1` FOREIGN KEY (`nom_usuario`) REFERENCES `usuarios_aplicacion` (`nom_usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
