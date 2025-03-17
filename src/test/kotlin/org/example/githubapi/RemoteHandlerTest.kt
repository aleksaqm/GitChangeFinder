package org.example.githubapi

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.org.example.exceptions.GitHubApiException
import org.example.org.example.githubapi.RemoteHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.net.HttpURLConnection
import java.net.URL

class RemoteHandlerTest {

    private val remoteHandler = RemoteHandler()

    @Test
    fun `test getChangedFilesRemoteBranch success`() {
        val owner = "owner"
        val repo = "repo"
        val branch = "main"
        val mergeBase = "abc123"
        val accessToken = "test_token"

        val mockConnection = mockk<HttpURLConnection>(relaxed = true)
        val mockUrl = mockk<URL>()

        every { mockUrl.openConnection() } returns mockConnection
        every { mockConnection.inputStream } returns ByteArrayInputStream(
            """
            {
              "files": [
                { "filename": "file1.txt" },
                { "filename": "file2.txt" }
              ]
            }
            """.trimIndent().toByteArray()
        )
        every { mockConnection.responseCode } returns 200

        val result = remoteHandler.getChangedFilesRemoteBranch(
            owner, repo, branch, mergeBase, accessToken, urlProvider = { mockUrl }
        )

        assertEquals(listOf("file1.txt", "file2.txt"), result)

        verify { mockConnection.disconnect() }
    }

    @Test
    fun `test getChangedFilesRemoteBranch failure`() {
        val owner = "owner"
        val repo = "repo"
        val branch = "main"
        val mergeBase = "abc123"
        val accessToken = "test_token"

        val mockConnection = mockk<HttpURLConnection>(relaxed = true)
        val mockUrl = mockk<URL>()

        every { mockUrl.openConnection() } returns mockConnection
        every { mockConnection.responseCode } returns 404
        every { mockConnection.errorStream } returns ByteArrayInputStream(
            """{"message":"Not Found"}""".toByteArray()
        )

        val exception = assertThrows(GitHubApiException::class.java) {
            remoteHandler.getChangedFilesRemoteBranch(
                owner, repo, branch, mergeBase, accessToken, urlProvider = { mockUrl }
            )
        }

        assertTrue(exception.message!!.contains("Failed to get changes in remote branch 'main'"))

        verify { mockConnection.disconnect() }
    }
}