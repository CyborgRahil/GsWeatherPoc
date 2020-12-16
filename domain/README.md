Domain Module
===============

 * The domain layer responsibility is to simply contain the UseCase instance used to retrieve data
   from the Data layer and pass it onto the Presentation layer.

 * The layer defines the Project class but no mapper. This is because the Domain layer is our central layer,
   it knows nothing of the layers outside of it so has no need to map data to any other type of model.

 * The Domain layer defines the WeatherForecastRepository interface which provides a set of methods for an external
   layer to implement as the UseCase classes use the interface when requesting data.

