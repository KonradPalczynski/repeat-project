package controllers

import models.Athlete
import controllers.BrandAPI

/**
 * API class to manage Athlete entities, including assignments to brands.
 * Uses BrandAPI to verify brand existence.
 */
class AthleteAPI(private val brandAPI: BrandAPI) {

    /**
     * Internal list storing all athletes.
     */
    private val athletes = mutableListOf<Athlete>()

    /**
     * Adds a new athlete to the list.
     * @param athlete The athlete to add.
     */
    fun addAthlete(athlete: Athlete){
        athletes.add(athlete)
    }

    /**
     * Returns a list of all stored athletes.
     * @return List of Athlete objects.
     */
    fun getAllAthletes(): List<Athlete> = athletes

    /**
     * Retrieves athletes assigned to a specific brand.
     * @param brandId The brand ID to filter athletes by.
     * @return List of athletes belonging to the brand.
     */
    fun getAthletesByBrand(brandId: Int): List<Athlete> =
        athletes.filter { it.brandId == brandId }

    /**
     * Assigns an athlete to a brand by updating the athlete's brandId.
     * Returns a status message depending on success or failure.
     * @param athleteId The ID of the athlete to assign.
     * @param brandId The ID of the brand to assign to.
     * @return String message indicating the result of the assignment.
     */
    fun addAthleteToBrand(athleteId: Int, brandId: Int): String {
        val athlete = athletes.find { it.id == athleteId }
        val brand = brandAPI.getBrandById(brandId)

        return when {
            athlete == null -> "Athlete with ID $athleteId does not exist."
            brand == null -> "Brand with ID $brandId does not exist."
            else -> {
                val updated = athlete.copy(brandId = brandId)
                athletes[athletes.indexOf(athlete)] = updated
                "Athlete ${updated.name} assigned to ${brand.name}."
            }
        }
    }

    /**
     * Deletes an athlete by ID.
     * @param id The athlete's ID.
     * @return The deleted Athlete if successful, or null if not found.
     */
    fun deleteAthleteById(id: Int): Athlete? {
        val athlete = athletes.find { it.id == id }
        return if (athlete != null && athletes.remove(athlete)) athlete else null
    }

    /**
     * Unassigns all athletes currently assigned to the given brand.
     * Sets their brandId to null.
     * @param brandId The brand ID from which to unassign athletes.
     */
    fun unassignAthletesFromBrand(brandId: Int) {
        athletes.replaceAll { athlete ->
            if (athlete.brandId == brandId) athlete.copy(brandId = null) else athlete
        }
    }

    /**
     * Edits an athlete's name.
     * @param id The athlete's ID.
     * @param newName The new name to set.
     * @return True if the athlete was found and edited, false otherwise.
     */
    fun editAthleteName(id: Int, newName: String): Boolean {
        val athlete = athletes.find { it.id == id }
        return if (athlete != null) {
            val index = athletes.indexOf(athlete)
            athletes[index] = athlete.copy(name = newName)
            true
        } else {
            false
        }
    }

    /**
     * Checks if an athlete ID exists.
     * @param id The athlete ID to check.
     * @return True if an athlete with the ID exists, false otherwise.
     */
    fun athleteIdExists(id: Int): Boolean {
        return athletes.any { it.id == id }
    }
}
