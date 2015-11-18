
use `spring_security`;

DROP TABLE IF EXISTS `user_roles`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `USER_ID` INT(10) UNSIGNED NOT NULL,
  `USERNAME` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `ENABLED` tinyint(1) NOT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `user_roles` (
  `USER_ROLE_ID` INT(10) UNSIGNED NOT NULL,
  `USER_ID` INT(10) UNSIGNED NOT NULL,
  `AUTHORITY` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`USER_ROLE_ID`),
  KEY `FK_user_roles` (`USER_ID`),
  CONSTRAINT `FK_user_roles` FOREIGN KEY (`USER_ID`) REFERENCES `users` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO spring_security.users (USER_ID, USERNAME,PASSWORD, ENABLED) 
VALUES (100, 'admin', '123456', TRUE),(200, 'sysadmin', '123456', TRUE);
 
INSERT INTO spring_security.user_roles (USER_ROLE_ID, USER_ID,AUTHORITY) 
VALUES (1, 100, 'ROLE_USER'),(2, 200, 'ROLE_USER'),(3, 200, 'ROLE_ADMIN');