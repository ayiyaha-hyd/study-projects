use test;
create table if not exists `orders`(
    `id` int(20) not null auto_increment,
    `orderTime` date default null,
    `total` double default null,
    `uid` int(20) default null,
    primary key (`id`)
);