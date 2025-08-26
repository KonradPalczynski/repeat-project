package controllers

import models.Brand

class BrandAPI {
    private var brands = ArrayList<Brand>()

    fun add(brand: Brand): Boolean {
        return brands.add(brand)
    }

    fun listAllBrands(): String {
        return if (brands.isEmpty()) {
            "No brands stored"
        } else {
            var listOfBrands = ""
            for (i in brands.indices) {
                listOfBrands += "${i}: ${brands[i]} \n"
            }
            listOfBrands
        }
    }
}
