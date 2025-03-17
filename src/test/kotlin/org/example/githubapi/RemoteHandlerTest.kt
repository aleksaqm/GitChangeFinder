package org.example.githubapi

import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.example.org.example.exceptions.GitHubApiException
import org.example.org.example.githubapi.getChangedFilesRemoteBranch
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlinx.serialization.SerializationException

class GitHubApiTest {
    private lateinit var server: MockWebServer

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `should return list of changed file names`() {
        val mockJsonResponse = """
        {
            "files": [
                { "filename": "src/main/kotlin/Main.kt" },
                { "filename": "README.md" }
            ]
        }
    """.trimIndent()
        server.enqueue(MockResponse().setBody(mockJsonResponse).setResponseCode(200))
        val mockServerUrl = server.url("/repos/test-owner/test-repo/compare/commit123...main").toString()
        val result = getChangedFilesRemoteBranch(
            "test-owner",
            "test-repo",
            "main",
            "commit123",
            "dummy_token",
            baseUrl = mockServerUrl
        )
        assertEquals(listOf("src/main/kotlin/Main.kt", "README.md"), result)
    }

    @Test
    fun `should throw GitHubApiException for 401 Unauthorized response`() {
        val mockJsonResponse = """
        {
            "message": "Bad credentials",
            "documentation_url": "https://docs.github.com/rest"
        }
    """.trimIndent()

        server.enqueue(MockResponse().setBody(mockJsonResponse).setResponseCode(401))
        val mockServerUrl = server.url("/repos/test-owner/test-repo/compare/commit123...main").toString()

        val exception = assertThrows<GitHubApiException> {
            getChangedFilesRemoteBranch(
                "test-owner",
                "test-repo",
                "main",
                "commit123",
                "dummy_token",
                baseUrl = mockServerUrl // Pass the mock server URL here
            )
        }
        val normalizedMessage = exception.message?.replace(Regex("\\s+"), " ")?.trim()
        val expectedMessage = "Failed to get changes in remote branch 'main'. Response code: 401, Error: { \"message\": \"Bad credentials\", \"documentation_url\": \"https://docs.github.com/rest\" }"
        assertEquals(expectedMessage, normalizedMessage)
    }

    @Test
    fun `should throw GitHubApiException for invalid JSON response`() {
        val invalidJsonResponse = """
        { "files": [ "filename": "README.md" }  // Invalid JSON
    """.trimIndent()

        server.enqueue(MockResponse().setBody(invalidJsonResponse).setResponseCode(200))

        val mockServerUrl = server.url("/repos/test-owner/test-repo/compare/commit123...main").toString()

        val exception = assertThrows<SerializationException> {
            getChangedFilesRemoteBranch(
                "test-owner",
                "test-repo",
                "main",
                "commit123",
                "dummy_token",
                baseUrl = mockServerUrl
            )
        }
        assertEquals("Unexpected JSON token at offset 24: Expected end of the array or comma at path: \$\n" +
                "JSON input: { \"files\": [ \"filename\": \"README.md\" }  // Invalid JSON", exception.message)
    }
}