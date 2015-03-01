package com.patco.doctorsdesk.server.util;

import com.patco.doctorsdesk.server.util.exceptions.InvalidAddressTypeException;
import com.patco.doctorsdesk.server.util.exceptions.InvalidMedEntryAlertException;

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

}
