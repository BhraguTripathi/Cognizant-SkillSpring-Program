-- Scenario 1: Loan interest discount for customers above 60

BEGIN
FOR cust_rec IN (
        SELECT CustomerID, DOB
        FROM Customers
    )
    LOOP
        IF MONTHS_BETWEEN(SYSDATE, cust_rec.DOB) / 12 > 60 THEN
UPDATE Loans
SET InterestRate = InterestRate - (InterestRate * 0.01)
WHERE CustomerID = cust_rec.CustomerID;

DBMS_OUTPUT.PUT_LINE('Discount applied for CustomerID: ' || cust_rec.CustomerID);
END IF;
END LOOP;

COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;

-- Scenario 2: Flag VIP customers based on balance

-- Run once to add the flag column (skip if it already exists)
ALTER TABLE Customers ADD IsVIP VARCHAR2(5) DEFAULT 'FALSE';

BEGIN
FOR cust_rec IN (
        SELECT CustomerID, Balance
        FROM Customers
    )
    LOOP
        IF cust_rec.Balance > 10000 THEN
UPDATE Customers
SET IsVIP = 'TRUE'
WHERE CustomerID = cust_rec.CustomerID;

DBMS_OUTPUT.PUT_LINE('CustomerID ' || cust_rec.CustomerID || ' marked as VIP.');
ELSE
UPDATE Customers
SET IsVIP = 'FALSE'
WHERE CustomerID = cust_rec.CustomerID;
END IF;
END LOOP;

COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;

-- Scenario 3: Loan due reminders (next 30 days)

DECLARE
CURSOR loan_cursor IS
SELECT l.LoanID, l.CustomerID, c.Name, l.EndDate, l.LoanAmount
FROM Loans l
         JOIN Customers c ON l.CustomerID = c.CustomerID
WHERE l.EndDate BETWEEN SYSDATE AND SYSDATE + 30;
BEGIN
FOR loan_rec IN loan_cursor
    LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Dear ' || loan_rec.Name ||
            ', your loan (ID: ' || loan_rec.LoanID ||
            ', Amount: ' || loan_rec.LoanAmount ||
            ') is due on ' || TO_CHAR(loan_rec.EndDate, 'DD-MON-YYYY')
        );
END LOOP;
END;

