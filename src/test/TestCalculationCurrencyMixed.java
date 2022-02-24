package test;

import org.junit.*;
import static org.junit.Assert.*;

import controllayer.*;
import modellayer.*;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik B�rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestCalculationCurrencyMixed {

	ControlPayStation ps;

	/** Fixture for pay station testing. */
	@Before
	public void setUp() {
		ps = new ControlPayStation();
	}

	/**
	 * Entering 1 cent and 50 �re should make the display report 4 minutes parking time.
	 */
	@Test
	public void shouldDisplay4MinFor1CentAnd50Ore() throws IllegalCoinException {
		System.out.println("Test 1");
		// Arrange
		int expectedParkingTime = 4;
		int coinValueEURO = 1;
		int coinValueDKK = 50;
		Currency.ValidCurrency coinCurrencyEURO = Currency.ValidCurrency.EURO;
		Currency.ValidCurrency coinCurrencyDKK = Currency.ValidCurrency.DKK;
		Currency.ValidCoinType coinType = Currency.ValidCoinType.FRACTION;
		
		// Act
		ps.addPayment(coinValueDKK, coinCurrencyDKK, coinType);
		ps.addPayment(coinValueEURO, coinCurrencyEURO, coinType);
		// Assert
		assertEquals("Should display 4 min for 1 cent and 1 �re", expectedParkingTime, ps.readDisplay());		
	}

	
	/** Fixture for pay station testing. */
	@After
	public void cleanUp() {
		ps.setReady();
	}
	
	/**
	 * Entering 5, 2 Euro, 50 øre(DKK), 10kr. (DKK), 50 øre(NOK) and 20kr. (NOK) should make the display report 138 minutes parking time.
	 * @throws IllegalCoinException 
	 */
	@Test(expected = IllegalCoinException.class)
	public void shouldThrowIllegalCoinExceptionOnNOKInput() throws IllegalCoinException {
		System.out.println("Test 2");
		int expectedParkingTime = 138;
		int coinValueCent = 5;
		int coinValueEURO = 2;
		int coinValueDKKØre = 50;
		int coinValueDKK = 10;
		int coinValueNOKØre = 50;
		int coinValueNOK = 20;
		Currency.ValidCurrency coinCurrencyEURO = Currency.ValidCurrency.EURO;
		Currency.ValidCurrency coinCurrencyDKK = Currency.ValidCurrency.DKK;
		Currency.ValidCurrency coinCurrencyNOK = Currency.ValidCurrency.NOK;
		Currency.ValidCoinType coinTypeFraction = Currency.ValidCoinType.FRACTION;
		Currency.ValidCoinType coinTypeInteger = Currency.ValidCoinType.INTEGER;
		
		// Act
		ps.addPayment(coinValueCent, coinCurrencyEURO, coinTypeFraction);
		ps.addPayment(coinValueEURO, coinCurrencyEURO, coinTypeInteger);
		ps.addPayment(coinValueDKKØre, coinCurrencyDKK, coinTypeFraction);
		ps.addPayment(coinValueDKK, coinCurrencyDKK, coinTypeInteger);
		System.out.println(ps.readDisplay());
		ps.addPayment(coinValueNOKØre, coinCurrencyNOK, coinTypeFraction);
		ps.addPayment(coinValueNOK, coinCurrencyNOK, coinTypeInteger);
		System.out.println(ps.readDisplay());
		
		
		// Assert
		assertEquals("Should display 138 min for mixed Coins", expectedParkingTime, ps.readDisplay());
		
	}
	
}
