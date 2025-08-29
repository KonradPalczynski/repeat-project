package controllers

import models.Brand

/**
 * API class to manage Brand entities, providing CRUD operations and brand queries.
 */
class BrandAPI {

    /**
     * Internal list to store brands.
     */
    private var brands = ArrayList<Brand>()

    /**
     * Adds a new brand to the collection.
     * @param brand The brand object to add.
     * @return True if added successfully, false otherwise.
     */
    fun addBrand(brand: Brand): Boolean {
        return brands.add(brand)
    }

    /**
     * Returns a formatted string listing all stored brands.
     * If no brands exist, returns a message indicating none are stored.
     * @return String representation of all brands or a message.
     */
    fun listAllBrands(): String {
        return if (brands.isEmpty()) {
            "No brands stored"
        } else {
            brands.joinToString("\n") { "ID: ${it.id}, Name: ${it.name}" }
        }
    }

    /**
     * Checks if a brand with the specified ID exists.
     * @param id The brand ID to check.
     * @return True if the brand exists, false otherwise.
     */
    fun brandExists(id: Int): Boolean {
        return brands.any { it.id == id }
    }

    /**
     * Finds and returns a brand by its ID.
     * @param id The brand ID to look up.
     * @return The Brand if found, null otherwise.
     */
    fun getBrandById(id: Int): Brand? {
        return brands.find { it.id == id }
    }

    /**
     * Deletes a brand by its ID.
     * @param id The ID of the brand to delete.
     * @return The deleted Brand if successful, null if not found.
     */
    fun deleteBrandById(id: Int): Brand? {
        val brand = brands.find { it.id == id }
        return if (brand != null && brands.remove(brand)) brand else null
    }

    /**
     * Edits the name of the brand with the given ID.
     * @param id The brand ID to edit.
     * @param newName The new name for the brand.
     * @return True if edit was successful, false if brand not found.
     */
    fun editBrandName(id: Int, newName: String): Boolean {
        val brand = brands.find { it.id == id }
        return if (brand != null) {
            val index = brands.indexOf(brand)
            brands[index] = brand.copy(name = newName)
            true
        } else{
            false
        }
    }

    /**
     * Checks if a brand ID exists in the collection.
     * @param id The brand ID to check.
     * @return True if ID exists, false otherwise.
     */
    fun brandIdExists(id: Int): Boolean {
        return brands.any { it.id == id }
    }

    /**
     * Returns a list of all stored brands.
     * @return List of Brand objects.
     */
    fun getAllBrands(): List<Brand> {
        return brands
    }
}
