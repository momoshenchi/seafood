/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/7/3 13:43:08                            */
/*==============================================================*/


drop table if exists address;

drop table if exists admin;

drop table if exists commidity_menu;

drop table if exists commodity;

drop table if exists commodity_discount;

drop table if exists coupon;

drop table if exists customer_comment;

drop table if exists discou;

drop table if exists foodtype;

drop table if exists menu;

drop table if exists order_detail;

drop table if exists orders;

drop table if exists purchase;

drop table if exists sale;

drop table if exists users;

/*==============================================================*/
/* Table: address                                               */
/*==============================================================*/
create table address
(
   addressid            int not null,
   userid               int,
   detail_add           varchar(50),
   contactname          varchar(50),
   phonenum             varchar(50),
   primary key (addressid)
);

/*==============================================================*/
/* Table: admin                                                 */
/*==============================================================*/
create table admin
(
   adminid              int not null,
   adminname            varchar(50),
   pwd                  varchar(50),
   primary key (adminid)
);

/*==============================================================*/
/* Table: commidity_menu                                        */
/*==============================================================*/
create table commidity_menu
(
   commodityid          int not null,
   menuid               int not null,
   description          varchar(50),
   primary key (commodityid, menuid)
);

/*==============================================================*/
/* Table: commodity                                             */
/*==============================================================*/
create table commodity
(
   commodityid          int not null,
   commodityname        varchar(50),
   price                double,
   vipprice             double,
   number               int,
   spec                 varchar(50),
   detail               varchar(50),
   picture              longblob,
   primary key (commodityid)
);

/*==============================================================*/
/* Table: commodity_discount                                    */
/*==============================================================*/
create table commodity_discount
(
   discountid           int not null,
   commodityid          int not null,
   start_date           datetime,
   end_date             datetime,
   primary key (discountid, commodityid)
);

/*==============================================================*/
/* Table: coupon                                                */
/*==============================================================*/
create table coupon
(
   couponid             int not null,
   detail               varchar(50),
   start_price          double,
   sub_price            double,
   start_date           datetime,
   end_date             datetime,
   primary key (couponid)
);

/*==============================================================*/
/* Table: customer_comment                                      */
/*==============================================================*/
create table customer_comment
(
   commodityid          int not null,
   userid               int not null,
   comments             varchar(100),
   commentdate          datetime not null,
   levels               varchar(10),
   picture              longblob,
   primary key (commodityid, userid, commentdate)
);

/*==============================================================*/
/* Table: discou                                                */
/*==============================================================*/
create table discou
(
   discountid           int not null,
   detail               varchar(50),
   min_number           int,
   discount             double,
   start_date           datetime,
   end_date             datetime,
   primary key (discountid)
);

/*==============================================================*/
/* Table: foodtype                                              */
/*==============================================================*/
create table foodtype
(
   typeid               int not null,
   typename             varchar(50),
   description          varchar(50),
   primary key (typeid)
);

/*==============================================================*/
/* Table: menu                                                  */
/*==============================================================*/
create table menu
(
   menuid               int not null,
   menuname             varchar(50),
   ingredient           varchar(50),
   step                 varchar(10),
   picture              longblob,
   primary key (menuid)
);

/*==============================================================*/
/* Table: order_detail                                          */
/*==============================================================*/
create table order_detail
(
   orderid              int not null,
   commodityid          int,
   number               int,
   price                double,
   discount             double,
   discountid           int,
   primary key (orderid)
);

/*==============================================================*/
/* Table: orders                                                */
/*==============================================================*/
create table orders
(
   orderid              int not null,
   userid               int,
   ori_amount           double,
   set_amount           double,
   couponid             int,
   order_time           datetime,
   addressid            int,
   orderstatus          varchar(50),
   primary key (orderid)
);

/*==============================================================*/
/* Table: purchase                                              */
/*==============================================================*/
create table purchase
(
   purchaseid           int not null,
   foodid               int,
   number               int,
   pstatus              varchar(50),
   primary key (purchaseid)
);

/*==============================================================*/
/* Table: sale                                                  */
/*==============================================================*/
create table sale
(
   saleid               int not null,
   commodityid          int,
   saleprice            double,
   maxnumber            int,
   start_date           datetime,
   end_date             datetime,
   primary key (saleid)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   userid               int not null,
   username             varchar(50),
   sex                  varchar(50),
   pwd                  varchar(50),
   phonenum             varchar(50),
   mail                 varchar(50),
   city                 varchar(50),
   register_time        datetime,
   isvip                varchar(50),
   vipendtime           datetime,
   primary key (userid)
);

alter table commidity_menu add constraint FK_Reference_1 foreign key (menuid)
      references menu (menuid) on delete restrict on update restrict;

alter table commidity_menu add constraint FK_Reference_2 foreign key (commodityid)
      references commodity (commodityid) on delete restrict on update restrict;

alter table commodity_discount add constraint FK_Reference_3 foreign key (discountid)
      references discou (discountid) on delete restrict on update restrict;

alter table commodity_discount add constraint FK_Reference_4 foreign key (commodityid)
      references commodity (commodityid) on delete restrict on update restrict;

alter table customer_comment add constraint FK_Reference_6 foreign key (commodityid)
      references commodity (commodityid) on delete restrict on update restrict;

alter table customer_comment add constraint FK_Reference_7 foreign key (userid)
      references users (userid) on delete restrict on update restrict;

alter table order_detail add constraint FK_Reference_11 foreign key (commodityid)
      references commodity (commodityid) on delete restrict on update restrict;

alter table order_detail add constraint FK_Reference_8 foreign key (discountid)
      references discou (discountid) on delete restrict on update restrict;

alter table orders add constraint FK_Reference_10 foreign key (addressid)
      references address (addressid) on delete restrict on update restrict;

alter table orders add constraint FK_Reference_12 foreign key (userid)
      references users (userid) on delete restrict on update restrict;

alter table orders add constraint FK_Reference_9 foreign key (couponid)
      references coupon (couponid) on delete restrict on update restrict;

alter table sale add constraint FK_Reference_5 foreign key (commodityid)
      references commodity (commodityid) on delete restrict on update restrict;

