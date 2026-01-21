## **Trade-flow**

A trade is a buy or sell operation executed for an account.
Trade execution is performed within a single DB transaction to 
ensure atomicity and consistency.

### Trade execution:

1. Validate that the account exists;
2. Validate that the asset exists;
3. Validate the trade quantity.

Depending on tradeSide, we have BUY and SELL. 

**If BUY:**
1. Fetch asset price from Alpha Vantage external API; 
2. Check if account has enough cash (by multiplying asset price with trade quantity); 
3. Create a new trade, set trade side to BUY; 
4. Create 3 trade entries: cash (debit), asset(credit), fee(debit); 
5. Update new account balance; 
6. Create a new position or update existing position for the asset. 

**If SELL:**
1. Validate that the account holds a position for this asset;
2. Validate that there is enough assets to sell;
3. Fetch asset price from Alpha Vantage external API; 
4. Create a new trade, set trade side to SELL; 
5. Create 3 trade entries: cash (credit), asset(debit), fee(debit); 
6. Update new account balance; 
7. Adjust position.