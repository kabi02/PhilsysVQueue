-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 18, 2023 at 12:19 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `admin`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin_acc`
--

CREATE TABLE `admin_acc` (
  `admin_un` varchar(10) NOT NULL,
  `admin_pw` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin_acc`
--

INSERT INTO `admin_acc` (`admin_un`, `admin_pw`) VALUES
('admin', 'admin123');

-- --------------------------------------------------------

--
-- Table structure for table `application_status`
--

CREATE TABLE `application_status` (
  `userID` int(11) NOT NULL,
  `transaction_date` date DEFAULT NULL,
  `status` enum('PROCESS','NONE','DECLINED','CLAIMING','BIOMETRICS','CLAIMED') DEFAULT NULL,
  `CTN` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `application_status`
--

INSERT INTO `application_status` (`userID`, `transaction_date`, `status`, `CTN`) VALUES
(1, '2023-02-18', 'PROCESS', '9485019172023-02-18');

-- --------------------------------------------------------

--
-- Table structure for table `userlogincreds`
--

CREATE TABLE `userlogincreds` (
  `userID` int(11) NOT NULL,
  `userEmail` varchar(100) DEFAULT NULL,
  `userPass` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userlogincreds`
--

INSERT INTO `userlogincreds` (`userID`, `userEmail`, `userPass`) VALUES
(1, 'myemail@gmail.com', 'qwerty'),
(2, 'newemail@gmail.com', 'qwerty');

-- --------------------------------------------------------

--
-- Table structure for table `user_address`
--

CREATE TABLE `user_address` (
  `userID` int(11) NOT NULL,
  `unit_no` varchar(10) DEFAULT NULL,
  `house_no` varchar(10) DEFAULT NULL,
  `street` varchar(100) DEFAULT NULL,
  `barangay_or_subd` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_address`
--

INSERT INTO `user_address` (`userID`, `unit_no`, `house_no`, `street`, `barangay_or_subd`, `city`, `province`, `country`) VALUES
(1, 'asdads', 'adsasd', 'sadasd', 'adsasddas', 'asdsad', 'asdasd', 'adsads');

-- --------------------------------------------------------

--
-- Table structure for table `user_personal_info`
--

CREATE TABLE `user_personal_info` (
  `userID` int(11) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  `blood_type` varchar(3) DEFAULT NULL,
  `nationality` varchar(50) DEFAULT NULL,
  `marital_status` varchar(20) DEFAULT NULL,
  `mname` varchar(100) DEFAULT NULL,
  `lname` varchar(100) DEFAULT NULL,
  `fname` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_personal_info`
--

INSERT INTO `user_personal_info` (`userID`, `birthdate`, `sex`, `blood_type`, `nationality`, `marital_status`, `mname`, `lname`, `fname`) VALUES
(1, '2023-02-06', 'M', 'A+', 'Filipino', 'Single', 'asdsad', 'asads', 'asdasdasasd');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin_acc`
--
ALTER TABLE `admin_acc`
  ADD PRIMARY KEY (`admin_un`);

--
-- Indexes for table `application_status`
--
ALTER TABLE `application_status`
  ADD PRIMARY KEY (`CTN`),
  ADD UNIQUE KEY `userID` (`userID`),
  ADD UNIQUE KEY `CTN` (`CTN`);

--
-- Indexes for table `userlogincreds`
--
ALTER TABLE `userlogincreds`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `user_address`
--
ALTER TABLE `user_address`
  ADD PRIMARY KEY (`userID`);

--
-- Indexes for table `user_personal_info`
--
ALTER TABLE `user_personal_info`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `userlogincreds`
--
ALTER TABLE `userlogincreds`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user_address`
--
ALTER TABLE `user_address`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1668051282;

--
-- AUTO_INCREMENT for table `user_personal_info`
--
ALTER TABLE `user_personal_info`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1668051282;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `application_status`
--
ALTER TABLE `application_status`
  ADD CONSTRAINT `application_status_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `userlogincreds` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_address`
--
ALTER TABLE `user_address`
  ADD CONSTRAINT `useraddress_fk` FOREIGN KEY (`userID`) REFERENCES `application_status` (`userID`) ON DELETE CASCADE;

--
-- Constraints for table `user_personal_info`
--
ALTER TABLE `user_personal_info`
  ADD CONSTRAINT `personal_info_fk` FOREIGN KEY (`userID`) REFERENCES `application_status` (`userID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
