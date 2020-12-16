Remote Module
===============

 * The Remote layer handles all communications with remote sources, in our case it makes a simple API call
   using a Retrofit interface. The WeatherForecastRemoteImpl class implements the WeatherForecastRemote interface from the
   Data layer and uses the WeatherForecastingService to retrieve data from the API.

 * The API returns us instances of a WeatherData and these are mapped to WeatherEntity instance from
   the Data layer using the WeatherResponseModelMapper class.