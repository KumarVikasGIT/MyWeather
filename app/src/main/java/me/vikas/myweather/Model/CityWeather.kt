package me.vikas.myweather.Model

data class CityWeather(
	val current: Current? = null,
	val location: Location? = null
)

data class Location(
	val localtime: String? = null,
	val country: String? = null,
	val localtimeEpoch: Int? = null,
	val name: String? = null,
	val lon: Any? = null,
	val region: String? = null,
	val lat: Any? = null,
	val tzId: String? = null
)

data class Condition(
	val code: Int? = null,
	val icon: String? = null,
	val text: String? = null
)

data class Current(
	val feelslikeC: Any? = null,
	val uv: Any? = null,
	val lastUpdated: String? = null,
	val feelslikeF: Any? = null,
	val windDegree: Int? = null,
	val lastUpdatedEpoch: Int? = null,
	val isDay: Int? = null,
	val precipIn: Any? = null,
	val windDir: String? = null,
	val gustMph: Any? = null,
	val tempC: Any? = null,
	val pressureIn: Any? = null,
	val gustKph: Any? = null,
	val tempF: Any? = null,
	val precipMm: Any? = null,
	val cloud: Int? = null,
	val windKph: Any? = null,
	val condition: Condition? = null,
	val windMph: Any? = null,
	val visKm: Any? = null,
	val humidity: Int? = null,
	val pressureMb: Any? = null,
	val visMiles: Any? = null
)

