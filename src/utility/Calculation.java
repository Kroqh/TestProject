package utility;

import modellayer.Coin;
import modellayer.Currency;
import modellayer.PPrice;

//TEst team crew
public class Calculation {

	public static double getEuroCoinValueInCent(Coin coin) {
		double valueInCent = 0;
		double coinValue = coin.getAmount();

		if (coin.getCoinType() == Currency.ValidCoinType.INTEGER) {
			valueInCent = coinValue * 100;
		} else {
			valueInCent = coinValue;
		}

		return valueInCent;
	}

	public static double getDkkCoinValueInCent(Coin coin, PPrice price) {
		double valueInCent = 0;
		Currency.ValidCoinType coinType = coin.getCoinType();
		double coinValue = coin.getAmount();

		if (coinType == Currency.ValidCoinType.INTEGER) {
			valueInCent = (coinValue * 100) / price.getExchangeEuroDkk();
		} else {
			valueInCent = coinValue / price.getExchangeEuroDkk();
		}

		return valueInCent;
	}	
}
