
-- US 01
select count(id) from users; --
-- 1856

select count(distinct id) from users;
-- 1856


-- RESULT --> MANUALLY IT IS PASSED


-- US 02
select * from users;



-- US 03
select count(*) from book_borrow
where is_returned=0;

select count(*) from books where name='Clean Code';

select * from books where name='Clean Code';

select b.name as bookName, author, bc.name as bookCategoryName from books b inner join book_categories
    bc on b.book_category_id = bc.id where b.name = 'Lord of the Files';
