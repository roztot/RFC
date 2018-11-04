package rentaFastCar;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;



public class Booking {

	private int autoId;
	private int mietId;
    private int kundenId;
    private String name;
    private String adresse;
    private int kilometer;
    private String model;
    private java.util.Date uebergabeDatum;
    private java.util.Date ruecknahmeDatum;
    private double preisProTag;
    private double gesamtPreis;
    private boolean tankstand;
	private boolean schaeden;
	LocalDate sqlDateU;
	LocalDate sqlDateR;
	private String kennzeichen;

	
	public Booking( int mietID, int autoId, int kundenId, String name, String kennzeichen, java.util.Date uebergabeDatum, 
			java.util.Date ruecknahmeDatum,
			double gesamtPreis, int kilometer, boolean tankstand, boolean schaeden) {
		super();
		this.autoId = autoId;
		this.mietId = mietID;
		this.kundenId = kundenId;
		this.name = name;
		this.kennzeichen = kennzeichen;
		this.uebergabeDatum = uebergabeDatum;
		this.ruecknahmeDatum = ruecknahmeDatum;
		this.gesamtPreis = gesamtPreis;
		this.kilometer = kilometer;
		this.tankstand = tankstand;
		this.schaeden = schaeden;
		
	}
	public Booking(int kilometer, boolean tankstand, boolean schaeden, double gesamtPreis) {
		super();
		this.kilometer = kilometer;
		this.tankstand = tankstand;
		this.schaeden = schaeden;
		this.gesamtPreis = gesamtPreis;
		
	}

	public Booking() {
		// TODO Auto-generated constructor stub
	}
	public Booking(int mietID, int autoId, int kundenId, String name, String kennzeichen,
			Date uebergabeDatum, Date ruecknahmeDatum, double gesamtPreis) {
		super();
		this.mietId = mietID;
		this.autoId = autoId;
		this.kundenId = kundenId;
		this.name = name;
		this.kennzeichen = kennzeichen;
		this.uebergabeDatum = uebergabeDatum;
		this.ruecknahmeDatum = ruecknahmeDatum;
		this.gesamtPreis = gesamtPreis;
	}
	public Booking(int autoId, int kundenId, String name, String kennzeichen, java.util.Date uebergabeDatum, 
			java.util.Date ruecknahmeDatum,
			double gesamtPreis, int kilometer) {
		super();
		this.autoId = autoId;
		this.kundenId = kundenId;
		this.name = name;
		this.kennzeichen = kennzeichen;
		this.uebergabeDatum = uebergabeDatum;
		this.ruecknahmeDatum = ruecknahmeDatum;
		this.gesamtPreis = gesamtPreis;
		this.kilometer = kilometer;
	}

	public Booking( int mietID, int autoId, int kundenId, String name, String adresse, String kennzeichen, 
			String model,double preisProTag, java.util.Date uebergabeDatum, 
			java.util.Date ruecknahmeDatum, 
			double gesamtPreis, int kilometer, boolean tankstand, boolean schaeden) {
		super();
		this.autoId = autoId;
		this.mietId = mietID;
		this.kundenId = kundenId;
		this.name = name;
		this.adresse = adresse;
		this.kennzeichen = kennzeichen;
		this.model = model;
		this.preisProTag = preisProTag;
		this.uebergabeDatum = uebergabeDatum;
		this.ruecknahmeDatum = ruecknahmeDatum;
		this.preisProTag = preisProTag;
		this.gesamtPreis = gesamtPreis;
		this.kilometer = kilometer;
		this.tankstand = tankstand;
		this.schaeden = schaeden;
		
	}


	public String getKennzeichen() {
		return kennzeichen;
	}

	public void setKennzeichen(String kennzeichen) {
		this.kennzeichen = kennzeichen;
	}

	public int getMietId() {
		return mietId;
	}

	public int setMietId(int mietID) {
		return this.mietId = mietID;
	}


	public int getAutoId() {
		return autoId;
	}

	public void setAutoId(int autoId) {
		this.autoId = autoId;
	}

	public int getKundenId() {
		return kundenId;
	}

	public void setKundenId(int kundenId) {
		this.kundenId = kundenId;
	}

	public int getKilometer() {
		return kilometer;
	}

	public void setKilometer(int kilometer) {
		this.kilometer = kilometer;
	}

	public java.util.Date getUebergabeDatum() {
		return uebergabeDatum;
	}

	public void setUebergabeDatum(Date uebergabeDatum) {
		this.uebergabeDatum = uebergabeDatum;
	}

	public java.util.Date getRuecknahmeDatum() {
		return ruecknahmeDatum;
	}

	public void setRuecknahmeDatum(Date ruecknahmeDatum) {
		this.ruecknahmeDatum = ruecknahmeDatum;
	}

	public double getGesamtPreis() {
		return gesamtPreis;
	}

	public void setGesamtPreis(double gesamtPreis) {
		this.gesamtPreis = gesamtPreis;
	}
	public boolean getTankstand() {
		return tankstand;
	}

	public void setTankstand(boolean tankstand) {
		this.tankstand = tankstand;
	}

	public boolean getSchaeden() {
		return schaeden;
	}

	public void setSchaeden(boolean schaeden) {
		this.schaeden = schaeden;
	}

	@Override
	public String toString() {
		return "Booking [autoId=" + autoId + ", mietId=" + mietId + ", kundenId=" + kundenId + ", name=" + name
				+ ", kilometer=" + kilometer + ", uebergabeDatum=" + uebergabeDatum + ", ruecknahmeDatum="
				+ ruecknahmeDatum + ", tankstand=" + tankstand + ", schaeden=" + schaeden + ", sqlDateU=" + sqlDateU
				+ ", sqlDateR=" + sqlDateR + ", kennzeichen=" + kennzeichen + "]";
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public double getPreisProTag() {
		return preisProTag;
	}
	public void setPreisProTag(double preisProTag) {
		this.preisProTag = preisProTag;
	}


}
