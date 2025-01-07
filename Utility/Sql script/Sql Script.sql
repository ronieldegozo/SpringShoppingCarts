select * from shoppingcarts.image;
select * from shoppingcarts.category;
select * from shoppingcarts.product;
select * from shoppingcarts.cart;
select * from shoppingcarts.cart_items;
select * from shoppingcarts.user;
select * from shoppingcarts.orders;
select * from shoppingcarts.order_item;

ALTER TABLE cart
DROP COLUMN user_ids;

ALTER TABLE cart
DROP FOREIGN KEY FK9b9hna4p0adttm2k36uo6va45;

ALTER TABLE cart
DROP COLUMN user_ids;


-- Delete image base on ids between
DELETE FROM shoppingcarts.image  WHERE id BETWEEN 16 AND 100;

-- Delete product base on ids
DELETE FROM shoppingcarts.product  WHERE id = '11';

-- Delete category base on ids between
DELETE FROM shoppingcarts.category  WHERE id BETWEEN 3 AND 4;

-- Delete all cartitems
DELETE FROM shoppingcarts.cart_items  WHERE cart_id BETWEEN 1 AND 100;

-- DELETE ALL CARTS
DELETE FROM shoppingcarts.cart  WHERE id BETWEEN 1 AND 100;

-- Delete catregory
DELETE FROM shoppingcarts.category  WHERE id BETWEEN 7 AND 13;