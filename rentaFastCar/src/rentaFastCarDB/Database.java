package rentaFastCarDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static final String DBlocation = "C:\\Eclipse_Databases\\DB";
	public static final String connString = "jdbc:derby:" + DBlocation + ";create=true";

	//Löschen der Tabelen aus DB
	public static void dropTable() throws SQLException {

		String dropTableMiete = "DROP TABLE " + "MIETE";

		String dropTableAuto = "DROP TABLE " + "AUTO";

		String dropTableKunde = "DROP TABLE " + "KUNDE";

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();
			stmt.executeUpdate(dropTableMiete);
			stmt.executeUpdate(dropTableAuto);
			stmt.executeUpdate(dropTableKunde);

			System.out.println("Tables dropped");

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
				return;
			}
		}
	}

	//Erzeugung der Tabellen Miete, Auto und Kunde in der DB
	public static void createTable() throws SQLException {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();

			try {

				String createTableAuto = "CREATE TABLE AUTO ("
						+ "AUTOID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
						+ "KENNZEICHEN VARCHAR(20), " + "MODEL VARCHAR(30), " + "KILOMETER INT NOT NULL, "
						+ "PREIS_PRO_TAG DECIMAL NOT NULL, " + "KATEGORIE VARCHAR(20), " + "CONSTRAINT primary_key PRIMARY KEY( AUTOID ))";

				String createTableKunde = "CREATE TABLE KUNDE ("
						+ "KUNDENID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
						+ "NAME VARCHAR(30)NOT NULL, " + "ADRESSE VARCHAR(50)NOT NULL, " + "GEBURTSDATUM DATE NOT NULL,"
						+ "FUERERSCHEIN_NUMMER  VARCHAR(20)NOT NULL," + "CONSTRAINT kundep_key PRIMARY KEY( KUNDENID))";

				String createTableMiete = "CREATE TABLE MIETE ("
						+ "MIETID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
						+ "AUTOID BIGINT NOT NULL,"  + "KUNDENID BIGINT NOT NULL,"
						+ "UEBERGABE DATE NOT NULL," + "RUECKNAHME DATE NOT NULL," 
						+ "GESAMTPREIS  DECIMAL NOT NULL," 
						+ "KILOMETER INTEGER NOT NULL, " + "TANKSTAND BOOLEAN , " + "SCHAEDEN BOOLEAN , "
						+ "CONSTRAINT AUTOID_FK FOREIGN KEY ( AUTOID ) REFERENCES AUTO ( AUTOID ),"
						+ "CONSTRAINT KUNDENID_FK FOREIGN KEY ( KUNDENID ) REFERENCES KUNDE ( KUNDENID),"
						+ "PRIMARY KEY( MIETID ))";

				stmt.executeUpdate(createTableAuto);
				System.out.println("AutosTable created");

				stmt.executeUpdate(createTableKunde);
				System.out.println("KundenTable created");

				stmt.executeUpdate(createTableMiete);
				System.out.println("MietenTable created");

			} catch (Exception e) {
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
				return;

			}

		}

	}
// Einfügen eines Autos in die AutosTable -> nur zum Testzweck
	public static void insertCar() throws SQLException {

		Connection conn = null;
		Statement stmt = null;

		try {
			conn = DriverManager.getConnection(connString);
			stmt = conn.createStatement();

			String insertCar = "INSERT INTO AUTO" + "(KENNZEICHEN,MODEL,KILOMETER,PREIS_PRO_TAG,KATEGORIE) VALUES"
					+ "('W123XY1', 'BMW_M4', 1234, 349.00 , 'SPORT')";

			stmt.executeUpdate(insertCar);
			System.out.println("Auto added");

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
				return;

			}
		}
	}
}