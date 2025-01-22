import com.sharcar.datasource.DatabaseConnection
import com.sharcar.entities.Enterprise
import com.sharcar.entities.Locations
import com.sharcar.exception.SharCarBadRequestException

class EnterpriseDatasource {
    private val database = DatabaseConnection


    fun save(enterprise: Enterprise): Boolean {
        try {
            database.executeQuery(
                """
            INSERT INTO Enterprise
            (id, name)
            VALUES (?, ?)
            """,
                listOf(
                    enterprise.id,
                    enterprise.name,
                )
            )
        } catch (e: Exception) {
            throw SharCarBadRequestException.create(701)
        }
        return true
    }

    fun findById(id: Int): Enterprise? {
        val result = database.executeQuery(
            """
            SELECT e.*, L.*
            FROM Enterprise e
                LEFT JOIN Locations L on e.id = L.enterprise
            WHERE e.id = ?
            """,
            listOf(id)
        )

        var enterpriseId = 0
        var name = ""
        val locations = mutableListOf<Locations>()

        while (result.next()) {
            enterpriseId = result.getInt("e.id")
            name = result.getString("e.name")

            result.getInt("L.id")
            if (!result.wasNull()) {
                locations.add(
                    Locations(
                        result.getInt("L.id"),
                        result.getString("city"),
                        result.getString("street"),
                        result.getString("L.name")
                    )
                )
            }
        }

        if (enterpriseId != 0) {
            return Enterprise(enterpriseId, name, locations)
        }

        return null
    }
}