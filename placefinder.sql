CREATE DATABASE  IF NOT EXISTS `placefinder` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `placefinder`;
-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: placefinder
-- ------------------------------------------------------
-- Server version	8.0.22-0ubuntu0.20.04.3

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
-- Table structure for table `Reservation`
--

DROP TABLE IF EXISTS `Reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Reservation` (
  `userId` varchar(45) NOT NULL,
  `slotId` int NOT NULL,
  `roomId` varchar(45) NOT NULL,
  `reservationDate` date NOT NULL,
  PRIMARY KEY (`userId`,`roomId`,`reservationDate`,`slotId`),
  KEY `fk_Reservation_2_idx` (`slotId`),
  KEY `fk_Reservation_3_idx` (`roomId`),
  CONSTRAINT `fk_reservation_1` FOREIGN KEY (`userId`) REFERENCES `User` (`username`),
  CONSTRAINT `fk_reservation_2` FOREIGN KEY (`slotId`) REFERENCES `Slot` (`idSlot`),
  CONSTRAINT `fk_Reservation_3` FOREIGN KEY (`roomId`) REFERENCES `Room` (`idRoom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservation`
--

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;
INSERT INTO `Reservation` VALUES ('aaa1',0,'a11','2021-01-23'),('aaa2',0,'a11','2021-01-23'),('aaa3',0,'a11','2021-01-23'),('aaa6',0,'a11','2021-01-23'),('zzzz',0,'a11','2021-01-20'),('aaa2',1,'a11','2021-01-23'),('bbb1',1,'b21','2021-01-23'),('bbb1',1,'f4','2021-01-10'),('aaa1',3,'f6','2021-01-20'),('aaa4',3,'f6','2021-01-20'),('aaa1',4,'f1','2021-01-16'),('aaa2',4,'f1','2021-01-10');
/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `idRoom` varchar(45) NOT NULL,
  `numSeats` int DEFAULT NULL,
  `capacity` float DEFAULT '1',
  PRIMARY KEY (`idRoom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Room`
--

LOCK TABLES `Room` WRITE;
/*!40000 ALTER TABLE `Room` DISABLE KEYS */;
INSERT INTO `Room` VALUES ('a11',50,1),('a12',50,1),('a13',50,1),('a14',50,1),('b12',200,1),('b21',200,1),('b22',30,1),('b31',150,1),('b34',50,1),('f1',150,1),('f2',150,1),('f3',150,1),('f4',150,1),('f5',150,1),('f6',200,1);
/*!40000 ALTER TABLE `Room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Slot`
--

DROP TABLE IF EXISTS `Slot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Slot` (
  `idSlot` int NOT NULL,
  `startTime` time DEFAULT NULL,
  `endTime` time DEFAULT NULL,
  PRIMARY KEY (`idSlot`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Slot`
--

LOCK TABLES `Slot` WRITE;
/*!40000 ALTER TABLE `Slot` DISABLE KEYS */;
INSERT INTO `Slot` VALUES (0,'09:00:00','11:00:00'),(1,'11:00:00','13:00:00'),(2,'14:00:00','16:00:00'),(3,'16:00:00','18:00:00'),(4,'18:00:00','20:00:00');
/*!40000 ALTER TABLE `Slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL DEFAULT 'STUDENT',
  `covidNotification` tinyint DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('aaa1','test','STUDENT',0),('aaa2','test','STUDENT',0),('aaa3','test','STUDENT',0),('aaa4','test','STUDENT',0),('aaa5','test','STUDENT',0),('aaa6','test','STUDENT',0),('aaa7','test','STUDENT',0),('admin1','test','ADMIN',0),('bbb1','test','PROF',0),('zzzz','test','STUDENT',0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-26 22:02:06
