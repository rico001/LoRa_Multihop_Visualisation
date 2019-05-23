package de.htwberlin.lora_multihop_implementation.components.model;

import android.location.Location;
import de.htwberlin.lora_multihop_implementation.enums.ELoraNodeState;

import java.sql.Timestamp;

public class NeighbourSet {

	// unique
	// [ 0 - 9999 ]
	private int uid;

	// 4 digit hex [ 0 - FFFE ]	(no FFFF -> broadcast)
	private String address;

	// directly attached hop
	// 4 digit hex [ 0 - FFFE ]	(no FFFF -> broadcast)
	private String dah;

	// longitude & latitude
	// range [ 0 - 179,999 ]
	private Location location;

	// possible: up, down, pending & unknown
	private ELoraNodeState state;

	// unix format
	private Timestamp timestamp;

	public NeighbourSet(int uid, String address, String dah, Location location, ELoraNodeState state, Timestamp timestamp)	{
		if(uid < 0 || uid > 9999)
			throw new IllegalArgumentException("invalid UID for neighbour set");
		if(Integer.parseInt(address, 16) < 0 || Integer.parseInt(address, 16) > 65534)
			throw new IllegalArgumentException("invalid address for neighbour set");
		if(Integer.parseInt(dah, 16) < 0 || Integer.parseInt(dah, 16) > 65534)
			throw new IllegalArgumentException("invalid dah for neighbour set");

		this.uid = uid;
		this.address = address;
		this.dah = dah;
		this.location = location;
		this.state = state;
		this.timestamp = timestamp;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDah() {
		return dah;
	}

	public void setDah(String dah) {
		this.dah = dah;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ELoraNodeState getState() {
		return state;
	}

	public void setState(ELoraNodeState state) {
		this.state = state;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "NeighbourSet{" +
				"uid=" + uid +
				", address='" + address + '\'' +
				", dah='" + dah + '\'' +
				", location=" + location +
				", state=" + state +
				", timestamp=" + timestamp +
				'}';
	}
}
