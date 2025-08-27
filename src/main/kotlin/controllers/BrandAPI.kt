package controllers

import models.Brand

class BrandAPI {
    private var brands = ArrayList<Brand>()

    fun addBrand(brand: Brand): Boolean {
        return brands.add(brand)
    }

    fun listAllBrands(): String {
        return if (brands.isEmpty()) {
            "No brands stored"
        } else {
            brands.joinToString("\n") { "ID: ${it.id}, Name: ${it.name}" }
        }
    }

    fun brandExists(id: Int): Boolean {
        return brands.any { it.id == id }
    }

    fun getBrandById(id: Int): Brand? {
        return brands.find { it.id == id }
    }

}
