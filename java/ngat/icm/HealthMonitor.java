package ngat.icm;

import ngat.message.ISS_INST.GET_STATUS_DONE;

public class HealthMonitor {

	private double failLowTemperature;
	private double warnLowTemperature;
	private double failHighTemperature;
	private double warnHighTemperature;

	public HealthMonitor(double failLowTemperature, double warnLowTemperature, double warnHighTemperature,
			double failHighTemperature) {
		this.failLowTemperature = failLowTemperature;
		this.warnLowTemperature = warnLowTemperature;
		this.warnHighTemperature = warnHighTemperature;
		this.failHighTemperature = failHighTemperature;
	}

	public String getTemperatureStatus(double temperature) {

		if (temperature > failHighTemperature)
			return GET_STATUS_DONE.VALUE_STATUS_FAIL;
		else if (temperature > warnHighTemperature)
			return GET_STATUS_DONE.VALUE_STATUS_WARN;
		else if (temperature > warnLowTemperature)
			return GET_STATUS_DONE.VALUE_STATUS_OK;
		else if (temperature > failLowTemperature)
			return GET_STATUS_DONE.VALUE_STATUS_WARN;
		else
			return GET_STATUS_DONE.VALUE_STATUS_FAIL;

	}

}