package org.team1540.common.core.bluetooth;

import java.util.UUID;

public class Address {
	public final String address;
	public final UUID uuid;
	public final String serviceName;

	public Address(final String address) {
		this.address = address;
		uuid = null;
		serviceName = null;
	}

	public Address(final String address, final UUID uuid) {
		this.address = address;
		this.uuid = uuid;
		serviceName = null;
	}

	public Address(final String address, final UUID uuid, final String serviceName) {
		this.address = address;
		this.uuid = uuid;
		this.serviceName = serviceName;
	}

	public String getAddress() {
		return address;
	}

	public UUID getUUID() {
		return uuid;
	}

	public String getServiceName() {
		return serviceName;
	}
}
