--Phase 1 Submission
--Phase 2 Submission

--This trigger will change the auctions accordingly when we change the date
create or replace trigger closeAuctions
after update of c_date
on ourSysDATE
for each row
begin
	update Product set status = 'closed' where sell_date < :new.c_date and status = 'underauction';
end ;
/

--This trigger will change the system time when a bid is made
CREATE OR REPLACE TRIGGER tri_bidTimeUpdate
after insert
on Bidlog
for each row
begin
	update ourSysDATE
		set c_date = c_date + 5/86400;
end;
/
--This trigger will change the current highest bid on an item according to the bitlog
CREATE OR REPLACE TRIGGER tri_updateHighBid 
after insert
on Bidlog
for each row
begin 
	update Product
		set product.amount = :new.amount
		where product.auction_id = :new.auction_id;
end;
/
--This function finds the count of a certain product over a certain period of time
create or replace function Product_Count (c in varchar2, x in number) return number
is
theCount number;
begin
	select count(*) into theCount from (Product P full outer join BelongsTo B on P.auction_id = B.auction_id) where category = c and status = 'sold'
	and sell_date > ADD_MONTHS((select c_date from ourSysDATE), (x * -1));
	return (theCount);
end;
/
--This function finds the count of bids that a certain user has made over a certain period of time 
create or replace function Bid_Count (u in varchar2, x in number) return number
is
theCount number;
begin
	select count(*) into theCount from Bidlog where bidder = u and bid_time > ADD_MONTHS((select c_date from ourSysDATE), (x * -1));
	return (theCount);
end;
/
--This function sums up the total amount of money spent by a certain user over a certain period of time
create or replace function Buying_Amount (u in varchar2, x in number) return number
is
theSum number;
begin
	select nvl(sum(amount), 0) into theSum from Product where buyer = u and sell_date > ADD_MONTHS((select c_date from ourSysDATE), (x * -1));
	return (theSum);
end;
/

--proc_putProduct
create or replace type category_t is table of varchar2(20);
/

create or replace procedure proc_putProduct(name varchar2, description varchar2 default '', 
			seller varchar2, category category_t, min_price int, number_of_days int, end_date date) 
	as
	next_auction_id int;
	curr_date 		date;
	begin
		select max(auction_id) + 1 into next_auction_id from Product;
		select c_date into curr_date from ourSysDATE;

		insert into Product values (next_auction_id, name, description, seller, curr_date,
									min_price, number_of_days, 'under auction', null, end_date, null);
		for i in 1 .. category.count
		loop
			insert into BelongsTo values (next_auction_id, category(i));
		end loop;
	end;
/
