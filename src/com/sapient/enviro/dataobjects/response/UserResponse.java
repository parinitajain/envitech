package com.sapient.enviro.dataobjects.response;

import java.io.Serializable;

public class UserResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 204974601028656195L;

	public String _id;
	public String ReferenceId;
	public String APIKey;
	public String Name;
	public String Email;
	public String VModel;
	public String VNumber;
	public String VType;
	public String Address;
	public String City;
	public String VBrand;

	public String getVBrand() {
		return VBrand;
	}

	public void setVBrand(String vBrand) {
		VBrand = vBrand;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getReferenceId() {
		return ReferenceId;
	}

	public void setReferenceId(String referenceId) {
		ReferenceId = referenceId;
	}

	public String getAPIKey() {
		return APIKey;
	}

	public void setAPIKey(String aPIKey) {
		APIKey = aPIKey;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getVModel() {
		return VModel;
	}

	public void setVModel(String vModel) {
		VModel = vModel;
	}

	public String getVNumber() {
		return VNumber;
	}

	public void setVNumber(String vNumber) {
		VNumber = vNumber;
	}

	public String getVType() {
		return VType;
	}

	public void setVType(String vType) {
		VType = vType;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

}
