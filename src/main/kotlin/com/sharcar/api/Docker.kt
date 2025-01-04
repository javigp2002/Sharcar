import com.sharcar.datasource.DatabaseConnection
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.docker() {
    route("/docker") {
        get("/test_database") {
            val db = DatabaseConnection
            val result = db.executeQuery("SELECT 1 = 1", emptyList())
            val isConnected = result.next()

            if (isConnected) {
                call.respond("Database test success")
            } else {
                call.respond("Database test failed")
            }
        }
    }
}