import controllers.BrandAPI
import controllers.AthleteAPI
import models.Brand
import models.Athlete

// Main function for handling operations
fun main() {

    val brandAPI = BrandAPI()
    val athleteAPI = AthleteAPI(brandAPI)

    // Adding sample brands
    brandAPI.addBrand(Brand(1, "HR"))
    brandAPI.addBrand(Brand(2, "Engineering"))

    // Adding sample athletes
    athleteAPI.addAthlete(Athlete(1, "Alice", 1))
    athleteAPI.addAthlete(Athlete(2, "Bob", 2))

    // Adding athletes to a brand
    athleteAPI.addAthleteToBrand(1, 2)
    athleteAPI.addAthleteToBrand(2, 1)

    // Displaying all athletes
    println("All Athletes:")
    athleteAPI.getAllAthletes().forEach { println(it) }

    // Displaying all brands
    println("All Brands:")
    brandAPI.getAllBrands().forEach { println(it) }

    // Displaying athletes by brand
    println("\nAthletes in models.Brand 1:")
    athleteAPI.getAthletesByBrand(1).forEach { println(it) }

    // Displaying athletes by brand
    println("\nAthletes in models.Brand 2:")
    athleteAPI.getAthletesByBrand(2).forEach { println(it) }

}