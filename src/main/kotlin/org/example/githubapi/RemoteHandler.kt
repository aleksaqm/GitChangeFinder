package org.example.org.example.githubapi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import org.example.org.example.exceptions.GitHubApiException
import java.net.HttpURLConnection
import java.net.URL

@Serializable
data class ChangedFile(
    val filename: String,
)

class RemoteHandler(private val json: Json = Json { ignoreUnknownKeys = true }) {

    fun getChangedFilesRemoteBranch(
        owner: String,
        repo: String,
        branch: String,
        mergeBaseCommit: String,
        accessToken: String,
        baseUrl: String = "https://api.github.com"
    ): List<String> {
        val url = URL("$baseUrl/repos/$owner/$repo/compare/$mergeBaseCommit...$branch")

        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Authorization", "Bearer $accessToken")
            setRequestProperty("Accept", "application/vnd.github.v3+json")
            setRequestProperty("User-Agent", "Kotlin-Git-Client")
            connectTimeout = 10_000
            readTimeout = 10_000
        }

        return try {
            if (connection.responseCode != 200) {
                val errorMsg = connection.errorStream?.bufferedReader()?.use { it.readText() } ?: "Unknown error"
                throw GitHubApiException("Failed to get changes in remote branch '$branch'. Response code: ${connection.responseCode}, Error: $errorMsg")
            }

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            val jsonObject = json.parseToJsonElement(response).jsonObject

            val filesJsonArray = jsonObject["files"]?.jsonArray ?: return emptyList()
            filesJsonArray.map { json.decodeFromString<ChangedFile>(it.toString()).filename }
        } finally {
            connection.disconnect()
        }
    }
}
