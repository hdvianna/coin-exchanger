# Coin Exchanger

Coin Exchanger exchanges bills for coins. Given a set of with their corresponding amounts, the coin exchanger will try to change the total amount of bills for coins.

For example, given the following set:
- two bills of ten
- three bills of five

Coin exchanger will change the total amount (twenty-five) for coins using the least coin amount strategy if the there is enough coins for change. 

## Configuration 

The Coin Exchanger is set to accept the following bill values: 1, 2, 5, 10, 20, 50, and 100. Also, it is set to start with the following coin state:

- 10,000,000 of 1 cent
- 5,000,000 of 5 cents
- 1,000,000 of 10 cents
- 750,000 of 25 cents
- 500,000 of 50 cents

> Bills and coin state are set in `hdvianna.coinexchanger.usecase.DefaultCoinExchangerInstance` class

## Running 

The _Coin Exchanger_ is delivery as a client application. To build it run `./gradlew build` in the terminal, and `./gradlew bootRun` to start it.

After the shell is ready you can exchange bill for coins using the `change` command. This commands accepts a sequence of bills and amounts in the following format: `change <bill> <amount> ...`. 

For example, the command `change 1 10 5 1` will try to change ten bills of value one and one bill of five for coins.  



