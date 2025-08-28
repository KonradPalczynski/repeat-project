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
            7. Delete a brand
            8. Delete an athlete
            9. Edit a brand
            10. Edit an athlete
            0. Exit
            ----------------------------
        """.trimIndent()
        )

        print("Enter your choice: ")
        when (readLine()?.toIntOrNull()) {

            1 -> {
                print("Enter brand ID: ")
                val id = readLine()?.toIntOrNull() ?: continue

                if (brandAPI.brandIdExists(id)) {
                    println("A brand with ID $id already exists.")
                    continue
                }

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
                print("Enter brand ID: ")
                val brandId = readLine()?.toIntOrNull() ?: continue
                val result = athleteAPI.addAthleteToBrand(athleteId, brandId)
                println(result)
            }

            4 -> {
                println("All Brands: ")
                println(brandAPI.listAllBrands())
            }

            5 -> {
                println("All Athletes:")
                if (athleteAPI.getAllAthletes().isEmpty()) {
                    println("No athletes stored.")
                }
                else {
                    athleteAPI.getAllAthletes().forEach { athlete ->
                        val brand = athlete.brandId?.let { brandAPI.getBrandById(it)?.name } ?: "Unassigned"
                        println("ID: ${athlete.id}, Name: ${athlete.name}, Brand: $brand")
                    }
                }
            }

            6 -> {
                print("Enter brand ID to filter: ")
                val brandId = readLine()?.toIntOrNull() ?: continue

                val brand = brandAPI.getBrandById(brandId)
                if (brand == null) {
                    println("Brand with ID $brandId does not exist.")
                    return
                }

                val athletes = athleteAPI.getAthletesByBrand(brandId)
                if (athletes.isEmpty()) {
                    println("No athletes found for brand '${brand.name}'.")
                }
                else {
                    println("Athletes in brand '${brand.name}':")
                    athletes.forEach { athlete ->
                        println("ID: ${athlete.id}, Name: ${athlete.name}")
                    }
                }
            }

            7 -> {
                print("Enter brand ID to delete: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val brand = brandAPI.getBrandById(id)
                if (brand == null) {
                    println("Brand with ID $id does not exist.")
                    continue
                }

                athleteAPI.unassignAthletesFromBrand(id)

                val deleted = brandAPI.deleteBrandById(id)
                if (deleted != null) {
                    println("Brand '${deleted.name}' was deleted.")
                }
                else {
                    println("Failed to delete brand.")
                }
            }

            8 -> {
                print("Enter athlete ID to delete: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val deleted = athleteAPI.deleteAthleteById(id)
                if (deleted != null) {
                    println("Athlete '${deleted.name}' was deleted.")
                }
                else {
                    println("Athlete with ID $id not found.")
                }
            }

            9 -> {
                print("Enter brand ID to edit: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val brand = brandAPI.getBrandById(id)
                if (brand == null) {
                    println("Brand with ID $id does not exist.")
                    continue
                }

                print("Enter new brand name: ")
                val newName = readLine().orEmpty()

                if (brandAPI.editBrandName(id, newName)) {
                    println("Brand '${brand.name}' renamed to '$newName'.")
                }
                else {
                    println("Failed to edit brand.")
                }
            }

            10 -> {
                print("Enter athlete ID to edit: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val athlete = athleteAPI.getAllAthletes().find { it.id == id }
                if (athlete == null) {
                    println("Athlete with ID $id does not exist.")
                    continue
                }

                print("Enter new athlete name: ")
                val newName = readLine().orEmpty()

                if (athleteAPI.editAthleteName(id, newName)) {
                    println("Athlete '${athlete.name}' renamed to '$newName'.")
                }
                else {
                    println("Failed to edit athlete.")
                }
            }

            0 -> {
                println("Exiting program.")
                return
            }

            else -> println("Invalid option. Try again.")
        }
    }
}

