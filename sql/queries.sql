-- Modifying Data
INSERT INTO cd.facilities 
VALUES (9, 'Spa', 20, 30, 100000, 800);

INSERT INTO cd.facilities 
VALUES ((
SELECT MAX(facid)
FROM cd.facilities 
)+1, 
'Spa', 20, 30, 100000, 800);

UPDATE cd.facilities 
SET initialoutlay = 10000
WHERE name = 'Tennis Court 2';

UPDATE cd.facilities f
SET 
	membercost = other.membercost * 1.1,
	guestcost = other.guestcost * 1.1
	FROM (SELECT * FROM cd.facilities WHERE name = 'Tennis Court 1') AS other
WHERE f.name = 'Tennis Court 2';

DELETE FROM cd.bookings;

DELETE FROM cd.members
WHERE memid=37;

-- Basics
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities 
WHERE (membercost > 0) AND (membercost < (monthlymaintenance / 50.0));

SELECT *
FROM cd.facilities 
WHERE name LIKE '%Tennis%';

SELECT *
FROM cd.facilities 
WHERE facid IN (1,5);

SELECT memid, surname, firstname, joindate
FROM cd.members
WHERE joindate > '2012-09-01';

SELECT surname
FROM cd.members
UNION
SELECT name
FROM cd.facilities;

-- Join
SELECT starttime 
FROM cd.members m 
	INNER JOIN cd.bookings b USING(memid)
WHERE m.firstname = 'David' AND m.surname = 'Farrell';

SELECT starttime AS start, name
FROM cd.bookings b 
	INNER JOIN cd.facilities f USING(facid)
WHERE f.name LIKE 'Tennis Court%' AND 
	b.starttime BETWEEN '2012-09-21'::date AND '2012-09-21'::date + '1 day'::interval;

SELECT m.firstname AS memfname, m.surname AS memsname, r.firstname AS recfname, r.surname AS recsname
FROM cd.members m 
	LEFT JOIN cd.members r 
		ON r.memid=m.recommendedby
ORDER BY m.surname ASC, m.firstname ASC;

SELECT DISTINCT m.firstname, m.surname
FROM cd.members m 
	INNER JOIN cd.members r
		ON m.memid=r.recommendedby
ORDER BY m.surname ASC, m.firstname ASC;

-- Aggregation
SELECT m.recommendedby, count(*) AS count
FROM cd.members m
WHERE m.recommendedby IS NOT NULL 
GROUP BY m.recommendedby
ORDER BY m.recommendedby ASC;

SELECT f.facid, SUM(b.slots) AS "Total Slots"
FROM cd.facilities f 
	INNER JOIN cd.bookings b 
		USING(facid)
GROUP BY f.facid 
ORDER BY f.facid ASC;


SELECT f.facid, SUM(b.slots) AS "Total Slots"
FROM cd.facilities f 
	INNER JOIN cd.bookings b 
		USING(facid)
WHERE b.starttime BETWEEN '2012-09-01' AND '2012-10-01'
GROUP BY f.facid 
ORDER BY SUM(b.slots) ASC;

SELECT f.facid, EXTRACT(MONTH FROM b.starttime) AS month, SUM(b.slots) AS "Total Slots"
FROM cd.facilities f 
	INNER JOIN cd.bookings b 
		USING(facid)
WHERE EXTRACT(YEAR FROM b.starttime) = 2012
GROUP BY f.facid, month
ORDER BY f.facid, month ASC;

SELECT COUNT(*)
FROM (
	SELECT DISTINCT b.memid
	FROM cd.bookings b 
	) AS TEMP;

SELECT m.surname, m.firstname, m.memid, MIN(b.starttime)
FROM cd.members m 
	INNER JOIN cd.bookings b
		USING(memid)
WHERE b.starttime >= '2012-09-01'
GROUP BY m.memid 
ORDER BY m.memid ASC;

SELECT COUNT(*) OVER(), m.firstname, m.surname 
FROM cd.members m;

SELECT ROW_NUMBER() OVER(ORDER BY m.joindate ASC), m.firstname, m.surname 
FROM cd.members m ;

-- String
SELECT (m.surname || ', ' || m.firstname) AS name
FROM cd.members m;

SELECT m.memid, m.telephone
FROM cd.members m 
WHERE m.telephone LIKE '(%)%';

SELECT LEFT(m.surname, 1), COUNT(*) AS count
FROM cd.members m 
GROUP BY LEFT(m.surname, 1)
ORDER BY LEFT(m.surname, 1);
