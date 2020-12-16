Data Module
===============

 * The Data layer is our access point to external data layers and is used to fetch data
   from multiple sources (the cache and network in our case).

 * It contains an implementation of the WeatherForecastRepository.
   To begin with, this class uses the WeatherForecastDataSourceFactory to decide which data store class will
   be used when fetching data - this will be either the remote data source or the
   cache data source - both of these classes implement the WeatherForecastDataSource repository so that
   our DataStore classes are enforced.

 * This layers data model is the WeatherEntity. Here the Mapper is used to map data to
   and from a Weather model instance from the domain layer and WeatherEntity instance from this layer as required.
