package rentaFastCarFX;

import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookingProperty {
	private SimpleIntegerProperty autoId;
	private SimpleIntegerProperty mietId;
    private SimpleIntegerProperty kundenId;
    private SimpleStringProperty name;
    private SimpleStringProperty kennzeichen;
    private SimpleObjectProperty<Date> uebergabeDatum;
    private SimpleObjectProperty<Date> ruecknahmeDatum;
    private SimpleDoubleProperty gesamtPreis;

	public BookingProperty(int autoId, int mietID, int kundenID, String name,
			String kennzeichen, Date uebergabeDatum,
			Date ruecknahmeDatum, double gesamtPreis) {
		super();
		this.autoId = new SimpleIntegerProperty(autoId);
		this.mietId = new SimpleIntegerProperty(mietID);
		this.kundenId = new SimpleIntegerProperty(kundenID);
		this.name = new SimpleStringProperty(name);
		this.kennzeichen = new SimpleStringProperty(kennzeichen);
		this.uebergabeDatum = new SimpleObjectProperty<Date>(uebergabeDatum);
		this.ruecknahmeDatum = new SimpleObjectProperty<Date>(ruecknahmeDatum);
		this.gesamtPreis = new SimpleDoubleProperty(gesamtPreis);
	}
	public BookingProperty() {
		// TODO Auto-generated constructor stub
	}
	public Integer getAutoId() {
		return autoId.get();
	}
	public void setAutoId(Integer autoId) {
		this.autoId.set(autoId);
	}
	public SimpleIntegerProperty autoIdProperty() {
		return autoId;
}
		public Integer getMietId() {
			return mietId.get();
		}
		public void setmietID(Integer mietID) {
			this.mietId.set(mietID);
		}
		public SimpleIntegerProperty mietIDProperty() {
			return mietId;
	}
		public Integer getKundenId() {
			return kundenId.get();
		}
		public void setKundenID(Integer kundenID) {
			this.kundenId.set(kundenID);
		}
		public SimpleIntegerProperty kundenIDProperty() {
			return kundenId;
	}
		public String getName() {
			return name.get();
		}
		public void setName(String name) {
			this.name.set(name);
		}
		public SimpleStringProperty nameProperty() {
			return name;
	}
		public String getKennzeichen() {
			return kennzeichen.get();
		}
		public void setKennzeichen(String kennzeichen) {
			this.kennzeichen.set(kennzeichen);
		}
		public SimpleStringProperty kennzeichenProperty() {
			return kennzeichen;
	}
		public Date getUebergabeDatum() {
			return (Date) uebergabeDatum.get();
		}
		public void setuebergabeDatum(Date uebergabeDatum) {
			this.uebergabeDatum.set(uebergabeDatum);
		}
		public SimpleObjectProperty uebergabeDatumProperty() {
			return uebergabeDatum;
	}
		public Date getRuecknahmeDatum() {
			return (Date) ruecknahmeDatum.get();
		}
		public void ruecknahmeDatum(Date ruecknahmeDatum) {
			this.ruecknahmeDatum.set(ruecknahmeDatum);
		}
		public SimpleObjectProperty ruecknahmeDatumProperty() {
			return ruecknahmeDatum;
			
	}
		public Double getgesamtPreis() {
			return gesamtPreis.get();
		}
		public void setgesamtPreis(Double gesamtPreis) {
			this.gesamtPreis.set(gesamtPreis);
		}
		public SimpleDoubleProperty gesamtPreisProperty() {
			return gesamtPreis;

			
	}
		@Override
		public String toString() {
			return "BookingProperty [autoId=" + autoId + ", mietId=" + mietId + ", kundenId=" + kundenId + ", name="
					+ name + ", kennzeichen=" + kennzeichen + ", uebergabeDatum=" + uebergabeDatum
					+ ", ruecknahmeDatum=" + ruecknahmeDatum + ", gesamtPreis=" + gesamtPreis + "]";
		}
		
		
		
}
