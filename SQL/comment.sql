CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  `text` varchar(500) NOT NULL,
  `insert_date` timestamp NOT NULL,
  `update_date` timestamp NOT NULL,
  PRIMARY KEY (`id`)
