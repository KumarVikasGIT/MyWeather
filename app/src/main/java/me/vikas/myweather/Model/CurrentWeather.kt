package me.vikas.myweather.Model

data class CurrentWeather(
	val elevation: Any? = null,
	val generationtimeMs: Any? = null,
	val current: CurrentA? = null,
	val timezoneAbbreviation: String? = null,
	val currentUnits: CurrentUnits? = null,
	val timezone: String? = null,
	val latitude: Any? = null,
	val utcOffsetSeconds: Int? = null,
	val longitude: Any? = null
)

data class CurrentUnits(
	val windSpeed10m: String? = null,
	val temperature2m: String? = null,
	val rain: String? = null,
	val showers: String? = null,
	val snowfall: String? = null,
	val cloudCover: String? = null,
	val apparentTemperature: String? = null,
	val isDay: String? = null,
	val interval: String? = null,
	val time: String? = null,
	val windDirection10m: String? = null
)

data class CurrentA(
	val windSpeed10m: Any? = null,
	val temperature2m: Any? = null,
	val rain: Any? = null,
	val showers: Any? = null,
	val snowfall: Any? = null,
	val cloudCover: Int? = null,
	val apparentTemperature: Any? = null,
	val isDay: Int? = null,
	val interval: Int? = null,
	val time: String? = null,
	val windDirection10m: Int? = null
)

