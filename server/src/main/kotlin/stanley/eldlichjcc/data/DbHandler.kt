package stanley.eldlichjcc.data

import stanley.eldlichjcc.model.LoginRequest
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DbHandler {
    private const val DB_URL = "jdbc:sqlite:database.db"

    fun init() {
        val conn = DriverManager.getConnection(DB_URL)
        try {
            // 检查表是否存在，不存在则创建
            val sql = """
                CREATE TABLE IF NOT EXISTS User (
                    username TEXT PRIMARY KEY,
                    password TEXT NOT NULL,
                    role TEXT NOT NULL,
                    seat TEXT NOT NULL
                )
            """.trimIndent()

            conn.createStatement().use { stmt ->
                stmt.executeUpdate(sql)
                println("Database initialized successfully")
            }
        } catch (e: SQLException) {
            System.err.println("Database initialization failed: ${e.message}")
        } finally {
            conn.close()
        }
    }

    fun loginCheck(request: LoginRequest): Pair<String, String>? {
        val sql = """
            SELECT password, role, seat
            FROM User 
            WHERE username = ?
        """.trimIndent()

        val conn = DriverManager.getConnection(DB_URL)
        conn.use { connection ->
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, request.username)
                stmt.executeQuery().use { rs ->
                    if (rs.next()) {
                        val storedPassword = rs.getString("password")
                        if (storedPassword == request.password) {
                            return Pair(rs.getString("role"),rs.getString("seat"))
                        }
                    }
                }
            }
        }
        return null
    }
}
