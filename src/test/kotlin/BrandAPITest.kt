/*

package utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test








class addBrand {

        @Test
        fun `should add a new brand successfully`() {
            val brand = Brand(1, "Nike")
            val result = brandAPI.addBrand(brand)

            assertTrue(result, "Expected the brand to be added successfully")
            assertEquals(1, brandAPI.getAllBrands().size)
        }

        @Test
        fun `should not add a brand with a duplicate ID`() {
            val brand1 = Brand(1, "Nike")
            val brand2 = Brand(1, "Adidas") // Duplicate ID

            brandAPI.addBrand(brand1)
            val result = brandAPI.addBrand(brand2)

            assertFalse(result, "Expected the second brand to be rejected due to duplicate ID")
            assertEquals(1, brandAPI.getAllBrands().size)
        }
    }


*/