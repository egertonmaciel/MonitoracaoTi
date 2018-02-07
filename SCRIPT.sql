CREATE DATABASE `crud_fichas`;

USE crud_fichas;

DROP TABLE IF EXISTS `crud_fichas`.`animal`;
CREATE TABLE  `crud_fichas`.`animal` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `crud_fichas`.`ficha`;
CREATE TABLE  `crud_fichas`.`ficha` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dt_cadastro` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  `observacao` varchar(300) NOT NULL DEFAULT ''
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `crud_fichas`.`animal_ficha`;
CREATE TABLE  `crud_fichas`.`animal_ficha` (
  `id_animal` int(11) NOT NULL DEFAULT '0',
  `id_ficha` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_animal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO animal VALUES (null,'Cachorro'),(null,'Gato'),(null,'Coelho'),(null,'Leão'),(null,'Rato'),(null,'Morcego');
