--Phase 1 Submission
--Phase 2 Submission

--Set the system time
insert into ourSysDATE values(to_date('19-Nov-2018 12:00:00 AM', 'dd-mon-yyyy hh:mi:ss AM'));

--Create 3 customers
insert into Customer values ('randlogin', 'randpass', 'Joe', 'Schmoe', 'randemail@gmail.com');
insert into Customer values ('doglover22', 'h8cats', 'Angela', 'Demarillos', 'iown6dogs@aol.com');
insert into Customer values ('noidea', '2ndprez', 'John', 'Adams', 'notjohn@yahoo.com');

--Create 1 Admin
insert into Administrator values ('admin', 'root', 'Admin', 'User', null);

--Create 5 products
--This is just a holder until the procedure is ready
--When the procedure proc_putProduct is done, it will update auction id and start date.
insert into Product values (1, 'Cat Bed', 'No longer own cats.', 'doglover22',
							to_date('17-Nov-2018 12:00:00 AM', 'dd-mon-yyyy hh:mi:ss AM'), 1, 2, 
							'sold', 'randlogin', to_date('19-Nov-2018 12:00:00 AM', 'dd-mon-yyyy hh:mi:ss AM'), 5);
insert into Product values (2, 'Gingerbread kit', 'Happy holidays!', 'noidea',
							to_date('18-Nov-2018 03:11:53 PM', 'dd-mon-yyyy hh:mi:ss AM'), 5, 1,
							'under auction', null, to_date('25-Nov-2018 03:11:53 PM', 'dd-mon-yyyy hh:mi:ss AM'), null);
insert into Product values (3, 'Water Purifier', 'Hooks to sink!', 'noidea',
							to_date('18-Nov-2018 03:13:22 PM', 'dd-mon-yyyy hh:mi:ss AM'), 3, 1,
							'under auction', null, to_date('23-Nov-2018 03:13:22 PM', 'dd-mon-yyyy hh:mi:ss AM'), null);
insert into Product values (4, 'Tools', 'Random tools.', 'randlogin',
							to_date('18-Nov-2018 05:22:10 PM', 'dd-mon-yyyy hh:mi:ss AM'), 20, 1,
							'under auction', null, to_date('28-Nov-2018 05:22:10 PM', 'dd-mon-yyyy hh:mi:ss AM'), 25);
insert into Product values (5, 'Ceramic mug', 'Pic of dog.', 'randlogin',
							to_date('18-Nov-2018 08:51:01 PM', 'dd-mon-yyyy hh:mi:ss AM'), 2, 1,
							'under auction', null, to_date('30-Nov-2018 08:51:01 PM', 'dd-mon-yyyy hh:mi:ss AM'), 10);

--Create bids
insert into Bidlog values (1, 1, 'randlogin', 
							to_date('17-Nov-2018 03:00:27 AM', 'dd-mon-yyyy hh:mi:ss AM'), 2);
insert into Bidlog values (2, 1, 'noidea',
							to_date('17-Nov-2018 05:59:02 AM', 'dd-mon-yyyy hh:mi:ss AM'), 3);
insert into Bidlog values (3, 1, 'randlogin',
							to_date('17-Nov-2018 12:02:53 PM', 'dd-mon-yyyy hh:mi:ss AM'), 5);
insert into Bidlog values (4, 4, 'noidea',
							to_date('18-Nov-2018 07:00:01 PM', 'dd-mon-yyyy hh:mi:ss AM'), 21);
insert into Bidlog values (5, 4, 'doglover22',
							to_date('18-Nov-2018 07:02:33 PM', 'dd-mon-yyyy hh:mi:ss AM'), 22);
insert into Bidlog values (6, 4, 'noidea',
							to_date('18-Nov-2018 07:10:50 PM', 'dd-mon-yyyy hh:mi:ss AM'), 23);
insert into Bidlog values (7, 4, 'doglover22',
							to_date('18-Nov-2018 07:11:21 PM', 'dd-mon-yyyy hh:mi:ss AM'), 24);
insert into Bidlog values (8, 4, 'noidea',
							to_date('18-Nov-2018 07:12:10 PM', 'dd-mon-yyyy hh:mi:ss AM'), 25);
insert into Bidlog values (9, 5, 'noidea',
							to_date('18-Nov-2018 09:02:59 PM', 'dd-mon-yyyy hh:mi:ss AM'), 5);
insert into Bidlog values (10, 5, 'doglover22',
							to_date('18-Nov-2018 10:58:22 PM', 'dd-mon-yyyy hh:mi:ss AM'), 10);

--Create categories
insert into Category values ('Hardware', null);
insert into Category values ('Kitchen', null);
insert into Category values ('Kitchen Utilities', 'Kitchen');
insert into Category values ('Food', null);
insert into Category values ('Baking', 'Food');
insert into Category values ('Mugs', 'Kitchen');
insert into Category values ('Pet', null);
insert into Category values ('Pet Furniture', 'Pet');
insert into Category values ('Tools', 'Hardware');

--Link Categories and Auctions
insert into BelongsTo values (1, 'Pet Furniture');
insert into BelongsTo values (2, 'Baking');
insert into BelongsTo values (3, 'Kitchen Utilities');
insert into BelongsTo values (4, 'Tools');
insert into BelongsTo values (5, 'Mugs');


--Test the trigger closeAuction
update ourSysDate 
set c_date = to_date('20-Nov-2018 12:00:00 AM', 'dd-mon-yyyy hh:mi:ss AM');
select * from product;

--Test the trigger tri_bidTimeUpdate
select * from ourSysDate;
insert into Bidlog values(11, 5, 'doglover22', to_date('18-Nov-2018 11:58:25 PM', 'dd-mon-yyyy hh:mi:ss AM'), 12);
select * from ourSysDate;


--Test the trigger tri_updateHighBid
select * from product;
insert into Bidlog values(12, 5, 'noidea', to_date('17-Nov-2018 11:50:25 PM', 'dd-mon-yyyy hh:mi:ss AM'), 13);
select * from product;

--Commit prior to using transactions
commit;

--Test functions
set transaction read write;
set constraints all deferred;
declare
	a number;
begin
	a := Product_Count('Pet', 1);
	dbms_output.put_line(a);
end;
/
commit;

set transaction read write;
set constraints all deferred;
declare
	a number;
begin
	a := Bid_Count('noidea', 1);
	dbms_output.put_line(a);
end;
/
commit;

set transaction read write;
set constraints all deferred;
declare
	a number;
begin
	a := Buying_Amount('randlogin', 1);
	dbms_output.put_line(a);
end;
/
commit;










