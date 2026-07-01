-- Scenario 1: Monthly interest processing for savings accounts

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
    v_interest_rate NUMBER := 0.01;  -- 1% monthly interest
BEGIN
UPDATE Accounts
SET Balance = Balance + (Balance * v_interest_rate),
    LastModified = SYSDATE
WHERE AccountType = 'Savings';

DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' savings accounts updated with monthly interest.');

COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error processing interest: ' || SQLERRM);
        RAISE;
END ProcessMonthlyInterest;
/

-- Execute:
EXEC ProcessMonthlyInterest;

-- Scenario 2: Employee bonus by department

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_department   IN VARCHAR2,
    p_bonus_percent IN NUMBER
) AS
BEGIN
    IF p_bonus_percent < 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Bonus percentage cannot be negative.');
END IF;

UPDATE Employees
SET Salary = Salary + (Salary * p_bonus_percent / 100)
WHERE Department = p_department;

IF SQL%ROWCOUNT = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No employees found in department: ' || p_department);
ELSE
        DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT || ' employee(s) in ' || p_department ||
                              ' received a ' || p_bonus_percent || '% bonus.');
END IF;

COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error updating bonus: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/

-- Execute example:
EXEC UpdateEmployeeBonus('IT', 10);

-- Scenario 3: Transfer funds between accounts

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_source_account IN Accounts.AccountID%TYPE,
    p_target_account IN Accounts.AccountID%TYPE,
    p_amount         IN NUMBER
) AS
    v_source_balance Accounts.Balance%TYPE;
    v_next_txn_id    Transactions.TransactionID%TYPE;

    insufficient_funds EXCEPTION;
    invalid_amount     EXCEPTION;
BEGIN
    IF p_amount <= 0 THEN
        RAISE invalid_amount;
END IF;

    -- Lock the source row to avoid race conditions during the check
SELECT Balance INTO v_source_balance
FROM Accounts
WHERE AccountID = p_source_account
    FOR UPDATE;

IF v_source_balance < p_amount THEN
        RAISE insufficient_funds;
END IF;

    -- Debit source account
UPDATE Accounts
SET Balance = Balance - p_amount,
    LastModified = SYSDATE
WHERE AccountID = p_source_account;

-- Credit target account
UPDATE Accounts
SET Balance = Balance + p_amount,
    LastModified = SYSDATE
WHERE AccountID = p_target_account;

-- Log both sides of the transfer in Transactions
SELECT NVL(MAX(TransactionID), 0) + 1 INTO v_next_txn_id FROM Transactions;
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (v_next_txn_id, p_source_account, SYSDATE, p_amount, 'Withdrawal');

SELECT NVL(MAX(TransactionID), 0) + 1 INTO v_next_txn_id FROM Transactions;
INSERT INTO Transactions (TransactionID, AccountID, TransactionDate, Amount, TransactionType)
VALUES (v_next_txn_id, p_target_account, SYSDATE, p_amount, 'Deposit');

COMMIT;
DBMS_OUTPUT.PUT_LINE('Transfer of ' || p_amount || ' from Account ' ||
                          p_source_account || ' to Account ' || p_target_account || ' completed.');

EXCEPTION
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed: insufficient balance in source account.');
WHEN invalid_amount THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed: transfer amount must be positive.');
WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed: source account not found.');
WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer failed: ' || SQLERRM);
        RAISE;
END TransferFunds;
/

-- Execute example:
EXEC TransferFunds(1, 2, 100);