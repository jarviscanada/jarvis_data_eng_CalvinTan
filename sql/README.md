# Introduction
This project was primarily a learning activity designed to build familiarity with SQL and RDBMS. 
A PostgreSQL database was created with Docker and sample data was then 
loaded in from a provided DDL file. The database contains three tables modeling a country club's
facilities, bookings, and members.
Various queries (involving SELECT, JOIN, GROUP BY, window functions, etc.) were written and
executed to extract meaningful information out of the database. 
# SQL Queries

##### Table Setup (DDL)
```sql
CREATE TABLE IF NOT EXISTS cd.members (
    memid INTEGER NOT NULL PRIMARY KEY ,
    surname VARCHAR(200) NOT NULL,
    firstname VARCHAR(200) NOT NULL,
    address VARCHAR(300) NOT NULL,
    zipcode INTEGER NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    recommendedby INTEGER NOT NULL REFERENCES cd.members(memid) ON DELETE SET NULL,
    joindate TIMESTAMP NOT NULL,
);

CREATE TABLE IF NOT EXISTS cd.bookings (
    bookid INTEGER NOT NULL PRIMARY KEY, 
    facid INTEGER NOT NULL REFERENCES cd.facilities(facid),
    memid INTEGER NOT NULL REFERENCES cd.members(memid),
    starttime TIMESTAMP NOT NULL,
    slots INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS cd.facilities (
    facid INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    membercost NUMERIC NOT NULL,
    guestcost NUMERIC NOT NULL,
    initaloutlay NUMERIC NOT NULL,
    monthlymaintenance NUMERIC NOT NULL
);
```
##### Question 1: insert new facility into database
```sql
INSERT INTO cd.facilities 
VALUES (9, 'Spa', 20, 30, 100000, 800);
```

##### Question 2: insert new facility with auto-generated id into database
```sql
INSERT INTO cd.facilities 
VALUES ((
SELECT MAX(facid)
FROM cd.facilities 
)+1, 
'Spa', 20, 30, 100000, 800);
```

##### Question 3: update incorrect initial outlay value
```sql
UPDATE cd.facilities 
SET initialoutlay = 10000
WHERE name = 'Tennis Court 2';
```

##### Question 4: update value using computed value
```sql
UPDATE cd.facilities f
SET 
	membercost = other.membercost * 1.1,
	guestcost = other.guestcost * 1.1
	FROM (SELECT * FROM cd.facilities WHERE name = 'Tennis Court 1') AS other
WHERE f.name = 'Tennis Court 2';
```

##### Question 5: delete all bookings
```sql
DELETE FROM cd.bookings;
```

##### Question 6: delete specific member id
```sql
DELETE FROM cd.members
WHERE memid=37;
```

##### Question 7: select facilities with a membership fee less than 1/50th the monthly maintenance cost
```sql
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities 
WHERE (membercost > 0) AND (membercost < (monthlymaintenance / 50.0));
```

##### Question 8: select all facilities with "Tennis" in their name
```sql
SELECT *
FROM cd.facilities 
WHERE name LIKE '%Tennis%';
```

##### Question 9: select all facilities with specific id
```sql
SELECT *
FROM cd.facilities 
WHERE facid IN (1,5);
```

##### Question 10: select all members that join after September 2012
```sql
SELECT memid, surname, firstname, joindate
FROM cd.members
WHERE joindate > '2012-09-01';
```

##### Question 11: select all member surnames and facilities names
```sql
SELECT surname
FROM cd.members
UNION
SELECT name
FROM cd.facilities;
```

##### Question 12: list start times for bookings made by "David Farrell"
```sql
SELECT starttime 
FROM cd.members m 
	INNER JOIN cd.bookings b USING(memid)
WHERE m.firstname = 'David' AND m.surname = 'Farrell';
```

##### Question 13: list start times for tennis court bookings on 2012-09-21
```sql
SELECT starttime AS start, name
FROM cd.bookings b 
	INNER JOIN cd.facilities f USING(facid)
WHERE f.name LIKE 'Tennis Court%' AND 
	b.starttime BETWEEN '2012-09-21'::date AND '2012-09-21'::date + '1 day'::interval;
```

##### Question 14: list all members and who recommended them (if any)
```sql
SELECT m.firstname AS memfname, m.surname AS memsname, r.firstname AS recfname, r.surname AS recsname
FROM cd.members m 
	LEFT JOIN cd.members r 
		ON r.memid=m.recommendedby
ORDER BY m.surname ASC, m.firstname ASC;
```

##### Question 15: list all members who have recommended another member
```sql
SELECT DISTINCT m.firstname, m.surname
FROM cd.members m 
	INNER JOIN cd.members r
		ON m.memid=r.recommendedby
ORDER BY m.surname ASC, m.firstname ASC;
```

##### Question 16: list all members who have recommended another member (without using JOIN)
```sql
SELECT m.firstname || ' ' || m.surname AS member, (
	SELECT r.firstname || ' ' || r.surname AS recommender
	FROM cd.members r
	WHERE r.memid = m.recommendedby)
FROM cd.members m 
ORDER BY member;
```

##### Question 17: count the number of recommendations each member has made, ordered by member id
```sql
SELECT m.recommendedby, count(*) AS count
FROM cd.members m
WHERE m.recommendedby IS NOT NULL 
GROUP BY m.recommendedby
ORDER BY m.recommendedby ASC;
```

##### Question 18: count the total number of slots booked per facility 
```sql
SELECT f.facid, SUM(b.slots) AS "Total Slots"
FROM cd.facilities f 
	INNER JOIN cd.bookings b 
		USING(facid)
GROUP BY f.facid 
ORDER BY f.facid ASC;
```

##### Question 19: count the total number of slots booked per facility on September 2012
```sql
SELECT f.facid, SUM(b.slots) AS "Total Slots"
FROM cd.facilities f 
	INNER JOIN cd.bookings b 
		USING(facid)
WHERE b.starttime BETWEEN '2012-09-01' AND '2012-10-01'
GROUP BY f.facid 
ORDER BY SUM(b.slots) ASC;
```

##### Question 20: count the total number of slots booked per facility for each month in 2012
```sql
SELECT f.facid, EXTRACT(MONTH FROM b.starttime) AS month, SUM(b.slots) AS "Total Slots"
FROM cd.facilities f 
	INNER JOIN cd.bookings b 
		USING(facid)
WHERE EXTRACT(YEAR FROM b.starttime) = 2012
GROUP BY f.facid, month
ORDER BY f.facid, month ASC;
```

##### Question 21: list the total number of members and guests that have made at least 1 booking
```sql
SELECT COUNT(*)
FROM (
	SELECT DISTINCT b.memid
	FROM cd.bookings b 
	) AS TEMP;
```

##### Question 22: list each member and their first booking after September 1st 2012
```sql
SELECT m.surname, m.firstname, m.memid, MIN(b.starttime)
FROM cd.members m 
	INNER JOIN cd.bookings b
		USING(memid)
WHERE b.starttime >= '2012-09-01'
GROUP BY m.memid 
ORDER BY m.memid ASC;
```

##### Question 23: list each member, with each row containing the total member count
```sql
SELECT COUNT(*) OVER(), m.firstname, m.surname 
FROM cd.members m;
```

##### Question 24: list each member, number by their date of joining 
```sql
SELECT ROW_NUMBER() OVER(ORDER BY m.joindate ASC), m.firstname, m.surname 
FROM cd.members m ;
```

##### Question 25: select the facility with the most number of slots booked
```sql
SELECT facid, SUM(slots) AS total
FROM cd.bookings b 
GROUP BY b.facid
ORDER BY total DESC
LIMIT 1;
```

##### Question 26: list names of all members formatted as "Surname, Firstname"
```sql
SELECT (m.surname || ', ' || m.firstname) AS name
FROM cd.members m;
```

##### Question 27: list all member ids and phone numbers containing parentheses
```sql
SELECT m.memid, m.telephone
FROM cd.members m 
WHERE m.telephone LIKE '(%)%';
```

##### Question 28: list the count of how many member's surname starts with each letter of the alphabet
```sql
SELECT LEFT(m.surname, 1), COUNT(*) AS count
FROM cd.members m 
GROUP BY LEFT(m.surname, 1)
ORDER BY LEFT(m.surname, 1);
```