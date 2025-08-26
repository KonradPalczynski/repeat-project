package controllers

import models.Athlete
import controllers.BrandAPI


class AthleteAPI(private val brandAPI: BrandAPI) {
    private val athletes = mutableListOf<Athlete>()

    fun addAthlete(athlete: Athlete){
        // add code to validate that AthleteId and deptId exist
        // add code for adding a athlete with a unique id
        athletes.add(athlete)
    }

    fun getAllAthletes(): List<Athlete> = athletes

    fun getAthletesByBrand(brandId: Int): List<Athlete> =
        athletes.filter { it.brandId == brandId }

    fun addAthleteToBrand(athleteId: Int, brandId: Int) : String {
        val athlete = athletes.find { it.id == athleteId }
        if (athlete == null) {
            return "models.Athlete with ID \${athleteId} does not exist"
        } else if (!brandAPI.brandExists(brandId)) {
            return "models.Brand with ID \${brandId} does not exist."
        } else {
            athletes[athletes.indexOf(athlete)] = athlete.copy(brandId = brandId)
            return "models.Athlete \${athlete.name} moved to brand ID \${brandId}."
        }
    }
}