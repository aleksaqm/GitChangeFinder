package org.example.gitlocal

import io.mockk.*
import org.example.org.example.gitlocal.findMergeBaseCommit
import org.example.org.example.utils.ProcessRunner
import org.example.org.example.exceptions.GitCommandException
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MergeBaseFinderTest {

    @Test
    fun `test findMergeBaseCommit successful`() {
        val processRunner = mockk<ProcessRunner>()
        val repoPath = "path/to/repo"
        val branchA = "main"
        val branchB = "feature"

        every { processRunner.runCommand(any(), File(repoPath)) } returns "abc123"

        val result = findMergeBaseCommit(branchA, branchB, repoPath, processRunner)

        assertEquals("abc123", result)
        verify {
            processRunner.runCommand(
                listOf("git", "merge-base", "origin/$branchA", branchB),
                File(repoPath)
            )
        }
    }

    @Test
    fun `test findMergeBaseCommit failure`() {
        val processRunner = mockk<ProcessRunner>()
        val repoPath = "path/to/repo"
        val branchA = "main"
        val branchB = "feature"

        every { processRunner.runCommand(any(), File(repoPath)) } throws RuntimeException("Command failed")

        val exception = assertFailsWith<GitCommandException> {
            findMergeBaseCommit(branchA, branchB, repoPath, processRunner)
        }

        assertEquals("Failed to find merge base between 'main' and 'feature': Command failed", exception.message)
    }
}