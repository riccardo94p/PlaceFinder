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
  `userId` int NOT NULL,
  `slotId` int NOT NULL,
  `roomId` varchar(45) NOT NULL,
  `timeStamp` datetime DEFAULT NULL,
  PRIMARY KEY (`userId`,`slotId`,`roomId`),
  KEY `fk_Reservation_1_idx` (`slotId`,`roomId`),
  CONSTRAINT `fk_Reservation_1` FOREIGN KEY (`slotId`, `roomId`) REFERENCES `Slot` (`id`, `idRoom`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservation`
--

LOCK TABLES `Reservation` WRITE;
/*!40000 ALTER TABLE `Reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Room`
--

DROP TABLE IF EXISTS `Room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Room` (
  `id` varchar(45) NOT NULL,
  `numSeats` int DEFAULT NULL,
  `capacity` int DEFAULT '1',
  PRIMARY KEY (`id`)
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
  `id` int NOT NULL,
  `idRoom` varchar(45) NOT NULL,
  `duration` int NOT NULL DEFAULT '2',
  `occupiedSeats` int DEFAULT '0',
  `status` tinyint DEFAULT '0',
  PRIMARY KEY (`id`,`idRoom`),
  KEY `fk_Slot_1_idx` (`idRoom`),
  CONSTRAINT `fk_Slot_1` FOREIGN KEY (`idRoom`) REFERENCES `Room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Slot`
--

LOCK TABLES `Slot` WRITE;
/*!40000 ALTER TABLE `Slot` DISABLE KEYS */;
INSERT INTO `Slot` VALUES (0,'a11',2,0,0),(0,'a12',2,0,0),(0,'a13',2,0,0),(0,'a14',2,0,0),(0,'b21',2,0,0),(0,'b22',2,0,0),(0,'b31',2,0,0),(0,'b34',2,0,0),(0,'f1',2,0,0),(0,'f2',2,0,0),(0,'f3',2,0,0),(0,'f4',2,0,0),(0,'f5',2,0,0),(0,'f6',2,0,0),(1,'a11',2,0,0),(1,'a12',2,0,0),(1,'a13',2,0,0),(1,'a14',2,0,0),(1,'b21',2,0,0),(1,'b22',2,0,0),(1,'b31',2,0,0),(1,'b34',2,0,0),(1,'f1',2,0,0),(1,'f2',2,0,0),(1,'f3',2,0,0),(1,'f4',2,0,0),(1,'f5',2,0,0),(1,'f6',2,0,0),(2,'a11',2,0,0),(2,'a12',2,0,0),(2,'a13',2,0,0),(2,'a14',2,0,0),(2,'b21',2,0,0),(2,'b22',2,0,0),(2,'b31',2,0,0),(2,'b34',2,0,0),(2,'f1',2,0,0),(2,'f2',2,0,0),(2,'f3',2,0,0),(2,'f4',2,0,0),(2,'f5',2,0,0),(2,'f6',2,0,0),(3,'a11',2,0,0),(3,'a12',2,0,0),(3,'a13',2,0,0),(3,'a14',2,0,0),(3,'b21',2,0,0),(3,'b22',2,0,0),(3,'b31',2,0,0),(3,'b34',2,0,0),(3,'f1',2,0,0),(3,'f2',2,0,0),(3,'f3',2,0,0),(3,'f4',2,0,0),(3,'f5',2,0,0),(3,'f6',2,0,0),(4,'a11',2,0,0),(4,'a12',2,0,0),(4,'a13',2,0,0),(4,'a14',2,0,0),(4,'b21',2,0,0),(4,'b22',2,0,0),(4,'b31',2,0,0),(4,'b34',2,0,0),(4,'f1',2,0,0),(4,'f2',2,0,0),(4,'f3',2,0,0),(4,'f4',2,0,0),(4,'f5',2,0,0),(4,'f6',2,0,0);
/*!40000 ALTER TABLE `Slot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `User` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `privilege` int NOT NULL DEFAULT '0',
  `covidNotification` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES (1,'aaa1','test',0,0),(2,'aaa2','test',0,0),(3,'bbb1','test',1,0),(4,'admin1','test',2,0);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL,
  `covid_notification` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `privilege` int DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-22 17:54:06
