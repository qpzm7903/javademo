CREATE TABLE IF NOT EXISTS `ARTICLES`
(
    `id`     INTEGER PRIMARY KEY auto_increment,
    `title`  VARCHAR(100) NOT NULL,
    `author` VARCHAR(100) NOT NULL
);