package com.patco.doctorsdesk.server.util;

import java.util.Calendar;

import com.patco.doctorsdesk.server.util.exceptions.InvalidAddressTypeException;
import com.patco.doctorsdesk.server.util.exceptions.InvalidContactInfoTypeException;
import com.patco.doctorsdesk.server.util.exceptions.InvalidMedEntryAlertException;
import com.patco.doctorsdesk.server.util.exceptions.InvalidMedIntakeRouteException;

public class DoctorsDeskUtils {

	// ADDRESS TYPE
	public static enum AddressType {
		HOME(0, "Home Address"), OFFICE(1, "Office Address"), BILLING(2,
				"Billing Address");

		private final String desc;
		private final int value;

		private AddressType(int type, String desc) {
			value = type;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDescription() {
			return desc;
		}
	}

	public static boolean isAddressTypeValid(int type)
			throws InvalidAddressTypeException {
		for (AddressType tp : DoctorsDeskUtils.AddressType.values())
			if (type == tp.getValue())
				return true;
		return false;
	}

	public static String findAddressTypeDescr(int type) {
		for (AddressType tp : AddressType.values()) {
			if (tp.getValue() == type)
				return tp.getDescription();
		}
		return "";
	}

	// MEDICAL HISTORY ENTRY ALERTS
	public static enum MedEntryAlertType {
		NOALERT(0, "Normal"), LOW(1, "Low"), MEDIUM(2, "Medium"), HIGH(3,
				"High");

		private final String desc;
		private final int value;

		private MedEntryAlertType(int type, String desc) {
			value = type;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDescription() {
			return desc;
		}
	}

	public static boolean isMedEntryAlertValid(int type)
			throws InvalidMedEntryAlertException {
		for (MedEntryAlertType tp : DoctorsDeskUtils.MedEntryAlertType.values())
			if (type == tp.getValue())
				return true;
		return false;
	}

	public static String findMedEntryAlertDescr(int type) {
		for (MedEntryAlertType tp : MedEntryAlertType.values()) {
			if (tp.getValue() == type)
				return tp.getDescription();
		}
		return "";
	}

	// MEDICINE INTAKE ROUTES
	public static enum MedIntakeRoute {
		ORAL(0, "Oral"), SUBLINGUAL(1, "Sublingual"), RECTAL(2, "Rectal"), TRANSDERMAL(
				3, "Transdermal"), TRANSMUCOSAL(4, "Transmucosal");

		private final String desc;
		private final int value;

		private MedIntakeRoute(int type, String desc) {
			value = type;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDescription() {
			return desc;
		}
	}

	public static boolean isMedIntakeRouteValid(int type)
			throws InvalidMedIntakeRouteException {
		for (MedIntakeRoute rt : DoctorsDeskUtils.MedIntakeRoute.values())
			if (type == rt.getValue())
				return true;
		return false;
	}

	public static String findMedIntakeRouteDescr(int type) {
		for (MedIntakeRoute rt : MedIntakeRoute.values()) {
			if (rt.getValue() == type)
				return rt.getDescription();
		}
		return "";
	}

	public static enum PrescrRowTimeunit {
		HOURS(Calendar.HOUR_OF_DAY, "hour(s)", "hour(s)"), DAYS(
				Calendar.DAY_OF_MONTH, "day(s)", "day(s)"), WEEK(
				Calendar.WEEK_OF_MONTH, "week(s)", "week(s)"), MONTH(
				Calendar.MONTH, "month(s)", "month(s)");

		private final String ddesc, fdesc;
		private final int value;

		private PrescrRowTimeunit(int type, String fdesc, String ddesc) {
			value = type;
			this.fdesc = fdesc;
			this.ddesc = ddesc;
		}

		public int getValue() {
			return value;
		}

		public String getFreqUnitDescription() {
			return fdesc;
		}

		public String getDurUnitDescription() {
			return ddesc;
		}
	}

	public static boolean isPrescrRowTimeunitValid(int type) {
		for (PrescrRowTimeunit tp : DoctorsDeskUtils.PrescrRowTimeunit.values())
			if (type == tp.getValue())
				return true;
		return false;
	}

	public static String findPrescrRowTimeunitDurDescr(int type) {
		for (PrescrRowTimeunit tp : PrescrRowTimeunit.values()) {
			if (tp.getValue() == type)
				return tp.getDurUnitDescription();
		}
		return "";
	}

	public static String findPrescrRowTimeunitFreqDescr(int type) {
		for (PrescrRowTimeunit tp : PrescrRowTimeunit.values()) {
			if (tp.getValue() == type)
				return tp.getFreqUnitDescription();
		}
		return "";
	}

	// CONTACT INFO TYPE
	public static enum ContactInfoType {
		EMAIL(0, "E-mail"), FAX(1, "Fax"), HOME(2, "Home Number "), OFFICE(3,
				"Office Number "), MOBILE(4, "Mobile Phone ");

		private final String desc;
		private final int value;

		private ContactInfoType(int type, String desc) {
			value = type;
			this.desc = desc;
		}

		public int getValue() {
			return value;
		}

		public String getDescription() {
			return desc;
		}
	}

	public static boolean isContactInfoTypeValid(int type)
			throws InvalidContactInfoTypeException {
		for (ContactInfoType tp : DoctorsDeskUtils.ContactInfoType.values())
			if (type == tp.getValue())
				return true;
		return false;
	}

	public static String findContactInfoTypeDescr(int type) {
		for (ContactInfoType tp : ContactInfoType.values()) {
			if (tp.getValue() == type)
				return tp.getDescription();
		}
		return "";
	}

}
