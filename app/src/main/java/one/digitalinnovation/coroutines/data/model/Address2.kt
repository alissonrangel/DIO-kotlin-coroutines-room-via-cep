package one.digitalinnovation.coroutines.data.model

import androidx.room.*

@Entity(tableName = "via_cep_address")
data class Address2 (

    @PrimaryKey(autoGenerate = true) var uid: Int,

    val cep: String?,
    val street: String?,
    val neighborhood: String?,
    val city: String?,
    val state: String?
) {
    override fun toString(): String {
        return """
            cep=$cep
            street=$street
            neighborhood=$neighborhood
            city=$city
            state=$state
            """.trimIndent()
    }
}