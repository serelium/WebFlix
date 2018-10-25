DROP TABLE MovieRole PURGE;
DROP TABLE DomainMovieRole PURGE;
/*DROP TABLE Artist PURGE;
DROP TABLE Rent PURGE;*/
DROP TABLE MovieCopy PURGE;
DROP TABLE MovieGenre PURGE;
DROP TABLE DomainMovieGenre PURGE;
DROP TABLE Movie_Scriptwriters PURGE;
DROP TABLE Movie_Countries PURGE;
DROP TABLE Scriptwriter PURGE;
DROP TABLE Trailer PURGE;
DROP TABLE Movie PURGE;
DROP TABLE Country PURGE;
DROP TABLE DomainLanguage PURGE;
/*DROP TABLE Customer PURGE;
DROP TABLE SubscriptionPlan PURGE;
DROP TABLE CreditCard PURGE;
DROP TABLE CreditCardType PURGE;
DROP TABLE Employee PURGE;
DROP TABLE Person PURGE;
DROP TABLE Address PURGE;*/

SET SERVEROUTPUT ON;

/*CREATE TABLE Address
(
    AddressID       INT GENERATED ALWAYS AS IDENTITY,
	CivicNumber	    INT NOT NULL,
	Street		    VARCHAR(40) NOT NULL,
	City			VARCHAR(40) NOT NULL,
	Province	    VARCHAR(2) NOT NULL,
    PostalCode      VARCHAR(6) NOT NULL,
    CONSTRAINT address_pk PRIMARY KEY(AddressID)
);

CREATE TABLE Person
(
    UserID          INT,
	FirstName	    VARCHAR(25) NOT NULL,
	LastName		VARCHAR(25) NOT NULL,
    BirthDate       DATE NOT NULL,
	Email			VARCHAR(40) NOT NULL UNIQUE,
	PhoneNumber	    VARCHAR(10) NOT NULL,
    UserPassword    VARCHAR(25) NOT NULL,
    AddressID       INT NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY(UserID),
    FOREIGN KEY (AddressID) REFERENCES Address(AddressID),
    CONSTRAINT CHK_PhoneNumber CHECK (REGEXP_LIKE(PhoneNumber, '^\d{10}$')),
    CONSTRAINT CHK_UserPassword CHECK (REGEXP_LIKE(UserPassword, '^[a-zA-Z0-9_]{5,25}$'))
);

CREATE TABLE CreditCardType
(
    CreditCardTypeID    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	TypeName	        VARCHAR(25) NOT NULL
);

CREATE TABLE CreditCard
(
    CreditCardID        VARCHAR2(16) PRIMARY KEY,
	CreditCardTypeID	INT NOT NULL,
	ExpiryMonth		    INT NOT NULL,
	ExpiryYear	        INT NOT NULL,
    FOREIGN KEY (CreditCardTypeID) REFERENCES CreditCardType(CreditCardTypeID),
    CONSTRAINT CHK_ExpiryMonth CHECK (ExpiryMonth > 0 AND ExpiryMonth <= 12)
);

CREATE TABLE SubscriptionPlan
(
    SubscriptionPlanID  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    PlanName            VARCHAR2(25) NOT NULL,
	MonthlyCost	        DECIMAL NOT NULL,
	MaxRent		        INT NOT NULL,
	MaxRentDuration 	INT NOT NULL
);

CREATE TABLE Customer
(
    UserID              INT PRIMARY KEY,
	SubscriptionPlanID  INT NOT NULL,
    CreditCardID        VARCHAR2(16) NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Person(UserID),
    FOREIGN KEY (SubscriptionPlanID) REFERENCES SubscriptionPlan(SubscriptionPlanID),
    FOREIGN KEY (CreditCardID) REFERENCES CreditCard(CreditCardID)
);

CREATE TABLE Employee
(
    UserID              INT PRIMARY KEY,
	RegeristrationID    INT NOT NULL UNIQUE,
    FOREIGN KEY (UserID) REFERENCES Person(UserID),
    CONSTRAINT CHK_RegeristrationID CHECK (REGEXP_LIKE(RegeristrationID, '^\d{7}$'))
);*/

CREATE TABLE DomainLanguage
(
    LanguageName VARCHAR(25) PRIMARY KEY
);

CREATE TABLE Country
(
    CountryName VARCHAR(25) PRIMARY KEY
);

CREATE TABLE Scriptwriter
(
    FullName       VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE Movie
(
    MovieID             INT PRIMARY KEY,
    Title               VARCHAR(200) NOT NULL,
    YearOfRelease       INT NOT NULL,
    MovieLength         INT NOT NULL,
    OriginLanguage      VARCHAR(25),
    PosterLink          VARCHAR2(1000),
    Synopsis            VARCHAR2(4000 CHAR),
    FOREIGN KEY (OriginLanguage) REFERENCES DomainLanguage(LanguageName)
);

CREATE TABLE Trailer
(
    TrailerID   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    TrailerLink VARCHAR2(500),
    MovieID     INT REFERENCES Movie(MovieID)
);

CREATE TABLE Movie_Scriptwriters
(
    ScriptwriterName   VARCHAR2(50) REFERENCES Scriptwriter(FullName),
    MovieID             INT REFERENCES Movie(MovieID)
);

CREATE TABLE Movie_Countries
(
    Country    VARCHAR2(25) REFERENCES Country(CountryName),
    MovieID    INT REFERENCES Movie(MovieID)
);

CREATE TABLE DomainMovieGenre
(
    MovieGenreName VARCHAR(25) PRIMARY KEY
);

CREATE TABLE MovieGenre
(
    MovieGenreName  VARCHAR2(25) REFERENCES DomainMovieGenre(MovieGenreName),
    MovieID         INT REFERENCES Movie(MovieID)
);

CREATE TABLE MovieCopy
(
    MovieCopyID             INT PRIMARY KEY,
	MovieID                 INT NOT NULL,
    FOREIGN KEY (MovieID)   REFERENCES Movie(MovieID)
);

/*CREATE TABLE Rent
(
    RentID          INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	MovieCopyID     INT NOT NULL,
    RentTime        DATE NOT NULL,
    UserID          INT NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Person(UserID),
    FOREIGN KEY (MovieCopyID) REFERENCES MovieCopy(MovieCopyID)
);

CREATE TABLE Artist
(
    ArtistID    INT PRIMARY KEY,
	ArtistName  VARCHAR2(100) NOT NULL,
    BirthDate   DATE,
    BirthPlace  VARCHAR2(100),
    PictureLink VARCHAR2(400),
    Biography   CLOB
);*/

CREATE TABLE DomainMovieRole
(
    MovieRole VARCHAR(25) PRIMARY KEY NOT NULL
);

CREATE TABLE MovieRole
(
    MovieRoleID     INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	RoleType        VARCHAR2(25) NOT NULL,
    CharacterName   VARCHAR2(200),
    ArtistID        INT NOT NULL,
    MovieID         INT NOT NULL,
    FOREIGN KEY (RoleType) REFERENCES DomainMovieRole(MovieRole),
    FOREIGN KEY (ArtistID) REFERENCES Artist(ArtistID),
    FOREIGN KEY (MovieID) REFERENCES Movie(MovieID)
);

CREATE OR REPLACE TRIGGER ValidateInsertPerson
BEFORE INSERT OR UPDATE OF BirthDate ON Person FOR EACH ROW
DECLARE
    MonthsBetween NUMBER;
BEGIN
    SELECT MONTHS_BETWEEN(sysdate, :NEW.BirthDate) INTO MonthsBetween FROM dual;
    IF MonthsBetween < 18 * 12 THEN
        RAISE_APPLICATION_ERROR(-20000, 'Participant must be at least 18 years old.');
    END IF;
END;
/

CREATE OR REPLACE TRIGGER CreditCardValidation

    BEFORE INSERT
    ON CreditCard
    FOR EACH ROW 
    DECLARE
        CurrentYear INT;
        CurrentMonth INT;
    BEGIN
        SELECT EXTRACT(YEAR FROM sysdate) INTO CurrentYear FROM dual;
        SELECT EXTRACT(MONTH FROM sysdate) INTO CurrentMonth FROM dual;
        
        IF :NEW.ExpiryYear < CurrentYear OR (:NEW.ExpiryYear = CurrentYear AND :NEW.ExpiryMonth < CurrentMonth) THEN
            RAISE_APPLICATION_ERROR(-20000, 'Credit card expired.');
        END IF;
    END;
/

CREATE OR REPLACE TRIGGER CheckIfCanRentMovie
BEFORE INSERT OR UPDATE OF MovieCopyID ON Rent FOR EACH ROW
DECLARE
    SubscriptionID INT;
    MaxRentForCustomer INT;
    CurrentRentCount INT;
    RentOfMovieCopy INT;
BEGIN
    SELECT SubscriptionPlanID INTO SubscriptionID FROM Customer WHERE Customer.UserID = :NEW.UserID;
    SELECT MaxRent INTO MaxRentForCustomer FROM SubscriptionPlan WHERE SubscriptionID = SubscriptionPLan.SubscriptionPlanID;
    SELECT COUNT(RentID) INTO CurrentRentCount FROM Rent WHERE :NEW.UserID = Rent.UserID;
    SELECT COUNT(RentID) INTO RentOfMovieCopy FROM Rent WHERE :NEW.MovieCopyID = Rent.MovieCopyID;
    
    IF CurrentRentCount >= MaxRentForCustomer THEN
        RAISE_APPLICATION_ERROR(-20000, 'You do not have anymore rents available, please return a movie before renting another one.');
    END IF;
    
    IF RentOfMovieCopy >= 1 THEN
        RAISE_APPLICATION_ERROR(-20000, 'Sorry, there are no more copies of this movie available for rent, try again another time.');
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE p_create_customer
(
    p_CivicNumber IN Address.CivicNumber%TYPE,
    p_Street IN Address.Street%TYPE,
    p_City IN Address.City%TYPE,
    p_Province IN Address.Province%TYPE,
    p_PostalCode IN Address.PostalCode%TYPE,

    p_FirstName IN Person.FirstName%TYPE,
    p_LastName IN Person.LastName%TYPE,
    p_BirthDate IN Person.BirthDate%TYPE,
    p_Email IN Person.Email%TYPE,
    p_PhoneNumber IN Person.PhoneNumber%TYPE,
    p_UserPassword IN Person.UserPassword%TYPE,
    
    p_CreditCardID IN CreditCard.CreditCardID%TYPE,
    p_CreditCardTypeID IN CreditCard.CreditCardTypeID%TYPE,
    p_ExpiryMonth IN CreditCard.ExpiryMonth%TYPE,
    p_ExpiryYear IN CreditCard.ExpiryYear%TYPE,
    p_CVV IN CreditCard.CVV%TYPE,
    
    p_SubscriptionPlanID IN SubscriptionPlan.SubscriptionPlanID%TYPE
)

IS
BEGIN

    INSERT INTO Address (CivicNumber, Street, City, Province, PostalCode) 
        VALUES (p_CivicNumber, p_Street, p_City, p_Province, p_PostalCode);
    INSERT INTO Person (FirstName, LastName, BirthDate, Email, PhoneNumber, UserPassword, AddressID)
        VALUES (p_FirstName, p_LastName, p_BirthDate, p_Email, p_PhoneNumber, p_UserPassword, (SELECT MAX(AddressID) FROM Address));
    INSERT INTO CreditCard (CreditCardID, CreditCardTypeID, ExpiryMonth, ExpiryYear, CVV)
        VALUES (p_CreditCardID, p_CreditCardTypeID, p_ExpiryMonth, p_ExpiryYear, p_CVV);
    INSERT INTO Customer (UserID, SubscriptionPlanID, CreditCardID)
        VALUES ((SELECT MAX(UserID) FROM Person), p_SubscriptionPlanID, p_CreditCardID);

    COMMIT;

END;
/

CREATE OR REPLACE PROCEDURE p_customer_connection
(
    p_Email IN Person.Email%TYPE,
    p_UserPassword IN Person.UserPassword%TYPE
)
IS
    accountsFound INTEGER;
BEGIN

    SELECT COUNT(Email) INTO accountsFound FROM Person WHERE Email LIKE p_Email AND UserPassword LIKE p_UserPassword;
    IF accountsFound = 0 THEN 
        RAISE_APPLICATION_ERROR (-20000, 'Wrong email or password');
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE p_rent_movie
(
    p_MovieID IN movie.movieid%TYPE,
    p_UserID IN Customer.UserID%TYPE
)

IS
BEGIN

    INSERT INTO Rent (MovieCopyID, RentTime, UserID) 
        VALUES ((SELECT MovieCopyID FROM MovieCopy WHERE p_MovieId = MovieCopy.MovieID), (SELECT sysdate FROM dual), p_UserID);
    
    COMMIT;

END;
/
INSERT INTO DomainMovieRole (MovieRole) VALUES ('Actor');
INSERT INTO DomainMovieRole (MovieRole) VALUES ('Director');

INSERT INTO CreditCardType (TypeName) VALUES ('Visa');
INSERT INTO CreditCardType (TypeName) VALUES ('MasterCard');

COMMIT;