-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 28 avr. 2025 à 20:18
-- Version du serveur :  10.4.14-MariaDB
-- Version de PHP : 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `crepehouse`
--

-- --------------------------------------------------------

--
-- Structure de la table `bill`
--

CREATE TABLE `bill` (
  `UniqueID` int(11) NOT NULL,
  `UniqueID_VENDOR` int(11) NOT NULL,
  `NUMBER` int(11) NOT NULL,
  `DATE` date NOT NULL,
  `TIME` time NOT NULL,
  `TOTAL_PRICE` double NOT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0,
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_AT` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `food`
--

CREATE TABLE `food` (
  `UniqueID` int(11) NOT NULL,
  `NAME_AR` varchar(100) CHARACTER SET utf8 NOT NULL,
  `NAME_FR` varchar(100) NOT NULL,
  `PRICE` double NOT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `PICTURE` longblob DEFAULT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0 COMMENT '0 = NON ARCHIVE / 1 = ARCHIVE',
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_AT` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `food`
--


-- --------------------------------------------------------

--
-- Structure de la table `food_bill`
--

CREATE TABLE `food_bill` (
  `UniqueID` int(11) NOT NULL,
  `UniqueID_BILL` int(11) NOT NULL,
  `UniqueID_FOOD` int(11) NOT NULL,
  `QTE` int(11) NOT NULL,
  `TOTAL_PRICE` int(11) NOT NULL,
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_AT` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `food_menu`
--

CREATE TABLE `food_menu` (
  `UniqueID` int(11) NOT NULL,
  `UniqueID_MENU` int(11) NOT NULL,
  `UniqueID_FOOD` int(11) NOT NULL,
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_AT` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `food_menu`
--

INSERT INTO `food_menu` (`UniqueID`, `UniqueID_MENU`, `UniqueID_FOOD`, `CREATE_AT`, `UPDATE_AT`) VALUES
(6, 3, 6, '2025-04-28 16:36:30', '2025-04-28 16:36:30'),
(7, 3, 7, '2025-04-28 16:36:30', '2025-04-28 16:36:30'),
(8, 4, 6, '2025-04-28 16:36:39', '2025-04-28 16:36:39'),
(9, 4, 7, '2025-04-28 16:36:39', '2025-04-28 16:36:39'),
(10, 4, 8, '2025-04-28 16:36:39', '2025-04-28 16:36:39'),
(11, 5, 6, '2025-04-28 16:42:32', '2025-04-28 16:42:32'),
(12, 5, 7, '2025-04-28 16:42:32', '2025-04-28 16:42:32');

-- --------------------------------------------------------

--
-- Structure de la table `menu`
--

CREATE TABLE `menu` (
  `UniqueID` int(11) NOT NULL,
  `SELECTED` tinyint(1) NOT NULL,
  `NAME` varchar(200) DEFAULT NULL,
  `DATE` date NOT NULL,
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_AT` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `menu`
--

INSERT INTO `menu` (`UniqueID`, `SELECTED`, `NAME`, `DATE`, `CREATE_AT`, `UPDATE_AT`) VALUES
(3, 0, 'test1', '2025-04-28', '2025-04-28 16:36:30', '2025-04-28 16:36:30'),
(4, 1, 'test 2', '2025-04-28', '2025-04-28 16:36:39', '2025-04-28 16:36:39'),
(5, 0, 'kaim', '2025-04-28', '2025-04-28 16:42:32', '2025-04-28 16:42:32');

-- --------------------------------------------------------

--
-- Structure de la table `vendor`
--

CREATE TABLE `vendor` (
  `UniqueID` int(11) NOT NULL,
  `NAME` varchar(300) NOT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `ARCHIVE` int(11) NOT NULL DEFAULT 0 COMMENT '0 = NON ARCIVE / 1 = ARCHIVE',
  `CREATE_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `UPDATE_AT` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `vendor`
--

INSERT INTO `vendor` (`UniqueID`, `NAME`, `PHONE`, `USERNAME`, `PASSWORD`, `ARCHIVE`, `CREATE_AT`, `UPDATE_AT`) VALUES
(2, 'ljd,', 'l:;m,d', ';ldm²', '1234', 0, '2025-04-23 09:57:34', '2025-04-23 09:57:34'),
(3, 'القايم', 'اتنبلا', ',lkmfed', '12', 0, '2025-04-23 09:59:42', '2025-04-23 10:11:16'),
(4, 'test', '1245', 'test', '1234', 0, '2025-04-28 11:09:09', '2025-04-28 11:09:37');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`UniqueID`),
  ADD KEY `UniqueID_VENDOR` (`UniqueID_VENDOR`);

--
-- Index pour la table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`UniqueID`);

--
-- Index pour la table `food_bill`
--
ALTER TABLE `food_bill`
  ADD PRIMARY KEY (`UniqueID`),
  ADD KEY `UniqueID_BILL` (`UniqueID_BILL`),
  ADD KEY `UniqueID_FOOD` (`UniqueID_FOOD`);

--
-- Index pour la table `food_menu`
--
ALTER TABLE `food_menu`
  ADD PRIMARY KEY (`UniqueID`),
  ADD KEY `UniqueID_FOOD` (`UniqueID_FOOD`),
  ADD KEY `UniqueID_MENU` (`UniqueID_MENU`);

--
-- Index pour la table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`UniqueID`);

--
-- Index pour la table `vendor`
--
ALTER TABLE `vendor`
  ADD PRIMARY KEY (`UniqueID`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bill`
--
ALTER TABLE `bill`
  MODIFY `UniqueID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `food`
--
ALTER TABLE `food`
  MODIFY `UniqueID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `food_bill`
--
ALTER TABLE `food_bill`
  MODIFY `UniqueID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `food_menu`
--
ALTER TABLE `food_menu`
  MODIFY `UniqueID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `menu`
--
ALTER TABLE `menu`
  MODIFY `UniqueID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `vendor`
--
ALTER TABLE `vendor`
  MODIFY `UniqueID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `bill_ibfk_1` FOREIGN KEY (`UniqueID_VENDOR`) REFERENCES `vendor` (`UniqueID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `food_bill`
--
ALTER TABLE `food_bill`
  ADD CONSTRAINT `food_bill_ibfk_1` FOREIGN KEY (`UniqueID_BILL`) REFERENCES `bill` (`UniqueID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `food_bill_ibfk_2` FOREIGN KEY (`UniqueID_FOOD`) REFERENCES `food` (`UniqueID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `food_menu`
--
ALTER TABLE `food_menu`
  ADD CONSTRAINT `food_menu_ibfk_1` FOREIGN KEY (`UniqueID_FOOD`) REFERENCES `food` (`UniqueID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `food_menu_ibfk_2` FOREIGN KEY (`UniqueID_MENU`) REFERENCES `menu` (`UniqueID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
