CREATE TABLE IF NOT EXISTS `ARTICLES`
(
    `id`     INTEGER PRIMARY KEY auto_increment,
    `title`  VARCHAR(100) NOT NULL,
    `author` VARCHAR(100) NOT NULL
);

create unique index if not exists `uk_title` on `ARTICLES` (`title`);