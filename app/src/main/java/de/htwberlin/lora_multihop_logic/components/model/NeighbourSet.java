package de.htwberlin.lora_multihop_logic.components.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;
import de.htwberlin.lora_multihop_logic.enums.ELoraNodeState;

import java.sql.Timestamp;

@Entity
public class NeighbourSet {

	// unique
	// [ 0 - 9999 ]
	@PrimaryKey
	@ColumnInfo(name = "uid")
	private int uid;

	// 4 digit hex [ 0 - FFFE ]	(no FFFF -> broadcast)
	@ColumnInfo(name = "address")
	private String address;

	// directly attached hop
	// 4 digit hex [ 0 - FFFE ]	(no FFFF -> broadcast)
	@ColumnInfo(name = "dah")
	private String dah;

	// longitude
	// range [ 0 - 179,999 ]
	@ColumnInfo(name = "longitude")
	private double longitude;

	// latitude
	// range [ 0 - 179,999 ]
	@ColumnInfo(name = "latitude")
	private double latitude;

	// possible: up, down, pending & unknown
	@ColumnInfo(name = "state")
	private String state;

	// unix format
	@ColumnInfo(name = "timestamp")
	private long timestamp;

	public NeighbourSet()	{}

    public NeighbourSet(int uid, String address, String dah, Location location, ELoraNodeState state, Long timestamp) {
		if(uid < 0 || uid > 9999)
			throw new IllegalArgumentException("invalid UID for neighbour set");
		if (Integer.parseInt(address, 16) < 0 || Integer.parseInt(address, 16) > 65535)
			throw new IllegalArgumentException("invalid address for neighbour set");
		if (Integer.parseInt(dah, 16) < 0 || Integer.parseInt(dah, 16) > 65535)
			throw new IllegalArgumentException("invalid dah for neighbour set");

		this.uid = uid;
		this.address = address;
		this.dah = dah;
		this.longitude = location.getLongitude();
		this.latitude = location.getLatitude();
		this.state = state.name();
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

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Location getLocation()	{
		Location gps = new Location("");
		gps.setLongitude(this.longitude);
		gps.setLatitude(this.latitude);
		return gps;
	}

	public ELoraNodeState getEnumLoraNodeState()	{
		return ELoraNodeState.valueOf(this.state);
	}

	public Timestamp getTimestampFormat()	{
		return new Timestamp(this.timestamp);
	}

	@Override
	public String toString() {
		return "NeighbourSet{" +
				"uid=" + uid +
				", address='" + address + '\'' +
				", dah='" + dah + '\'' +
				", longitude='" + longitude + '\'' +
				", latitude='" + latitude + '\'' +
				", state=" + state +
				", timestamp=" + timestamp +
				'}';
	}
}
