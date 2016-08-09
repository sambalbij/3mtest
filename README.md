# 3mtest


# Database
To setup a database in the project, we have to add the right dependency. Therefore we add the jdbc starter to the pom. This adds a DataSource object that connects to the running mysql database.

Check connection
mysql -u root

Create a schema
Create a user with the right authorisations for the schema

CREATE TABLE `m3test`.`Events` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `finished` BIT(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC));
  
INSERT INTO `m3test`.`Events` (`name`,`finished`) VALUES ('Elasticon',0);
INSERT INTO `m3test`.`Events` (`name`,`finished`) VALUES ('Zomervakantie',1);

select * from Events where finished=1;

