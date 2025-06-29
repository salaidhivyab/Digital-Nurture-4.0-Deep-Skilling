-- Enable output
SET SERVEROUTPUT ON;

-- Drop tables if they exist (safe for rerun)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE Transactions';
    EXECUTE IMMEDIATE 'DROP TABLE Accounts';
    EXECUTE IMMEDIATE 'DROP TABLE Loans';
    EXECUTE IMMEDIATE 'DROP TABLE Employees';
    EXECUTE IMMEDIATE 'DROP TABLE Customers';
EXCEPTION
    WHEN OTHERS THEN NULL;
END;
/

-- ========== 1. TABLE CREATION ==========

CREATE TABLE Customers (
    CustomerID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    DOB DATE,
    Balance NUMBER,
    LastModified DATE,
    IsVIP CHAR(1)
);

CREATE TABLE Accounts (
    AccountID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    AccountType VARCHAR2(20),
    Balance NUMBER,
    LastModified DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Transactions (
    TransactionID NUMBER PRIMARY KEY,
    AccountID NUMBER,
    TransactionDate DATE,
    Amount NUMBER,
    TransactionType VARCHAR2(10),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)
);

CREATE TABLE Loans (
    LoanID NUMBER PRIMARY KEY,
    CustomerID NUMBER,
    LoanAmount NUMBER,
    InterestRate NUMBER,
    StartDate DATE,
    EndDate DATE,
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

CREATE TABLE Employees (
    EmployeeID NUMBER PRIMARY KEY,
    Name VARCHAR2(100),
    Position VARCHAR2(50),
    Salary NUMBER,
    Department VARCHAR2(50),
    HireDate DATE
);

-- ========== 2. SAMPLE DATA ==========

-- Elderly and young customers, some VIP candidates
INSERT INTO Customers VALUES (1, 'John Doe', TO_DATE('1950-05-15', 'YYYY-MM-DD'), 15000, SYSDATE, NULL);
INSERT INTO Customers VALUES (2, 'Jane Smith', TO_DATE('1990-07-20', 'YYYY-MM-DD'), 8000, SYSDATE, NULL);
INSERT INTO Customers VALUES (3, 'Elder Joe', TO_DATE('1940-03-01', 'YYYY-MM-DD'), 12000, SYSDATE, NULL);

-- Accounts
INSERT INTO Accounts VALUES (1, 1, 'Savings', 1000, SYSDATE);
INSERT INTO Accounts VALUES (2, 2, 'Checking', 1500, SYSDATE);
INSERT INTO Accounts VALUES (3, 3, 'Savings', 2000, SYSDATE);

-- Transactions
INSERT INTO Transactions VALUES (1, 1, SYSDATE, 200, 'Deposit');
INSERT INTO Transactions VALUES (2, 2, SYSDATE, 300, 'Withdrawal');

-- Loans: 2 due in <30 days, 1 not
INSERT INTO Loans VALUES (1, 1, 5000, 5, SYSDATE, SYSDATE + 15);  -- Due soon
INSERT INTO Loans VALUES (2, 2, 8000, 7, SYSDATE, SYSDATE + 90); -- Not due soon
INSERT INTO Loans VALUES (3, 3, 10000, 6, SYSDATE, SYSDATE + 10); -- Due soon

-- Employees
INSERT INTO Employees VALUES (1, 'Alice Johnson', 'Manager', 70000, 'HR', TO_DATE('2015-06-15', 'YYYY-MM-DD'));
INSERT INTO Employees VALUES (2, 'Bob Brown', 'Developer', 60000, 'IT', TO_DATE('2017-03-20', 'YYYY-MM-DD'));

COMMIT;

-- ========== 3. SCENARIO 1: INTEREST DISCOUNT FOR AGE > 60 ==========

BEGIN
    FOR cust IN (SELECT CustomerID, DOB FROM Customers) LOOP
        IF TRUNC(MONTHS_BETWEEN(SYSDATE, cust.DOB) / 12) > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = cust.CustomerID;

            DBMS_OUTPUT.PUT_LINE('✅ Applied 1% interest discount for CustomerID ' || cust.CustomerID);
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ========== 4. SCENARIO 2: SET IsVIP = 'Y' IF BALANCE > 10000 ==========

BEGIN
    FOR cust IN (SELECT CustomerID, Balance FROM Customers) LOOP
        IF cust.Balance > 10000 THEN
            UPDATE Customers SET IsVIP = 'Y' WHERE CustomerID = cust.CustomerID;
            DBMS_OUTPUT.PUT_LINE('🌟 CustomerID ' || cust.CustomerID || ' marked as VIP');
        ELSE
            UPDATE Customers SET IsVIP = 'N' WHERE CustomerID = cust.CustomerID;
            DBMS_OUTPUT.PUT_LINE('ℹ️ CustomerID ' || cust.CustomerID || ' marked as Non-VIP');
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- ========== 5. SCENARIO 3: LOAN DUE REMINDERS (WITHIN 30 DAYS) ==========

BEGIN
    FOR loan IN (
        SELECT l.CustomerID, l.EndDate
        FROM Loans l
        WHERE l.EndDate <= SYSDATE + 30
    ) LOOP
        DECLARE
            v_name Customers.Name%TYPE;
        BEGIN
            SELECT Name INTO v_name FROM Customers WHERE CustomerID = loan.CustomerID;
            DBMS_OUTPUT.PUT_LINE('📢 Reminder: Loan for ' || v_name || ' is due on ' ||
                                 TO_CHAR(loan.EndDate, 'YYYY-MM-DD'));
        END;
    END LOOP;
END;
/

-- ========== 6. OPTIONAL: FINAL STATE CHECK (USE MANUALLY) ==========

-- SELECT * FROM Loans;
-- SELECT CustomerID, Name, Balance, IsVIP FROM Customers;
