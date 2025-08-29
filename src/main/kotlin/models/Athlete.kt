/**
 * Represents an Athlete with an ID, name, and optional associated brand.
 */
data class Athlete(
    /** Unique identifier for the athlete */
    val id: Int,

    /** Name of the athlete */
    val name: String,

    /** ID of the brand this athlete is assigned to; null if unassigned */
    val brandId: Int?
)