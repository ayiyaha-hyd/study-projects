use test;
create table if not exists `user` (
    `id` int(11) not null auto_increment,
    `username` varchar(11) default null,
    `password` varchar(22) default null,
    primary key(`id`)
)engine=innodb auto_increment=10 default charset=utf8;

insert into `user` (`username`,`password`) values ('Tom','123456');
insert into `user` (`username`,`password`) values ('Jerry','654321');