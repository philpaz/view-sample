package com.rest.json.bean;

import java.io.Serializable;
import java.util.logging.Logger;

/**
	@Source auto generated by PAZ generatorzz
**/

public class ManufacturerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4429181087590494084L;
	/**
	 * 
	 */
	private Integer	id = null;
	private String	name = null;
	private Integer active = null;
	private String nonce = null;
	
	private static final Logger log = Logger.getLogger(ManufacturerBean.class.getName());

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setActive(Integer active){
		this.active = active;
	}

	public Integer getActive(){
		return active;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}


}