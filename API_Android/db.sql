-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 30, 2024 at 01:47 AM
-- Server version: 10.5.20-MariaDB
-- PHP Version: 7.3.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id22152954_db_api_android`
--

-- --------------------------------------------------------

--
-- Table structure for table `couple`
--

CREATE TABLE `couple` (
  `couple_id` int(11) NOT NULL,
  `couple_timestart` datetime NOT NULL,
  `di_id` int(11) NOT NULL,
  `photo_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `couple`
--

INSERT INTO `couple` (`couple_id`, `couple_timestart`, `di_id`, `photo_id`) VALUES
(6, '2024-05-28 16:15:28', 51, 6),
(7, '2024-05-28 16:50:57', 57, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `date_invitation`
--

CREATE TABLE `date_invitation` (
  `di_id` int(11) NOT NULL,
  `di_timesend` datetime DEFAULT NULL,
  `di_timefeedback` datetime DEFAULT NULL,
  `di_is_accepted` bit(1) DEFAULT NULL,
  `receiver_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `date_invitation`
--

INSERT INTO `date_invitation` (`di_id`, `di_timesend`, `di_timefeedback`, `di_is_accepted`, `receiver_id`, `sender_id`) VALUES
(3, '2024-04-17 12:50:12', '2024-05-25 16:55:25', b'0', 4, 5),
(4, '2024-04-17 12:50:12', '2024-05-25 16:55:25', b'0', 4, 5),
(6, '2024-04-17 22:06:58', '2024-05-28 16:15:28', b'0', 1, 3),
(12, '2024-05-25 16:55:10', '2024-05-25 16:55:25', b'1', 4, 7),
(51, '2024-05-28 15:16:10', '2024-05-28 16:15:28', b'1', 9, 1),
(57, '2024-05-28 16:48:35', '2024-05-28 16:50:57', b'1', 6, 8);

-- --------------------------------------------------------

--
-- Table structure for table `farewell_request`
--

CREATE TABLE `farewell_request` (
  `fr_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `couple_id` int(11) NOT NULL,
  `fr_timesend` datetime DEFAULT NULL,
  `fr_timefeedback` datetime DEFAULT NULL,
  `fr_isaccept` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `message_id` int(11) NOT NULL,
  `couple_id` int(11) DEFAULT NULL,
  `sender_id` int(11) NOT NULL,
  `message_timesend` datetime DEFAULT NULL,
  `message_timeread` datetime DEFAULT NULL,
  `message_content` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_id`, `couple_id`, `sender_id`, `message_timesend`, `message_timeread`, `message_content`) VALUES
(12, 6, 1, '2024-05-28 22:00:27', '2024-05-28 22:04:21', 'hello baybe'),
(13, 6, 9, '2024-05-28 22:05:22', '2024-05-30 02:48:41', 'à yong xê ô');

-- --------------------------------------------------------

--
-- Table structure for table `photo`
--

CREATE TABLE `photo` (
  `photo_id` int(11) NOT NULL,
  `photo_publicid` varchar(50) NOT NULL,
  `photo_url` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `photo`
--

INSERT INTO `photo` (`photo_id`, `photo_publicid`, `photo_url`) VALUES
(1, 'iqwhptxcnnwarfqdvhii', 'https://res.cloudinary.com/drjqjxjts/image/upload/v1716617347/iqwhptxcnnwarfqdvhii.jpg'),
(2, 'fzb69hhh3uvew0g9vjdm', 'https://res.cloudinary.com/drjqjxjts/image/upload/v1715512432/fzb69hhh3uvew0g9vjdm.jpg'),
(3, 'qdpyzq1iccdqbcsihok3', 'https://res.cloudinary.com/drjqjxjts/image/upload/v1716629403/qdpyzq1iccdqbcsihok3.jpg'),
(4, 'vmtmhtm97m4gmkti66r9', 'https://res.cloudinary.com/drjqjxjts/image/upload/v1716752224/vmtmhtm97m4gmkti66r9.jpg'),
(5, 'nibgb5whs68xkdp8irjr', 'https://res.cloudinary.com/drjqjxjts/image/upload/v1716883116/nibgb5whs68xkdp8irjr.jpg'),
(6, 't21a062rblltzxpsc6rr', 'https://res.cloudinary.com/drjqjxjts/image/upload/v1717033332/t21a062rblltzxpsc6rr.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `schedule`
--

CREATE TABLE `schedule` (
  `schedule_id` int(11) NOT NULL,
  `couple_id` int(11) NOT NULL,
  `sender_id` int(11) NOT NULL,
  `schedule_time` datetime NOT NULL,
  `schedule_timesend` datetime NOT NULL,
  `schedule_timefeedback` datetime DEFAULT NULL,
  `schedule_isaccept` bit(1) DEFAULT NULL,
  `schedule_title` varchar(100) NOT NULL,
  `schedule_content` text DEFAULT NULL,
  `schedule_deleted` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `schedule`
--

INSERT INTO `schedule` (`schedule_id`, `couple_id`, `sender_id`, `schedule_time`, `schedule_timesend`, `schedule_timefeedback`, `schedule_isaccept`, `schedule_title`, `schedule_content`, `schedule_deleted`) VALUES
(34, 6, 9, '2024-06-01 18:30:00', '2024-05-28 16:17:40', '2024-05-30 02:49:31', b'0', 'đi ăn kem', 'đưa e đi ăn kem nè', b'0'),
(35, 6, 9, '2024-12-31 15:10:59', '2024-05-28 20:33:12', NULL, NULL, 'đi chơi', 'aaaaaaa', b'0'),
(36, 6, 9, '2024-06-01 20:00:00', '2024-05-28 21:49:14', NULL, NULL, 'abc', '', b'0'),
(37, 6, 9, '2024-06-01 10:00:00', '2024-05-28 21:50:15', '2024-05-30 02:49:30', b'1', 'cde', '', b'0'),
(38, 6, 9, '2024-06-02 09:30:00', '2024-05-28 21:51:15', NULL, NULL, 'qwe', '', b'0'),
(39, 6, 9, '2024-06-03 10:00:00', '2024-05-28 21:52:04', NULL, NULL, 'haha', 'xxx', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `user_fullname` varchar(30) DEFAULT NULL,
  `user_alias` varchar(30) DEFAULT NULL,
  `user_gender` bit(1) DEFAULT NULL,
  `user_dob` date DEFAULT NULL,
  `user_lifestory` text DEFAULT NULL,
  `user_email` text NOT NULL,
  `user_username` varchar(30) NOT NULL,
  `user_password` text NOT NULL,
  `user_locked` bit(1) DEFAULT b'0',
  `user_timecreate` datetime DEFAULT NULL,
  `photo_id` int(11) DEFAULT NULL,
  `user_authen_code` char(6) DEFAULT NULL,
  `user_authen_timecreate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `user_fullname`, `user_alias`, `user_gender`, `user_dob`, `user_lifestory`, `user_email`, `user_username`, `user_password`, `user_locked`, `user_timecreate`, `photo_id`, `user_authen_code`, `user_authen_timecreate`) VALUES
(1, 'Nguyến Văn Thường', 'Thường', b'1', '2003-11-10', 'Chậu đã có hoa. Đừng đập chậu, hoa không vui!', 'nguyenvanthuong10112003@gmail.com', 'thuong2003', 'namdinh1', b'0', '2024-03-27 12:57:47', 1, '276663', '2024-05-28 15:58:04'),
(3, 'Nguyễn Thị T', 'T', b'0', '2003-11-11', NULL, 't@gmail.com', 'thuong', 'namdinh1', b'0', '2024-04-16 10:16:11', NULL, NULL, NULL),
(4, 'Nguyễn Đức Du', 'Du', b'1', '2003-12-19', NULL, 'du@gmail.com', 'du', 'namdinh1', b'0', '2024-04-16 10:45:00', NULL, NULL, NULL),
(5, 'Nguyễn Hà Phương', 'Phương', b'0', '2003-12-19', NULL, 'phuong@gmail.com', 'phuong', 'namdinh1', b'0', '2024-04-16 10:46:00', NULL, NULL, NULL),
(6, 'Nguyễn Tú', 'Tú', b'0', '2003-11-10', '', 'tu@gmail.com', 'tuxinh', 'namdinh1', b'0', '2024-04-16 10:47:00', 2, NULL, NULL),
(7, 'abcdefg', 'abcdefg', b'1', '2003-12-11', '', 'abcd@gmail.com', 'abcd', 'namdinh1', b'0', '2024-05-25 15:08:08', 3, NULL, NULL),
(8, 'Minhdz', 'minhdz', b'1', '2003-05-14', NULL, 'minhdz@gmail.com', 'minhdz', '123456', b'0', '2024-05-28 11:35:42', NULL, '937791', '2024-05-28 16:44:57'),
(9, 'Vũ Tiến Đạt', 'cccccccccccccc', b'1', '2003-03-06', 'm bt bo m la ai ko', 'dat@gmail.com', 'datAbcd', 'datdat', b'0', '2024-05-28 14:55:06', 5, NULL, NULL),
(10, NULL, NULL, NULL, NULL, NULL, 'minh@gmail.com', 'm', '@@@@@@', b'0', '2024-05-29 09:50:59', NULL, NULL, NULL),
(11, NULL, NULL, NULL, NULL, NULL, 'test@gmail.com', 'test', '123456', b'0', '2024-05-29 10:13:58', NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `couple`
--
ALTER TABLE `couple`
  ADD PRIMARY KEY (`couple_id`),
  ADD KEY `couple_fk1` (`di_id`),
  ADD KEY `couple_fk2` (`photo_id`);

--
-- Indexes for table `date_invitation`
--
ALTER TABLE `date_invitation`
  ADD PRIMARY KEY (`di_id`),
  ADD KEY `di_fk1` (`receiver_id`),
  ADD KEY `di_fk2` (`sender_id`);

--
-- Indexes for table `farewell_request`
--
ALTER TABLE `farewell_request`
  ADD PRIMARY KEY (`fr_id`),
  ADD KEY `sender_id` (`sender_id`),
  ADD KEY `couple_id` (`couple_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `couple_id` (`couple_id`),
  ADD KEY `sender_id` (`sender_id`);

--
-- Indexes for table `photo`
--
ALTER TABLE `photo`
  ADD PRIMARY KEY (`photo_id`);

--
-- Indexes for table `schedule`
--
ALTER TABLE `schedule`
  ADD PRIMARY KEY (`schedule_id`),
  ADD KEY `couple_id` (`couple_id`),
  ADD KEY `sender_id` (`sender_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `user_username` (`user_username`),
  ADD UNIQUE KEY `user_email` (`user_email`) USING HASH,
  ADD KEY `user_fk1` (`photo_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `couple`
--
ALTER TABLE `couple`
  MODIFY `couple_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `date_invitation`
--
ALTER TABLE `date_invitation`
  MODIFY `di_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=58;

--
-- AUTO_INCREMENT for table `farewell_request`
--
ALTER TABLE `farewell_request`
  MODIFY `fr_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `message_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `photo`
--
ALTER TABLE `photo`
  MODIFY `photo_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `schedule`
--
ALTER TABLE `schedule`
  MODIFY `schedule_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `couple`
--
ALTER TABLE `couple`
  ADD CONSTRAINT `couple_fk1` FOREIGN KEY (`di_id`) REFERENCES `date_invitation` (`di_id`),
  ADD CONSTRAINT `couple_fk2` FOREIGN KEY (`photo_id`) REFERENCES `photo` (`photo_id`);

--
-- Constraints for table `date_invitation`
--
ALTER TABLE `date_invitation`
  ADD CONSTRAINT `invite_fk1` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `invite_fk2` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `farewell_request`
--
ALTER TABLE `farewell_request`
  ADD CONSTRAINT `farewell_request_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `farewell_request_ibfk_2` FOREIGN KEY (`couple_id`) REFERENCES `couple` (`couple_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`couple_id`) REFERENCES `couple` (`couple_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `message_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `schedule`
--
ALTER TABLE `schedule`
  ADD CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`couple_id`) REFERENCES `couple` (`couple_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_fk1` FOREIGN KEY (`photo_id`) REFERENCES `photo` (`photo_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
