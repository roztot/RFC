package rentaFastCar;

import java.sql.Date;

public class Customer {
	private  int kundenId;
	private  String name;
	private  String adresse;
	private  java.util.Date geburtsDatum;
	private  String fuehrerscheinNummer;
	

	public Customer(int kundenID, String name, String adresse, java.util.Date geburtsDatum, String fuehrerscheinNummer) {
		super();
		this.kundenId = kundenID;
		this.name = name;
		this.adresse = adresse;
		this.geburtsDatum = geburtsDatum;
		this.fuehrerscheinNummer = fuehrerscheinNummer;
		
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}
	

	public  int getKundenId() {
		return kundenId;
	}

	public void setKundenId(int kundenID) {
		this.kundenId = kundenID;
	}

	public  String getName() {
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

	public java.util.Date getGeburtsDatum() {
		return geburtsDatum;
	}

	public void setGeburtsDatum(Date geburtsDatum) {
		this.geburtsDatum = geburtsDatum;
	}

	public String getFuehrerscheinNummer() {
		return fuehrerscheinNummer;
	}

	public void setFuehrerscheinNummer(String fuehrerscheinNummer) {
		this.fuehrerscheinNummer = fuehrerscheinNummer;
	}

	

	@Override
	public String toString() {
		return "Customer [kundenId=" + kundenId + ", name=" + name + ", adresse=" + adresse + ", geburtsDatum="
				+ geburtsDatum + ", fuehrerscheinNummer=" + fuehrerscheinNummer + "]";
	}
	

}
