![Image](https://github.com/user-attachments/assets/7d9182d9-c269-4b2b-be3e-212211554492)
## Team 8:
Ørjan Hammer Sylta (orjanhsy)\
Julie Alme  (julikal)\
Muna Isman (Munaai)\
Agnes (agneshb)\
Evelyn Bizimana (evelynsb)\
Tobias (tobiawi)


## Documentation
The application is documented through comments in the code. The Markdown file ARCHITECTURE.md provides an overview of the architecture through an architecture drawing, a textual description, and an overview of the API levels, tools and the APIs we have used.


## Information about libraries
View more in ARCHITECTURE.md

* Proto DataStore
* KTOR
* LocalDateTime
* MapBox
* Jetpack Compose
* Material3

## Explanation of warnings
* We have one warning in the IDE in SurfAreaScreen and DailyScreen. The Material3.TopAppBar library has been deprecated. We didn't see a quick way to manage it, so we chose to keep using it.
* In the model folder, many classes have warnings on attribute names, as we didn't use @SerializedName.
* We get some warnings in Gradle indicating that some of the libraries are not fully updated.
