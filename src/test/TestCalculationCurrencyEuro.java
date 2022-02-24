package test;

import org.junit.*;
import static org.junit.Assert.*;

import controllayer.*;
import modellayer.*;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik B�rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestCalculationCurrencyEuro {

	private ControlPayStation ps;

	/** Fixture for pay station testing. */
	@Before
	public void setUp() {
		ps = new ControlPayStation();
	}
	
	/**
	 * Entering no coins should make the display report 0 minutes parking time.
	 */
	@Test
	public void shouldDisplay0() throws IllegalCoinException {
		
		// Arrange
		int expectedParkingTime = 0;
		
		// Act
		// No action
		
		// Assert
		assertEquals("Should display 0 min for no coins", expectedParkingTime, ps.readDisplay());
	}	

	/**
	 * Entering 5 cents should make the display report 2 minutes parking time
	 */
	@Test
	public void shouldDisplay1MinFor1Cents() throws IllegalCoinException {
		
		// Arrange
		int expectedParkingTime = 1;	// In minutes		
		int coinValue = 1;
		Currency.ValidCurrency coinCurrency = Currency.ValidCurrency.EURO;
		Currency.ValidCoinType coinType = Currency.ValidCoinType.FRACTION;
		
		// Act
		ps.addPayment(coinValue, coinCurrency, coinType);
		
		// Assert
		assertEquals("Should display 1 min for 1 cents", expectedParkingTime, ps.readDisplay());
	}

		/**
		 * Entering 1 Euro and 2 Euro  should make the display report 120 minutes parking time
		 */
		@Test
		public void ShouldDisplay120MinFor3Euro() throws IllegalCoinException {
			
			// Arrange
			int expectedParkingTime = 120;		// In minutes
			int coinValue = 1;
			int coinValue2 = 2;
			Currency.ValidCurrency coinCurrency = Currency.ValidCurrency.EURO;
			Currency.ValidCoinType coinType = Currency.ValidCoinType.INTEGER;
			
			// Act
			ps.addPayment(coinValue, coinCurrency, coinType);
			ps.addPayment(coinValue2, coinCurrency, coinType);
			
			// Assert
			assertEquals("Should display 120 min for 3 Euro", expectedParkingTime, ps.readDisplay());
			
		}
}
