--Phase 1 Submission
--Phase 2 Submission

--drop the tables just in case they already exist
drop table ourSysDATE cascade constraints;
drop table Customer cascade	constraints;
drop table Administrator cascade constraints;
drop table Product cascade constraints;
drop table Bidlog cascade constraints;
drop table Category cascade constraints;
drop table BelongsTo cascade constraints;

--ourSysDATE to allow us to easily manipulate the date for testing
create table ourSysDATE (
	c_date date not null,
	primary key (c_date)
);

--Customer table
--The login username must be unique for all customers
--One account per email
create table Customer (
	login 		varchar2(10) not null,
	password 	varchar2(10) not null,
	name 		varchar2(20),
	address 	varchar2(30),
	email 		varchar2(20),
	primary key (login) deferrable,
	unique(email) deferrable
);

--Administrator table
--The login username must be unique across admins
--One account per email
create table Administrator (
	login 		varchar2(10),
	password 	varchar(10),
	name 		varchar2(20),
	address 	varchar2(20),
	email 		varchar2(20),
	primary key (login) deferrable,
	unique(email) deferrable
);

--Product table
create table Product (
	auction_id 		int not null,
	name 			varchar2(20),
	description 	varchar2(20),
	seller 			varchar2(10),
	start_date		date,
	min_price		int,
	number_of_days 	int,
	status 			varchar2(15) not null,
	buyer			varchar2(10),
	sell_date 		date not null,
	amount 			int,
	primary key (auction_id) deferrable,
	foreign key (seller) references Customer(login) deferrable,
	foreign key (buyer) references Customer(login) deferrable,
	constraint PRODUCT_status 
		check (status in ('under auction', 'sold', 'withdrawn', 'closed'))
);

--Bidlog table
create table Bidlog (
	bidsn		int,
	auction_id 	int,
	bidder 		varchar2(10),
	bid_time 	date,
	amount 		int,
	primary key (bidsn) deferrable,
	foreign key (auction_id) references Product(auction_id) deferrable,
	foreign key (bidder) references Customer(login) deferrable
);

--Category table
create table Category (
	name 			varchar2(20),
	parent_category varchar2(20),
	primary key (name) deferrable,
	foreign key (parent_category) references Category(name) deferrable
);

--BelongsTo
create table BelongsTo (
	auction_id 	int,
	category 	varchar2(20),
	primary key (auction_id, category) deferrable,
	foreign key (auction_id) references Product(auction_id) deferrable,
	foreign	key (category) references Category(name) deferrable
);
