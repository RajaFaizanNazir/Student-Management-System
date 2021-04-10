-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 30, 2021 at 04:47 AM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 8.0.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentmanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `slot` int(2) NOT NULL DEFAULT 1,
  `day` varchar(20) NOT NULL,
  `teacherid` int(10) NOT NULL,
  `capacity` int(11) NOT NULL DEFAULT 5,
  `enrolled` int(2) NOT NULL DEFAULT 0,
  `cr` int(2) NOT NULL DEFAULT 3,
  `time` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`id`, `name`, `slot`, `day`, `teacherid`, `capacity`, `enrolled`, `cr`, `time`) VALUES
(1, 'Programming', 1, 'Monday,Monday', 1, 20, 0, 2, '2021-03-25 03:48:56.169947'),
(2, 'OOP', 2, 'Monday,Tuesday', 1, 20, 0, 4, '2021-03-29 08:15:36.145290'),
(3, 'Algo', 5, 'Tuesday,Thursday', 3, 20, 0, 3, '2021-03-29 08:20:25.794586'),
(4, 'Mboile Application', 4, 'Wednesday,Friday', 1, 20, 0, 3, '2021-03-29 19:20:27.002542');

-- --------------------------------------------------------

--
-- Table structure for table `request`
--

CREATE TABLE `request` (
  `id` int(10) NOT NULL,
  `studentid` int(10) NOT NULL,
  `courseid` int(10) NOT NULL,
  `status` varchar(15) NOT NULL DEFAULT 'Pending',
  `time` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `request`
--

INSERT INTO `request` (`id`, `studentid`, `courseid`, `status`, `time`) VALUES
(145, 1, 1, 'Approved', '2021-03-30 01:58:36.887673'),
(146, 1, 2, 'Approved', '2021-03-30 01:58:38.830899'),
(147, 1, 3, 'Declined', '2021-03-30 01:58:40.712481'),
(148, 1, 4, 'Approved', '2021-03-30 01:58:45.238989');

-- --------------------------------------------------------

--
-- Table structure for table `slot`
--

CREATE TABLE `slot` (
  `id` int(2) NOT NULL,
  `start` varchar(5) NOT NULL,
  `end` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `slot`
--

INSERT INTO `slot` (`id`, `start`, `end`) VALUES
(1, '8.00', '9.15'),
(2, '9.30', '10.45'),
(3, '11.00', '12.15'),
(4, '12.30', '1.45'),
(5, '2.00', '3.15'),
(6, '3.30', '4.45'),
(7, '5.00', '6.15');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL DEFAULT '123',
  `maxcr` int(2) NOT NULL DEFAULT 16,
  `time` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `name`, `email`, `password`, `maxcr`, `time`) VALUES
(1, 'Faizan', 'F2018065101', 'F2018065101', 2, '2021-03-25 05:44:14.856601');

-- --------------------------------------------------------

--
-- Table structure for table `teacher`
--

CREATE TABLE `teacher` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `maxcr` int(2) NOT NULL DEFAULT 16,
  `time` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `teacher`
--

INSERT INTO `teacher` (`id`, `name`, `email`, `maxcr`, `time`) VALUES
(1, 'Nabeel Sabir', 'nabeel.sabir@umt.edu.pk', 16, '2021-03-24 19:00:00.000000'),
(3, 'Mr.Tahir Ijjaz', 'Tahir.Ijjaz@umt.edu.pk', 8, '2021-03-29 08:20:00.596129');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `request`
--
ALTER TABLE `request`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `slot`
--
ALTER TABLE `slot`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `request`
--
ALTER TABLE `request`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=149;

--
-- AUTO_INCREMENT for table `slot`
--
ALTER TABLE `slot`
  MODIFY `id` int(2) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
