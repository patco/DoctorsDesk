package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name = "doctor")
@NamedQueries({
@NamedQuery(name="Doctor.GetAll", query="SELECT d FROM Doctor d"),
@NamedQuery(name="Doctor.CountAll", query="SELECT count(d) FROM Doctor d"),
@NamedQuery(name=Doctor.GETBYUSERNAME, query="SELECT d FROM Doctor d WHERE d.username= :username")
})
public class Doctor extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -4725822810854224357L;

	public static final String GETBYUSERNAME="Doctor.GetByUserName";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer id;

	@NotNull
	@NotEmpty
	@Column(length = 80)
	private String name;

	@NotNull
	@NotEmpty
	@Column(length = 16)
	private String password;

	@NotNull
	@NotEmpty
	@Column(length = 80)
	private String surname;

	@NotNull
	@NotEmpty
	@Column(unique = true, length = 16)
	private String username;

	@OneToMany(cascade = CascadeType.ALL,orphanRemoval=true, mappedBy = "doctor", fetch = FetchType.LAZY)
	private List<Discount> discounts;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "doctor", fetch = FetchType.LAZY)
	private List<PricelistItem> priceables;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor", fetch = FetchType.EAGER)
	private List<Patient> patients;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor", fetch = FetchType.LAZY)
	private List<Prescription> prescriptions;

	@Override
	public String getUIFriendlyString() {
		return getName() + " " + getSurname() + " (" + getUsername() + ")";
	}

	public Integer getId() {
		return this.id;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getName() {
		return this.name;
	}

	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String name) {
		this.surname = name;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// PATIENTS
	public List<Patient> getPatientList() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		if (patients != null)
			patients.clear();
		for (Patient patient : patients) {
			addPatient(patient);
		}
	}

	public void addPatient(Patient p) {
		if (patients == null)
			patients = new ArrayList<Patient>();

		patients.add(p);
	}

	public void removePatient(Patient p) {
		if (patients.contains(p))
			patients.remove(p);
	}

	// PRICABLES
	public List<PricelistItem> getPriceList() {
		return priceables;
	}

	public void setPricelist(final List<PricelistItem> pc) {
		if (priceables != null)
			priceables.clear();
		for (PricelistItem item : priceables) {
			addPricelistItem(item);
		}
	}

	public void addPricelistItem(final PricelistItem item) {
		if (priceables == null)
			priceables = new ArrayList<PricelistItem>();

		priceables.add(item);
	}

	public void removePricelistItem(final PricelistItem item) {
		if (priceables.contains(item))
			priceables.remove(item);
	}

	// DISCOUNTS
	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> ds) {
		if (discounts != null)
			discounts.clear();
		for (Discount discount : discounts) {
			addDiscount(discount);
		}
	}

	public void addDiscount(Discount ds) {
		if (discounts == null)
			discounts = new ArrayList<Discount>();

		discounts.add(ds);
	}

	public void removeDiscount(Discount d) {
		if (discounts.contains(d))
			discounts.remove(d);
	}

	public static final String DOCTOR_NODE = "<dentist>";
	public static final String DOCTOR_ENDNODE = "</dentist>";
	public static final String DOCTOR_NAMENODE = "<name>";
	public static final String DOCTOR_NAMEENDNODE = "</name>";
	public static final String DOCTOR_SURNAMENODE = "<surname>";
	public static final String DOCTOR_SURNAMEENDNODE = "</surname>";
	public static final String DOCTOR_USERNAMENODE = "<username>";
	public static final String DOCTOR_USERNAMEENDNODE = "</username>";
	public static final String DOCTOR_PASSWORDNODE = "<password>";
	public static final String DOCTOR_PASSWORDENDNODE = "</password>";
	public static final String DOCTOR_IDNODE = "<id>";
	public static final String DOCTOR_IDENDNODE = "</id>";

	public String getBASICXML() {
		StringBuilder ans = new StringBuilder(DOCTOR_NODE + DOCTOR_ENDNODE);
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), DOCTOR_IDNODE + getId()
				+ DOCTOR_IDENDNODE);
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), DOCTOR_NAMENODE + name
				+ DOCTOR_NAMEENDNODE);
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), DOCTOR_SURNAMENODE + surname
				+ DOCTOR_SURNAMEENDNODE);
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), DOCTOR_USERNAMENODE
				+ username + DOCTOR_USERNAMEENDNODE);
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), DOCTOR_PASSWORDNODE
				+ password + DOCTOR_PASSWORDENDNODE);
		return ans.toString();
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder(getBASICXML());

		ans.insert(ans.indexOf(DOCTOR_ENDNODE), "<pricelist>");
		if (priceables != null)
			for (PricelistItem pbl : priceables)
				ans.insert(ans.indexOf(DOCTOR_ENDNODE), pbl.getXML());
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), "</pricelist>");

		ans.insert(ans.indexOf(DOCTOR_ENDNODE), "<discounts>");
		if (discounts != null)
			for (Discount ds : discounts)
				ans.insert(ans.indexOf(DOCTOR_ENDNODE), ds.getXML());
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), "</discounts>");

		ans.insert(ans.indexOf(DOCTOR_ENDNODE), "<patientlist>");
		if (patients != null)
			for (Patient patient : patients)
				ans.insert(ans.indexOf(DOCTOR_ENDNODE), patient.getXML());
		ans.insert(ans.indexOf(DOCTOR_ENDNODE), "</patientlist>");

		return ans.toString();
	}

}
