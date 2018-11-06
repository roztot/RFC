package rentaFastCarDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import rentaFastCar.Booking;
import rentaFastCar.Car;
import rentaFastCar.Customer;
import rentaFastCarFX.CustomerProperty;

public class BookingDatabase {

	public static final String DBlocation = "C:\\Eclipse_Databases\\DB";
	public static final String connString = "jdbc:derby:" + DBlocation + ";create=true";

	// neues Auto hinzufügen
	public static boolean insertAuto(Car car) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmtcar = null;

		try {
			conn = DriverManager.getConnection(connString);

			pstmtcar = conn.prepareStatement("INSERT INTO AUTO"
					+ "( KENNZEICHEN, MODEL, KILOMETER, PREIS_PRO_TAG, KATEGORIE) VALUES (?, ?, ?, ?, ?)");
			// st.RETURN_GENERATED_KEYS);
			pstmtcar.setString(1, car.getKennzeichen());
			pstmtcar.setString(2, car.getModel());
			pstmtcar.setInt(3, car.getKilometer());
			pstmtcar.setDouble(4, car.getPreisProTag());
			pstmtcar.setString(5, car.getKategorie());
			pstmtcar.executeUpdate();
			System.out.println("Auto added");
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM AUTO";
			ResultSet rs = conn.createStatement().executeQuery(identity);
			// pstmtcar.getGeneratedKeys(); conn.createStatement().executeQuery(identity);
			rs.next();
			car.setAutoId(rs.getInt("1"));
			return true;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmtcar != null)
					pstmtcar.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return false;

			}
		}
	}

	// Ein Auto ändern/bearbeiten
	public static void updateAuto(Car car) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmtcar = null;

		try {
			conn = DriverManager.getConnection(connString);

			String updateAuto = "UPDATE AUTO SET KENNZEICHEN = ?, MODEL = ?, KILOMETER = ?, PREIS_PRO_TAG = ?, KATEGORIE = ?"
					+ "WHERE AUTOID = ?";
			pstmtcar = conn.prepareStatement(updateAuto);
			pstmtcar.setString(1, car.getKennzeichen());
			pstmtcar.setString(2, car.getModel());
			pstmtcar.setInt(3, car.getKilometer());
			pstmtcar.setDouble(4, car.getPreisProTag());
			pstmtcar.setString(5, car.getKategorie());
			pstmtcar.setInt(6, car.getAutoId());
			pstmtcar.executeUpdate();

			return;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmtcar != null)
					pstmtcar.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
			}

		}
	}

	// Ein Auto aus der DB löschen
	public static void deleteCar(int carId) throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();
			String delete = "DELETE FROM AUTO WHERE AUTOID = " + carId;
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// neuen Kunden erzeugen
	public static boolean insertKunde(Customer customer) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmtcust = null;
		try {
			conn = DriverManager.getConnection(connString);

			pstmtcust = conn.prepareStatement(
					"INSERT INTO KUNDE" + "( NAME, ADRESSE, GEBURTSDATUM, FUERERSCHEIN_NUMMER) VALUES (?, ?, ?, ?)");
			// st.RETURN_GENERATED_KEYS);
			// pstmtcust.setInt(1, customer.getKundenID()); -> wird automatisch erzeugt
			pstmtcust.setString(1, customer.getName());
			pstmtcust.setString(2, customer.getAdresse());
			pstmtcust.setDate(3, (java.sql.Date) customer.getGeburtsDatum());
			pstmtcust.setString(4, customer.getFuehrerscheinNummer());
			pstmtcust.executeUpdate();
			System.out.println("Customer inserted");
			// neueste kdID beschaffen
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM KUNDE"; // Derby Befehl
			ResultSet rs = conn.createStatement().executeQuery(identity);
			rs.next();
			customer.setKundenId(rs.getInt("1"));
			return true;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmtcust != null)
					pstmtcust.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return false;

			}

		}

	}

	// einen Kunden ändern
	public static void updateKunde(Customer customer) throws SQLException {

		Connection conn = null;
		PreparedStatement pstmtcust = null;

		try {
			conn = DriverManager.getConnection(connString);

			String updateCustomer = "UPDATE KUNDE SET NAME = ?, ADRESSE = ?, GEBURTSDATUM = ?, FUERERSCHEIN_NUMMER =?"
					+ "WHERE KUNDENID = ?";
			pstmtcust = conn.prepareStatement(updateCustomer);
			pstmtcust.setString(1, customer.getName());
			pstmtcust.setString(2, customer.getAdresse());
			pstmtcust.setDate(3, (java.sql.Date) customer.getGeburtsDatum());
			pstmtcust.setString(4, customer.getFuehrerscheinNummer());
			pstmtcust.setInt(5, customer.getKundenId());
			pstmtcust.executeUpdate();

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmtcust != null)
					pstmtcust.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return;
			}

		}
	}

	// einen Kunden löschen
	public static void deleteCustomer(int kundenId) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();
			String delete = "DELETE FROM KUNDE WHERE KUNDENID = " + kundenId;
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// Alle Kunden aus DB in der KundenTableView auflisten
	@SuppressWarnings("finally")
	public static ArrayList<Customer> loadCustomerList() throws SQLException {

		ArrayList<Customer> custList = new ArrayList<Customer>();
		Connection conn = null;
		Statement stmt = null;
		try {

			conn = DriverManager.getConnection(connString);

			stmt = conn.createStatement();
			String query = "SELECT * FROM KUNDE";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				custList.add(new Customer(rs.getInt("KUNDENID"), rs.getString("NAME"), rs.getString("ADRESSE"),
						rs.getDate("GEBURTSDATUM"), rs.getString("FUERERSCHEIN_NUMMER")));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

			return custList;
		}

	}

	// alle Autos aus DB  im AutosTableView auflisten
	public static ArrayList<Car> loadCarsList() throws SQLException {

		ArrayList<Car> carsList = new ArrayList<Car>();

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();

			String query = "SELECT * FROM AUTO";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				carsList.add(new Car(rs.getInt("AUTOID"), rs.getString("KENNZEICHEN"), rs.getString("MODEL"),
						rs.getInt("KILOMETER"), rs.getDouble("PREIS_PRO_TAG"), rs.getString("KATEGORIE")));

			}

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return carsList;
	}

	// selektieren mit der comboBox und DatePickern in der AutoTabele von StartBookingDialog
	public static ArrayList<Car> loadCarsList(String kategorie,LocalDate uebergabe, LocalDate ruecknahme)
			throws SQLException {

		ArrayList<Car> carsList = new ArrayList<Car>();
		ArrayList<Car> finalCarList = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();

			String query = "SELECT * FROM AUTO WHERE  ";
			if (kategorie != null) {
				query += "  AUTO.KATEGORIE = '"+kategorie+"'";
				
			}
			
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Car car = new Car(rs.getInt("AUTOID"), rs.getString("KENNZEICHEN"), rs.getString("MODEL"),
						rs.getInt("KILOMETER"), rs.getDouble("PREIS_PRO_TAG"), rs.getString("KATEGORIE"));

				carsList.add(car);
			}
				for (Car c : carsList) {
					query = "SELECT * FROM MIETE WHERE MIETE.AUTOID = " + c.getAutoId();
					boolean autoFound = false;
					rs = stmt.executeQuery(query);
					while (rs.next()) {
						autoFound = true;
						java.sql.Date rd = rs.getDate("RUECKNAHME");
						java.sql.Date ud = rs.getDate("UEBERGABE");
						
						if(rs.getDate("RUECKNAHME").before( java.sql.Date.valueOf(uebergabe)) ||
			                      java.sql.Date.valueOf(ruecknahme).before(rs.getDate("UEBERGABE"))) {

			                            finalCarList.add(c); 
						
//					}else {
//						finalCarList.add(car);
				
				}
				}
					if(autoFound == false)
						finalCarList.add(c);
					
			}
			
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return finalCarList;
	}


	// Neue Miete speichern
	public static boolean createBooking(Booking booking) throws SQLException {

		PreparedStatement pstmtbooking = null;
		Connection conn = null;
		try {

			conn = DriverManager.getConnection(connString);
			pstmtbooking = conn.prepareStatement(
					"INSERT INTO MIETE" + "(AUTOID, KUNDENID, UEBERGABE, RUECKNAHME, GESAMTPREIS, KILOMETER)"
							+ " VALUES (?, ?, ?, ? ,?, ?)");
			// st.RETURN_GENERATED_KEYS);

			pstmtbooking.setInt(1, booking.getAutoId());
			pstmtbooking.setInt(2, booking.getKundenId());
			pstmtbooking.setDate(3, (java.sql.Date) booking.getUebergabeDatum());
			pstmtbooking.setDate(4, (java.sql.Date) booking.getRuecknahmeDatum());
			pstmtbooking.setDouble(5, booking.getGesamtPreis());
			pstmtbooking.setInt(6, booking.getKilometer());
			pstmtbooking.executeUpdate();
			String identity = "SELECT IDENTITY_VAL_LOCAL() FROM MIETE"; //neueste mietId holen
			ResultSet rs = conn.createStatement().executeQuery(identity);
			rs.next();
			int mietID = booking.setMietId(rs.getInt("1"));
			booking.setMietId(mietID);
			System.out.println("Miete gestartet");
			return true;
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (pstmtbooking != null)
					pstmtbooking.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				return false;

			}
		}

	}

	// Update einer Miete die beendet wird, setzen der fählenden Werte in der DB -> Kilometer
	public static void finishBookingKm(int autoId, int kilometer) throws SQLException {
		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();

			String update = "UPDATE MIETE SET KILOMETER =  " + kilometer + "  WHERE MIETE.AUTOID = " + autoId;

			stmt.execute(update);

			System.out.println("Miete beendet");

		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}
		}
		return;

	}

	// Update einer Miete die beendet wird, setzen der fählenden Werte in der DB -> Tankstand
	public static void updateBookingTank(int mietId, boolean tankstand) {

		Statement stmt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("Tankstand setzen");

		try {
			conn = DriverManager.getConnection(connString);
			pstmt = conn.prepareStatement("UPDATE MIETE SET TANKSTAND = " + tankstand + " WHERE MIETE.MIETID = " + mietId);
			pstmt.executeUpdate();
			System.out.println("Miete mit MietId: " + mietId + " aktualisiert");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				stmt = null;
				if (conn != null)
					conn.close();
				conn = null;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

		}

	}

	// Update einer Miete die beendet wird, setzen der fählenden Werte in der DB -> Schäden
	public static void updateBookingSchaden(int mietId, boolean schaden) {

		Statement stmt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("Schaden setzen");

		try {
			conn = DriverManager.getConnection(connString);
			pstmt = conn.prepareStatement("UPDATE MIETE SET SCHAEDEN = " + schaden + " WHERE MIETE.MIETID = " + mietId);
			pstmt.executeUpdate();
			System.out.println("Miete mit MietId: " + mietId + " aktualisiert");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				stmt = null;
				if (conn != null)
					conn.close();
				conn = null;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

		}
	}
	
	// Update einer Miete die beendet wird, setzen der fählenden Werte in der DB -> Gesamtpreis
	public static void updateBookingPreis(int mietId, double gesamtPreis) {

		Statement stmt = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("Gesamtpreis ändern");

		try {
			conn = DriverManager.getConnection(connString);
			pstmt = conn.prepareStatement("UPDATE MIETE SET GESAMTPREIS = " + gesamtPreis + "WHERE MIETID = " + mietId);
			pstmt.executeUpdate();
			System.out.println("Miete mit MietId: " + mietId + " aktualisiert");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				stmt = null;
				if (conn != null)
					conn.close();
				conn = null;
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

		}
	}

	// Alle Mieten auflisten für die BookingTableView
	@SuppressWarnings("finally")
	public static ArrayList<Booking> getBookingList() throws SQLException {

		// LocalDate lDatel =
		// lDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// java.sql.Date date = java.sql.Date.valueOf(lDatel);
		//
		ArrayList<Booking> bookingList = new ArrayList<Booking>();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();
			String query = "SELECT MIETE.MIETID AS MIETID, MIETE.AUTOID AS AUTOID, MIETE.KUNDENID AS KUNDENID, KUNDE.NAME AS NAME,"
					+ " AUTO.KENNZEICHEN AS KENNZEICHEN, MIETE.UEBERGABE AS UEBERGABE, MIETE.RUECKNAHME AS RUECKNAHME, MIETE.GESAMTPREIS"
					+ " AS GESAMTPREIS"
					+ " FROM MIETE, AUTO, KUNDE WHERE MIETE.AUTOID = AUTO.AUTOID AND MIETE.KUNDENID = KUNDE.KUNDENID";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				// if(date.equals(rs.getDate("UEBERGABE"))){
				bookingList.add(new Booking(rs.getInt("MIETID"), rs.getInt("AUTOID"), rs.getInt("KUNDENID"),
						rs.getString("NAME"), rs.getString("KENNZEICHEN"), rs.getDate("UEBERGABE"),
						rs.getDate("RUECKNAHME"), rs.getDouble("GESAMTPREIS")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

			return bookingList;
		}

	}

	//Eine bestimmte Miete abfragen für die Rechnung Kalkulation
	@SuppressWarnings("finally")
	public static Booking getBooking(int mietId, int carId, int customerId) throws SQLException {

		// LocalDate lDatel =
		// lDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		// java.sql.Date date = java.sql.Date.valueOf(lDatel);
		//
		Booking booking = new Booking();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();
			String query = " SELECT MIETE.MIETID AS MIETID, MIETE.AUTOID AS AUTOID, MIETE.KUNDENID AS KUNDENID, KUNDE.NAME AS NAME, "
					+ " KUNDE.ADRESSE AS ADRESSE, AUTO.KENNZEICHEN AS KENNZEICHEN, AUTO.MODEL AS MODEL, AUTO.PREIS_PRO_TAG AS PREIS_PRO_TAG, "
					+ " MIETE.UEBERGABE AS UEBERGABE, MIETE.RUECKNAHME AS RUECKNAHME, MIETE.GESAMTPREIS"
					+ " AS GESAMTPREIS, MIETE.KILOMETER AS KILOMETER, MIETE.TANKSTAND AS TANKSTAND, MIETE.SCHAEDEN AS SCHAEDEN "
					+ " FROM MIETE, AUTO, KUNDE WHERE MIETE.MIETID = MIETID AND MIETE.AUTOID = AUTO.AUTOID AND MIETE.KUNDENID = KUNDE.KUNDENID ";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				booking.setMietId(rs.getInt("MIETID"));
				booking.setKundenId(rs.getInt("KUNDENID"));
				booking.setAutoId(rs.getInt("AUTOID"));
				booking.setName(rs.getString("NAME"));
				booking.setAdresse(rs.getString("ADRESSE"));
				booking.setKennzeichen(rs.getString("KENNZEICHEN"));
				booking.setModel(rs.getString("MODEL"));
				booking.setPreisProTag(rs.getDouble("PREIS_PRO_TAG"));
				booking.setUebergabeDatum(rs.getDate("UEBERGABE"));
				booking.setRuecknahmeDatum(rs.getDate("RUECKNAHME"));
				booking.setGesamtPreis(rs.getDouble("GESAMTPREIS"));
				booking.setKundenId(rs.getInt("KILOMETER"));
				booking.setTankstand(rs.getBoolean("TANKSTAND"));
				booking.setSchaeden(rs.getBoolean("SCHAEDEN"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}

			return booking;
		}

	}

	// Listung der Kategorien in der comboBox für die Selektion -> 
	// in eine TreeSet, damit die doppelten Einträge aussortiert werden
	public static ArrayList<String> chooseCategories() throws SQLException {

		ArrayList<String> list = new ArrayList<>();
		Connection conn = null;
		Statement pstmt = null;
		try {
			conn = DriverManager.getConnection(connString);
			pstmt = conn.createStatement();
			ResultSet rs = pstmt.executeQuery("SELECT * FROM AUTO");
			TreeSet<String> ts = new TreeSet<>();
			while (rs.next()) {
				ts.add(rs.getString("KATEGORIE"));
			}
			for (String s : ts) {
				list.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();

			}
		}
		return list;
	}
}

//  Nach Customer Id filtern in der TableView -> die Suche mit TextProperty gelöst
//	@SuppressWarnings("unchecked")
//	public static ArrayList<CustomerProperty> CustomerIdList(int kundenId) throws SQLException {
//		ArrayList<CustomerProperty> custIdList = new ArrayList<>();
//		Connection conn = null;
//		Statement pstmt = null;
//		try {
//
//			conn = DriverManager.getConnection(connString);
//			pstmt = conn.createStatement();
//			ResultSet rs = pstmt.executeQuery("SELECT * FROM KUNDE WHERE KUNDENID =+ kundenId");
//			while (rs.next()) {
//				custIdList.addAll((Collection<? extends CustomerProperty>) new Customer(rs.getInt("KUNDENID"),
//						rs.getString("NAME"), rs.getString("ADRESSE"), rs.getDate("GEBURTSDATUM"),
//						rs.getString("FUERERSCHEIN_NUMMER")));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//
//			}
//		}
//		return custIdList;
//
//	}
//
//	// Auswahl für Namensuche im TextField-nicht verwendet, da Textfeldsuche auch
//	// ohne funktioniert
//	public static ArrayList<String> searchCustomer(String name) throws SQLException {
//		ArrayList<String> nameList = new ArrayList<>();
//		Connection conn = null;
//		Statement pstmt = null;
//		try {
//
//			conn = DriverManager.getConnection(connString);
//
//			pstmt = conn.createStatement();
//			ResultSet rs = pstmt.executeQuery("SELECT * FROM KUNDE WHERE NAME LIKE 'ABC%'");
//			while (rs.next()) {
//				nameList.add(rs.getString("NAME"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//
//			}
//		}
//		return nameList;
//	}
//
//	// Auswahl für KundenIdsuche im TextField-nicht verwendet, da Textfeldsuche auch
//	// ohne funktioniert
//	public static ArrayList<Integer> searchCustomerIds() throws SQLException {
//		ArrayList<Integer> custIdsList = new ArrayList<>();
//		Connection conn = null;
//		Statement pstmt = null;
//		try {
//
//			conn = DriverManager.getConnection(connString);
//			pstmt = conn.createStatement();
//			ResultSet rs = pstmt.executeQuery("SELECT * FROM KUNDE");
//			while (rs.next()) {
//				custIdsList.add(rs.getInt("KUNDENID"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if (pstmt != null)
//					pstmt.close();
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				System.out.println(e.getMessage());
//				e.printStackTrace();
//
//			}
//		}
//		return custIdsList;
//
//	}
//
//}