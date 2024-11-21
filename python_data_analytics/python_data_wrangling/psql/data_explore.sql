SELECT * 
FROM public.retail r
LIMIT 10;

SELECT COUNT(*) AS "number of records"
FROM public.retail r;

SELECT COUNT(DISTINCT customer_id)
FROM public.retail r

SELECT MIN(invoice_date) AS "min", MAX(invoice_date) AS "max"
FROM public.retail r ;

