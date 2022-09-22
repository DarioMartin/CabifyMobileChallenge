Cabify Store
=====  

Cabify Store is an Android application that recovers a list products, shows them and let the user
select how many of them they wanto to buy and the discounts they want to apply. It is built in
Kotlin and using Jetpack Compose.

Author
------  
Darío Martín-Lara Ortega - @dmartinlara on GitLab email: dariomartinlara@gmail.com

Build
-----  
You can build the app using Android Studio and compiling using the debug BuildVariant.

About the exercise
------

**The architectural approach you took and why**
For this exercise I have decided to follow the architecture proposed by Google for the development
of Android applications since it is the one I have been using lately and allows the application to
scale easily and be open to possible modifications.

This way you can add as many products or discounts as needed and app can scale without a lot of new
development and all the previous features will work as before.

In this architecture we can find 3 layers:

- **The presentation layer**, where all the components needed to paint the screens are located. As a
  design pattern I have followed **MVVM**, as it facilitates the development and is fully integrated
  into the Android framework. All the views are developed using JetpackCompose.

- **The domain layer**, which houses the data models with which the application will work and the
  different use cases that bring the different functionalities to our app. Even though most of the
  use cases are just a bridge I've decided to include them for consistency. It is easier to follow
  similar flows for all te interactions.

- **The data layer**, where all the components needed to retrieve, store or delete the information
  that the app needs are located. For this exercise there are 2 different data sources, one local
  and one remote. Depending on the use case the app get the data from the one that better suits the
  needs.

**Testing**
I have added unit tests for model components, specially to check that all the discounts work as
expected. I have also tested the view models, use cases and the data base.

**3rd party libraries or copied code you may have used**
I have used:

- **Retrofit** and **OkHttp** for network connections
- **Jetpack Compose** for views implementation
- **Androidx Navigation** for navigating between views
- **Hilt** for dependency injection
- **Room** for database storage
- **JUnit** for tests

**Other information about the exercise**
I have also added support for DarkTheme using JetpackCompose.