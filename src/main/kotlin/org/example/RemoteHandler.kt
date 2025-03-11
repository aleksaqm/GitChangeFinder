package org.example

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun getChangedFilesRemoteBranch(owner: String, repo: String, branch: String, mergeBaseCommit: String, accessToken: String): List<String> {
    val url = URL("https://api.github.com/repos/$owner/$repo/compare/$mergeBaseCommit...$branch")
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Authorization", "Bearer $accessToken")
    connection.setRequestProperty("Accept", "application/vnd.github.v3+json")

    val responseCode = connection.responseCode
    if (responseCode != 200){
        println("Failed to get changes in remote branch $branch")
        return emptyList()
    }

    val reader = BufferedReader(InputStreamReader(connection.inputStream))
    val response = reader.readText()
    reader.close()

    println(response)

    return emptyList()

}
