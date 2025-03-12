package org.example.githubapi

import kotlinx.serialization.json.Json
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.example.org.example.githubapi.getChangedFilesRemoteBranch
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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

        // Mock the server response with a 200 status
        server.enqueue(MockResponse().setBody(mockJsonResponse).setResponseCode(200))

        // Now run your function
        val result = getChangedFilesRemoteBranch(
            "test-owner",
            "test-repo",
            "main",
            "commit123",
            "dummy_token"  // Using dummy token for mock test
        ).sorted()

        // Verify that the result matches the expected file names
        assertEquals(listOf("README.md", "src/main/kotlin/Main.kt"), result)
    }
}