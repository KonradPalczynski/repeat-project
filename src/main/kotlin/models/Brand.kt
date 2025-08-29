package models

/**
 * Represents a Brand with a unique ID and a name.
 */
data class Brand(
    /** Unique identifier for the brand */
    val id: Int,

    /** Name of the brand */
    val name: String
)