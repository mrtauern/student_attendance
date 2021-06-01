-- MySQL dump 10.13  Distrib 8.0.21, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: student_attendance
-- ------------------------------------------------------
-- Server version	5.7.26

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) NOT NULL,
  `time` datetime DEFAULT NULL,
  `networkVerified` tinyint(1) DEFAULT NULL,
  `sessionId_fk` int(11) NOT NULL,
  `studentId_fk` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `session_fk` (`sessionId_fk`),
  KEY `student_fk` (`studentId_fk`),
  CONSTRAINT `session_fk` FOREIGN KEY (`sessionId_fk`) REFERENCES `sessions` (`id`),
  CONSTRAINT `student_fk` FOREIGN KEY (`studentId_fk`) REFERENCES `students` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES (1,1,'2021-05-03 10:15:00',1,1,4),(9,1,'2021-05-11 10:56:32',1,1,5),(11,1,'2021-05-30 22:01:42',0,2,4);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classname` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `classes`
--

LOCK TABLES `classes` WRITE;
/*!40000 ALTER TABLE `classes` DISABLE KEYS */;
INSERT INTO `classes` VALUES (1,'sd20i'),(2,'Dat18v2');
/*!40000 ALTER TABLE `classes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courseClass`
--

DROP TABLE IF EXISTS `courseClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courseClass` (
  `courseId_fk` int(11) NOT NULL,
  `classId_fk` int(11) NOT NULL,
  KEY `class4_fk` (`classId_fk`),
  KEY `course2_fk` (`courseId_fk`),
  CONSTRAINT `class4_fk` FOREIGN KEY (`classId_fk`) REFERENCES `classes` (`id`),
  CONSTRAINT `course2_fk` FOREIGN KEY (`courseId_fk`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courseClass`
--

LOCK TABLES `courseClass` WRITE;
/*!40000 ALTER TABLE `courseClass` DISABLE KEYS */;
INSERT INTO `courseClass` VALUES (1,1),(2,1),(3,2),(4,2);
/*!40000 ALTER TABLE `courseClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES (1,'sd12'),(2,'sc13'),(3,'db9'),(4,'st10'),(5,'ws18');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ipranges`
--

DROP TABLE IF EXISTS `ipranges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ipranges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ipFromPart1` int(3) NOT NULL,
  `ipFromPart2` int(3) NOT NULL,
  `ipFromPart3` int(3) NOT NULL,
  `ipFromPart4` int(3) NOT NULL,
  `ipToPart1` int(3) NOT NULL,
  `ipToPart2` int(3) NOT NULL,
  `ipToPart3` int(3) NOT NULL,
  `ipToPart4` int(3) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ipranges`
--

LOCK TABLES `ipranges` WRITE;
/*!40000 ALTER TABLE `ipranges` DISABLE KEYS */;
INSERT INTO `ipranges` VALUES (1,1,1,1,1,255,255,255,255);
/*!40000 ALTER TABLE `ipranges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `courseId_fk` int(11) NOT NULL,
  `sessionCode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_fk` (`courseId_fk`),
  CONSTRAINT `course_fk` FOREIGN KEY (`courseId_fk`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (1,'2021-05-03 10:15:00',1,'4H2J'),(2,'2021-05-03 11:15:00',1,'83GD'),(3,'2021-05-04 08:30:00',3,'5JHS'),(4,'2021-05-04 09:15:00',3,'48B2'),(5,'2021-05-31 16:25:00',2,'69OD'),(6,'2021-05-28 08:25:00',5,'0AT3');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentClass`
--

DROP TABLE IF EXISTS `studentClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentClass` (
  `studentId_fk` int(11) NOT NULL,
  `classId_fk` int(11) NOT NULL,
  KEY `student2_fk` (`studentId_fk`),
  KEY `class2_fk` (`classId_fk`),
  CONSTRAINT `class2_fk` FOREIGN KEY (`classId_fk`) REFERENCES `classes` (`id`),
  CONSTRAINT `student2_fk` FOREIGN KEY (`studentId_fk`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentClass`
--

LOCK TABLES `studentClass` WRITE;
/*!40000 ALTER TABLE `studentClass` DISABLE KEYS */;
INSERT INTO `studentClass` VALUES (4,1),(5,1),(6,1),(1,2),(2,2),(3,2);
/*!40000 ALTER TABLE `studentClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentCourse`
--

DROP TABLE IF EXISTS `studentCourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studentCourse` (
  `studentId_fk` int(11) NOT NULL,
  `courseId_fk` int(11) NOT NULL,
  KEY `course4_fk` (`courseId_fk`),
  KEY `student5_fk` (`studentId_fk`),
  CONSTRAINT `course3_fk` FOREIGN KEY (`courseId_fk`) REFERENCES `courses` (`id`),
  CONSTRAINT `course4_fk` FOREIGN KEY (`courseId_fk`) REFERENCES `courses` (`id`),
  CONSTRAINT `student5_fk` FOREIGN KEY (`studentId_fk`) REFERENCES `students` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentCourse`
--

LOCK TABLES `studentCourse` WRITE;
/*!40000 ALTER TABLE `studentCourse` DISABLE KEYS */;
INSERT INTO `studentCourse` VALUES (3,5),(5,5),(7,1),(8,1);
/*!40000 ALTER TABLE `studentCourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'Henrik','Handskesen','heha1234@stud.kea.dk','123456'),(2,'Kevin','Carlsen','keca6473@stud.kea.dk','123456'),(3,'Michael','Money','mimo6447@stud.kea.dk','123456'),(4,'Sofie','Hansen','soha5629@stud.kea.dk','123456'),(5,'Hanna','Henriksen','hahe6538@stud.kea.dk','123456'),(6,'Sune','Manfredsen','suma7285@stud.kea.dk','123456'),(7,'Bent','Hansen','beha7374@stud.kea.dk','123456'),(8,'Janni','Svendsen','jasv8463@stud.kea.dk','123456');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacherClass`
--

DROP TABLE IF EXISTS `teacherClass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacherClass` (
  `teacherId_fk` int(11) NOT NULL,
  `classId_fk` int(11) NOT NULL,
  KEY `class3_fk` (`classId_fk`),
  KEY `teacher_fk` (`teacherId_fk`),
  CONSTRAINT `class3_fk` FOREIGN KEY (`classId_fk`) REFERENCES `classes` (`id`),
  CONSTRAINT `teacher_fk` FOREIGN KEY (`teacherId_fk`) REFERENCES `teachers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacherClass`
--

LOCK TABLES `teacherClass` WRITE;
/*!40000 ALTER TABLE `teacherClass` DISABLE KEYS */;
/*!40000 ALTER TABLE `teacherClass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacherCourse`
--

DROP TABLE IF EXISTS `teacherCourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacherCourse` (
  `teacherId_fk` int(11) NOT NULL,
  `courseId_fk` int(11) NOT NULL,
  KEY `course5_fk` (`courseId_fk`),
  KEY `teacher3_fk` (`teacherId_fk`),
  CONSTRAINT `course5_fk` FOREIGN KEY (`courseId_fk`) REFERENCES `courses` (`id`),
  CONSTRAINT `teacher3_fk` FOREIGN KEY (`teacherId_fk`) REFERENCES `teachers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacherCourse`
--

LOCK TABLES `teacherCourse` WRITE;
/*!40000 ALTER TABLE `teacherCourse` DISABLE KEYS */;
INSERT INTO `teacherCourse` VALUES (1,2),(2,1),(2,4),(1,3),(1,5);
/*!40000 ALTER TABLE `teacherCourse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers`
--

DROP TABLE IF EXISTS `teachers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers`
--

LOCK TABLES `teachers` WRITE;
/*!40000 ALTER TABLE `teachers` DISABLE KEYS */;
INSERT INTO `teachers` VALUES (1,'Poul','Svendsen','psv@kea.dk','123456'),(2,'Palle','Hansen','pha@kea.dk','123456');
/*!40000 ALTER TABLE `teachers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'student_attendance'
--
/*!50003 DROP PROCEDURE IF EXISTS `get_course_attendance_by_student` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_course_attendance_by_student`(IN student_id int)
BEGIN

SELECT
    x.courseID,
    x.courseName,
    x.studentId,
    x.studentFirstname,
    x.studentLastname,
    x.session,
    IFNULL(x.attended, 0) AS attended
FROM
(
    SELECT
       c.id AS courseID,
       c.name AS courseName,
       s.id AS studentId,
       s.firstname AS studentFirstname,
       s.lastname AS studentLastname,
       SUM(1) AS session,
        SUM((SELECT status FROM attendance a WHERE a.sessionId_fk = ss.id AND a.studentId_fk = s.id)) AS attended
    FROM courses c
        JOIN studentCourse sC on c.id = sC.courseId_fk
        JOIN students s on s.id = sC.studentId_fk
        JOIN sessions ss on c.id = ss.courseId_fk
    WHERE s.id = student_id
    GROUP BY courseID

    UNION ALL

    SELECT
       c.id AS courseID,
       c.name AS courseName,
       s.id AS studentId,
       s.firstname AS studentFirstname,
       s.lastname AS studentLastname,
       SUM(1) AS session,
        SUM((SELECT status FROM attendance a WHERE a.sessionId_fk = ss.id AND a.studentId_fk = s.id)) AS attended
    FROM courses c
        JOIN courseClass cC on c.id = cC.courseId_fk
        JOIN classes c2 on c2.id = cC.classId_fk
        JOIN studentClass sC on c2.id = sC.classId_fk
        JOIN students s on s.id = sC.studentId_fk
        JOIN sessions ss on c.id = ss.courseId_fk
    WHERE s.id = student_id
    GROUP BY courseID
)x;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_student_attendance_by_course` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_student_attendance_by_course`(IN course_id int)
BEGIN

SELECT
    x.courseID,
    x.courseName,
    x.studentId,
    x.studentFirstname,
    x.studentLastname,
    x.session,
    IFNULL(x.attended, 0) AS attended
FROM
(
    SELECT
       c.id AS courseID,
       c.name AS courseName,
       s.id AS studentId,
       s.firstname AS studentFirstname,
       s.lastname AS studentLastname,
       SUM(1) AS session,
        SUM((SELECT status FROM attendance a WHERE a.sessionId_fk = ss.id AND a.studentId_fk = s.id)) AS attended
    FROM courses c
    JOIN studentCourse sC on c.id = sC.courseId_fk
    JOIN students s on s.id = sC.studentId_fk
    JOIN sessions ss on c.id = ss.courseId_fk
    WHERE c.id = course_id
    GROUP BY studentId

    UNION ALL

    SELECT
       c.id AS courseID,
       c.name AS courseName,
       s.id AS studentId,
       s.firstname AS studentFirstname,
       s.lastname AS studentLastname,
       SUM(1) AS session,
        SUM((SELECT status FROM attendance a WHERE a.sessionId_fk = ss.id AND a.studentId_fk = s.id)) AS attended
    FROM courses c
    JOIN courseClass cC on c.id = cC.courseId_fk
    JOIN classes c2 on c2.id = cC.classId_fk
    JOIN studentClass sC on c2.id = sC.classId_fk
    JOIN students s on s.id = sC.studentId_fk
    JOIN sessions ss on c.id = ss.courseId_fk
    WHERE c.id = course_id
    GROUP BY studentId
)x;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_total_attendance_by_student` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_total_attendance_by_student`(IN student_id int)
BEGIN

SELECT
    x.studentId,
    SUM(x.session) AS session,
    IFNULL(SUM(x.attended), 0) AS attended
FROM
(
    SELECT
       s.id AS studentId,
       s.firstname AS studentFirstname,
       s.lastname AS studentLastname,
       SUM(1) AS session,
        SUM((SELECT status FROM attendance a WHERE a.sessionId_fk = ss.id AND a.studentId_fk = s.id)) AS attended
    FROM courses c
        JOIN studentCourse sC on c.id = sC.courseId_fk
        JOIN students s on s.id = sC.studentId_fk
        JOIN sessions ss on c.id = ss.courseId_fk
    WHERE s.id = student_id
    GROUP BY studentId

    UNION ALL

    SELECT
       s.id AS studentId,
       s.firstname AS studentFirstname,
       s.lastname AS studentLastname,
       SUM(1) AS session,
        SUM((SELECT status FROM attendance a WHERE a.sessionId_fk = ss.id AND a.studentId_fk = s.id)) AS attended
    FROM courses c
        JOIN courseClass cC on c.id = cC.courseId_fk
        JOIN classes c2 on c2.id = cC.classId_fk
        JOIN studentClass sC on c2.id = sC.classId_fk
        JOIN students s on s.id = sC.studentId_fk
        JOIN sessions ss on c.id = ss.courseId_fk
    WHERE s.id = student_id
    GROUP BY studentId
)x
GROUP BY studentId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-01 11:16:22
