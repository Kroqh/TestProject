package modellayer;

import controllayer.ControlPrice;
import controllayer.IllegalCoinException;
import utility.Validation;
import utility.Calculation;

/**
 * Inspired by the book: Flexible, Reliable Software
 * Henrik B�rbak Christensen: Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class PPayStation {
	
	// PayStation ident
	private int id;	
	// PayStaion model
	private String payStationModel;
	// Amount inserted in pay station
	private double amount = 0;
	private ControlPrice controlPrice;
	
	
	public PPayStation(int id, String payStationModel) {
		this.id = id;
		this.payStationModel = payStationModel;
		
		this.controlPrice = new ControlPrice();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPayStationModel() {
		return payStationModel;
	}

	public void setPayStationModel(String payStationModel) {
		this.payStationModel = payStationModel;
	}	
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void addAmount(Coin coin, PPrice currentPrice) {
		
		Currency.ValidCurrency currency = coin.getCurrency();
		
		double valueInCent = 0;

		if (currency == Currency.ValidCurrency.DKK) {
			//PPrice nowPrice = controlPrice.getCurrentPrice();
			valueInCent = Calculation.getDkkCoinValueInCent(coin, currentPrice);
		} else {
			valueInCent = Calculation.getEuroCoinValueInCent(coin);
		}
		
		this.amount += valueInCent;
	}
	
	public int getTimeBoughtInMinutes() {
		
		PPrice aPrice = controlPrice.getCurrentPrice();
		int timeBoughtInMinutes = 0;

		double timeBoughtInSeconds = this.amount * aPrice.getParkingPrice();
		timeBoughtInMinutes = (int) ((timeBoughtInSeconds + 59) / 60);

		return timeBoughtInMinutes;
	}
	
	public void validateCoin(Coin coin) throws IllegalCoinException {
		
		Validation.validateCoin(coin);	
	}
	

	
	
}
