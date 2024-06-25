show databases;
create database dbHomework;

use dbHomework;

-- ————————————————————————————类别：外卖员1，商家2，客户3
    create  table  category(
        id int,
        name varchar(16)
    );
insert into category (id, name)
values (1,'外卖员'),(2,'商家'),(3,'顾客');

select * from category;

-- ————————————————————————用户表————————————————————
create table users(
    account int,-- 账号
    password varchar(16),-- 密码
    username varchar(8),-- 用户名
    picture varchar(128),-- 头像地址
    category int
);

-- alter table users modif column password  varchar(32);
alter table users modify column password varchar(32);
-- 只是加上id看看。
alter table users add column id int primary key auto_increment;

insert into users(account,password,username) values (1,'2','l');
delete  from users where picture is null;
delete from users where username='test3';

alter table users add column balance int default 0;

update users set balance=0 where balance<>0;

delete from users where username like 'test%';
select * from users;

/*自增主键：id int primary key auto_increment,*/

-- ————————————————————————菜单表————————————————————
create table menus(
    seller int ,
    name varchar(16),
    price int
);

delete from menus where seller is not null;
delete from menus where seller=366659464;
select * from menus;



-- ————————————————————————订单表————————————————————
create  table  orders(
    num int primary key auto_increment,
    seller int,
    name varchar(16),
    customer int,
    rider int
);

drop table orders;
delete from orders where rider is null or rider is not null;
select * from orders;



