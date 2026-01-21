## **Domain Model Documentation**


### 1. User

* Represents an application user.
* A user can open and manage one or more brokerage accounts.

### 2. Account

* Represents a user's brokerage account.
* Holds available cash balance.
* Contains list of positions and transaction entries.

### 3. Transaction

* Represents a cash-related event affecting an account.
* A transaction consists of one or more transaction entries.

### 4. TransactionEntry

* Represents individual cash movements like deposits, withdrawals or transfers.

### 5. Position

* Represents an asset position held by an account
* A position is the result of a trade event.
* A position is updated based on trade entries affecting the account.

### 6. Trade

* Represents the trade events affecting one's account.
* A trade consists of one or more trade entries

### 7. TradeEntry

* Represents individual components of a trade.
* Entries which are defined by an entry type: cash, asset or fee.
* Trade entries define the outcome of a trade.

### 8. Asset

* Represents an asset available for trading.
* Does not contain prices. Price are fetched from Alpha Vantage API.