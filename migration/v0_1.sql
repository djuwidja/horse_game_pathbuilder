CREATE DATABASE  IF NOT EXISTS `horse_game` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `horse_game`;
-- MySQL dump 10.13  Distrib 8.0.20, for Linux (x86_64)
--
-- Host: localhost    Database: horse_game
-- ------------------------------------------------------
-- Server version	8.0.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `race_track`
--

DROP TABLE IF EXISTS `race_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `race_track` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `terrain_type` int unsigned NOT NULL,
  `track_type` int unsigned NOT NULL,
  `finish_line_pt1_x` decimal(10,5) NOT NULL,
  `finish_line_pt1_z` decimal(10,5) NOT NULL,
  `finish_line_pt2_x` decimal(10,5) NOT NULL,
  `finish_line_pt2_z` decimal(10,5) NOT NULL,
  `control_pt_x` decimal(10,5) NOT NULL,
  `control_pt_z` decimal(10,5) NOT NULL,
  `finish_line_activation` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `race_track`
--

LOCK TABLES `race_track` WRITE;
/*!40000 ALTER TABLE `race_track` DISABLE KEYS */;
/*!40000 ALTER TABLE `race_track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `start_point`
--

DROP TABLE IF EXISTS `start_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `start_point` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `set_id` int unsigned NOT NULL,
  `lane_id` int unsigned NOT NULL,
  `x` decimal(10,5) NOT NULL,
  `z` decimal(10,5) NOT NULL,
  `vec_x` decimal(10,5) NOT NULL,
  `vec_z` decimal(10,5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `start_point`
--

LOCK TABLES `start_point` WRITE;
/*!40000 ALTER TABLE `start_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `start_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `track_trigger`
--

DROP TABLE IF EXISTS `track_trigger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_trigger` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `track_id` int unsigned NOT NULL,
  `type` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_trigger`
--

LOCK TABLES `track_trigger` WRITE;
/*!40000 ALTER TABLE `track_trigger` DISABLE KEYS */;
/*!40000 ALTER TABLE `track_trigger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `track_trigger_line`
--

DROP TABLE IF EXISTS `track_trigger_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_trigger_line` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `track_trigger_id` int unsigned NOT NULL,
  `pt1_x` decimal(10,5) NOT NULL,
  `pt1_z` decimal(10,5) NOT NULL,
  `pt2_x` decimal(10,5) NOT NULL,
  `pt2_z` decimal(10,5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `track_trigger_id_UNIQUE` (`track_trigger_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_trigger_line`
--

LOCK TABLES `track_trigger_line` WRITE;
/*!40000 ALTER TABLE `track_trigger_line` DISABLE KEYS */;
/*!40000 ALTER TABLE `track_trigger_line` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `track_vector`
--

DROP TABLE IF EXISTS `track_vector`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_vector` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `track_id` int unsigned NOT NULL,
  `x` decimal(10,5) NOT NULL,
  `z` decimal(10,5) NOT NULL,
  `seq` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_vector`
--

LOCK TABLES `track_vector` WRITE;
/*!40000 ALTER TABLE `track_vector` DISABLE KEYS */;
/*!40000 ALTER TABLE `track_vector` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-06-10 13:46:07
