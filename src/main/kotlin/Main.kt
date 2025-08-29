import controllers.BrandAPI
import controllers.AthleteAPI
import models.Brand
import models.Athlete

// Main function for handling operations
/**
 * Entry point for the Athlete Tracker app.
 * Sets up APIs and handles the main menu loop.
 */
fun main() {

    /**
     * Initialize BrandAPI instance to manage brands.
     */
    val brandAPI = BrandAPI()
    /**
     * Initialize AthleteAPI instance to manage athletes,
     * passing brandAPI to link athletes to brands.
     */
    val athleteAPI = AthleteAPI(brandAPI)

    /**
     * Infinite loop to display menu and handle user input.
     */
    while (true) {
        /**
         * Print the main menu options.
         */
        println(
            """
    ========================================
               ATHLETE TRACKER APP
    ========================================

    Please choose an option:

    1.  Add a brand
    2.  Add an athlete
    3.  Assign athlete to brand
    4.  List all brands
    5.  List all athletes
    6.  List athletes by brand
    7.  Delete a brand
    8.  Delete an athlete
    9.  Edit a brand
    10. Edit an athlete
    11. View brand summary (athlete count)

    0.  Exit

    ========================================
    """.trimIndent()
        )

        /**
         * Prompt user for choice and convert input to integer.
         */
        print("Enter your choice: ")
        when (readLine()?.toIntOrNull()) {

            /**
             * Add a brand:
             * Prompt for brand ID and name.
             * Validate ID uniqueness before adding.
             */
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

            /**
             * Add an athlete:
             * Prompt for athlete ID and name.
             * Validate ID uniqueness before adding.
             */
            2 -> {
                print("Enter athlete ID: ")
                val id = readLine()?.toIntOrNull() ?: continue

                if (athleteAPI.athleteIdExists(id)) {
                    println("An athlete with ID $id already exists.")
                    continue
                }

                print("Enter athlete name: ")
                val name = readLine().orEmpty()

                athleteAPI.addAthlete(Athlete(id, name, null))
                println("Athlete added.")
            }

            /**
             * Assign athlete to brand:
             * Prompt for athlete and brand IDs.
             * Perform the assignment and print result.
             */
            3 -> {
                print("Enter athlete ID: ")
                val athleteId = readLine()?.toIntOrNull() ?: continue
                print("Enter brand ID: ")
                val brandId = readLine()?.toIntOrNull() ?: continue
                val result = athleteAPI.addAthleteToBrand(athleteId, brandId)
                println(result)
            }

            /**
             * List all brands.
             */
            4 -> {
                println("All Brands: ")
                println(brandAPI.listAllBrands())
            }

            /**
             * List all athletes with their associated brand names.
             */
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

            /**
             * List athletes filtered by a given brand ID.
             */
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

            /**
             * Delete a brand by ID after confirmation.
             * Unassign athletes linked to the brand first.
             */
            7 -> {
                print("Enter brand ID to delete: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val brand = brandAPI.getBrandById(id)
                if (brand == null) {
                    println("Brand with ID $id does not exist.")
                    continue
                }

                print("Are you sure you want to delete brand '${brand.name}'? (y/n): ")
                val confirmation = readLine()?.trim()?.lowercase()
                if (confirmation != "y") {
                    println("Deletion cancelled.")
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

            /**
             * Delete an athlete by ID after confirmation.
             */
            8 -> {
                print("Enter athlete ID to delete: ")
                val id = readLine()?.toIntOrNull() ?: continue

                val athlete = athleteAPI.getAllAthletes().find { it.id == id }
                if (athlete == null) {
                    println("Athlete with ID $id does not exist.")
                    continue
                }

                print("Are you sure you want to delete athlete '${athlete.name}'? (y/n): ")
                val confirmation = readLine()?.trim()?.lowercase()
                if (confirmation != "y") {
                    println("Deletion cancelled.")
                    continue
                }

                val deleted = athleteAPI.deleteAthleteById(id)
                if (deleted != null) {
                    println("Athlete '${deleted.name}' was deleted.")
                } else {
                    println("Failed to delete athlete.")
                }
            }

            /**
             * Edit an existing brand's name.
             */
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

            /**
             * Edit an existing athlete's name.
             */
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

            /**
             * Show a summary of all brands with the count of athletes.
             */
            11 -> {
                val brands = brandAPI.getAllBrands()

                if (brands.isEmpty()) {
                    println("No brands available.")
                    continue
                }

                println("Brand Summary:")
                brands.forEach { brand ->
                    val count = athleteAPI.getAthletesByBrand(brand.id).size
                    val label = if (count == 1) "athlete" else "athletes"
                    println("${brand.name}: $count $label")
                }
            }

            /**
             * Exit the program cleanly.
             */
            0 -> {
                println("Exiting program.")
                return
            }

            /**
             * Handle invalid menu option input.
             */
            else -> println("Invalid option. Try again.")
        }
    }
}
