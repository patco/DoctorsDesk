package com.patco.doctorsdesk.server.util;

import com.patco.doctorsdesk.server.util.exceptions.InvalidAddressTypeException;

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
}
