package org.example.utils

import io.mockk.every
import io.mockk.mockk
import org.example.org.example.utils.ProcessRunner
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.File
import java.io.IOException

class ProcessRunnerTest {

    private val processRunner = ProcessRunner()
    private val mockDirectory = mockk<File>(relaxed = true)

    @Test
    fun `runCommand should execute command successfully`() {
        val result = processRunner.runCommand(listOf("java", "-version"), File("./"))
        assertTrue(result.contains("java"))
    }

    @Test
    fun `runCommand should throw exception for failed command`() {
        val tempDir = File(System.getProperty("java.io.tmpdir"))
        val exception = assertThrows<RuntimeException> {
            processRunner.runCommand(listOf("git", "invalid-command"), tempDir)
        }
        assertTrue(exception.message!!.contains("Command failed with exit code"))
    }

    @Test
    fun `runCommand should throw IOException for non-existent command`() {
        val exception = assertThrows<IOException> {
            processRunner.runCommand(listOf("invalidCommand"), mockDirectory)
        }
        assertTrue(exception.message!!.contains("Cannot run program"))
    }
}