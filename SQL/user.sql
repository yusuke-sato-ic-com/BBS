CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` varchar(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(10) NOT NULL,
  `branch_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `using` TINYINT(1) NOT NULL default 0, 
  PRIMARY KEY (`id`)
  );
  
ALTER TABLE user ADD COLUMN
  branch_name VARCHAR(45) NOT NULL,
  department_name  VARCHAR(45) NOT NULL;
  
ALTER TABLE user DROP COLUMN
  branch_id,department_id;
  