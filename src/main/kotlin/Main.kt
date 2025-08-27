import controllers.BrandAPI
import controllers.AthleteAPI
import models.Brand
import models.Athlete

// Main function for handling operations
fun main() {

    val brandAPI = BrandAPI()
    val athleteAPI = AthleteAPI(brandAPI)

    while (true) {
        println(
            """
            ----------------------------
            Please choose an option:
            1. Add a brand
            2. Add an athlete
            3. Assign athlete to brand
            4. List all brands
            5. List all athletes
            6. List athletes by brand
            0. Exit
            ----------------------------
        """.trimIndent()
        )

        print("Enter your choice: ")
        when (readLine()?.toIntOrNull()) {
            1 -> {
                print("Enter brand ID: ")
                val id = readLine()?.toIntOrNull() ?: continue
                print("Enter brand name: ")
                val name = readLine().orEmpty()
                brandAPI.addBrand(Brand(id, name))
                println("Brand added.")
            }

            2 -> {
                print("Enter athlete ID: ")
                val id = readLine()?.toIntOrNull() ?: continue
                print("Enter athlete name: ")
                val name = readLine().orEmpty()

                    athleteAPI.addAthlete(Athlete(id, name))
                    println("Athlete added.")
                }

            3 -> {
                print("Enter athlete ID: ")
                val athleteId = readLine()?.toIntOrNull() ?: continue
                print("Enter new brand ID: ")
                val brandId = readLine()?.toIntOrNull() ?: continue
                val result = athleteAPI.addAthleteToBrand(athleteId, brandId)
                println(result)
            }
            }
        }
    }
    

