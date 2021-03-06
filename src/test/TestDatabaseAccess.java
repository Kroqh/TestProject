package test;

import static org.junit.Assert.*;

import org.junit.*;
import java.time.LocalDate;

//import controllayer.ControlPayStation;
//import controllayer.Currency;
//import controllayer.IPayStation;
//import controllayer.IReceipt;
//import controllayer.IllegalCoinException;

import databaselayer.*;
import modellayer.*;
import controllayer.*;

//import static org.junit.Assert.*;

/**
 * Inspired by the book: Flexible, Reliable Software Henrik B?rbak Christensen:
 * Flexible, Reliable Software. Taylor and Francis Group, LLC 2010
 */

public class TestDatabaseAccess {
	
	DBConnection con = null;
	static PBuy tempPBuy;

	/** Fixture for pay station testing. */
	@Before
	public void setUp() {
		con = DBConnection.getInstance();
	}
	
	
	@Test
	public void wasConnected() {
		assertNotNull("Connected - connection cannot be null", con);
		
		DBConnection.closeConnection();
		boolean wasNullified = DBConnection.instanceIsNull();
		assertTrue("Disconnected - instance set to null", wasNullified);
		
		con = DBConnection.getInstance();
		assertNotNull("Connected - connection cannot be null", con);		
	}
	
	
	@Test
	public void wasInsertedBuy() throws DatabaseLayerException {
		
		// Arrange
		LocalDate timeNow = java.time.LocalDate.now();
		double payedCentAmount = 100;
		
		tempPBuy = new PBuy();
		
		PPayStation pStat = new PPayStation(1, "P-423E");
		pStat.setAmount(payedCentAmount);
		tempPBuy.setAssociatedPaystation(pStat);
		tempPBuy.setBuyTime(timeNow);
		
		DatabasePBuy dbPbuy = new DatabasePBuy();
		
		// Act
		
		int key = dbPbuy.insertParkingBuy(tempPBuy);
		
		// Assert
		assertEquals("Buy was inserted succesfully", true, key > 0);
		
	}	
	
	
	@Test
	public void wasRetrievedPriceDatabaselayer() throws DatabaseLayerException {
		// Arrange
		PPrice foundPrice = null;
		int pZoneId = 2;
		DatabasePPrice dbPrice = new DatabasePPrice();

		
		// Act
		foundPrice = dbPrice.getPriceByZoneId(pZoneId);
		// Assert
		assertNotNull("price was recieved",foundPrice);  	
		
	}
	
	
	@Test
	public void wasRetrievedPriceControllayer() throws DatabaseLayerException {

		// Arrange
		PPrice foundPrice = null;
		int pZoneId = 2;
		ControlPrice cp = new ControlPrice();
		
		// Act
		foundPrice = cp.getPriceRemote(pZoneId);
		// Assert
		assertNotNull("price was recieved",foundPrice);  	
		
	}	
	
	
	/** Fixture for pay station testing. */
	@After
	public void cleanUp() {
		DBConnection.closeConnection();
	}	
	
	@AfterClass
	public static void cleanUpWhenFinish() {
		// 	
		
		// Arrange
		DatabasePBuy dbPbuy = new DatabasePBuy();
		int numDeleted = 0;
		
		// Act
		try {
			numDeleted = dbPbuy.deleteParkingBuy(tempPBuy);
		} catch(Exception ex) { 
			System.out.println("Error: " + ex.getMessage());
		} finally {
			DBConnection.closeConnection();
		}
	
		// Assert
		assertEquals("One row deleted", 0, numDeleted );
	}	

}
