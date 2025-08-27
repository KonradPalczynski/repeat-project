package controllers

import models.Athlete
import controllers.BrandAPI


class AthleteAPI(private val brandAPI: BrandAPI) {
    private val athletes = mutableListOf<Athlete>()

    fun addAthlete(athlete: Athlete){

        athletes.add(athlete)
    }

    fun getAllAthletes(): List<Athlete> = athletes

    fun getAthletesByBrand(brandId: Int): List<Athlete> =
        athletes.filter { it.brandId == brandId }

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

    fun deleteAthleteById(id: Int): Athlete? {
        val athlete = athletes.find { it.id == id }
        return if (athlete != null && athletes.remove(athlete)) athlete else null
    }

    fun unassignAthletesFromBrand(brandId: Int) {
        athletes.replaceAll { athlete ->
            if (athlete.brandId == brandId) athlete.copy(brandId = null) else athlete
        }
    }
}