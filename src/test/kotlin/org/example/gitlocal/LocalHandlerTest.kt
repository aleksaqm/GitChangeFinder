package org.example.gitlocal

import io.mockk.every
import io.mockk.mockk
import org.example.utils.ProcessRunner
import org.example.exceptions.GitCommandException
import org.example.gitlocal.LocalHandler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class LocalHandlerTest {

    private val mockProcessRunner = mockk<ProcessRunner>()
    private val localHandler = LocalHandler(mockProcessRunner)

    @Test
    fun `test findMergeBaseCommit success`() {
        val branchA = "main"
        val branchB = "feature"
        val localRepoPath = "/path/to/repo"
        val expectedMergeBase = "abc123"

        every { mockProcessRunner.runCommand(
            listOf("git", "merge-base", "origin/$branchA", branchB),
            File(localRepoPath)
        ) } returns expectedMergeBase

        val result = localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath)
        assertEquals(expectedMergeBase, result)
    }

    @Test
    fun `test findMergeBaseCommit failure`() {
        val branchA = "main"
        val branchB = "feature"
        val localRepoPath = "/path/to/repo"

        every { mockProcessRunner.runCommand(
            listOf("git", "merge-base", "origin/$branchA", branchB),
            File(localRepoPath)
        ) } throws RuntimeException("Error")

        assertThrows(GitCommandException::class.java) {
            localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath)
        }
    }

    @Test
    fun `test getChangedFilesLocalBranch success`() {
        val mergeBase = "abc123"
        val branch = "main"
        val localRepoPath = "/path/to/repo"
        val gitOutput = "file1.txt\nfile2.txt\n"
        val expectedFiles = listOf("file1.txt", "file2.txt")

        every { mockProcessRunner.runCommand(
            listOf("git", "diff", "--name-only", mergeBase, branch),
            File(localRepoPath)
        ) } returns gitOutput

        val result = localHandler.getChangedFilesLocalBranch(mergeBase, branch, localRepoPath)
        assertEquals(expectedFiles, result)
    }

    @Test
    fun `test getChangedFilesLocalBranch failure`() {
        val mergeBase = "abc123"
        val branch = "main"
        val localRepoPath = "/path/to/repo"

        every { mockProcessRunner.runCommand(
            listOf("git", "diff", "--name-only", mergeBase, branch),
            File(localRepoPath)
        ) } throws RuntimeException("Error")

        assertThrows(GitCommandException::class.java) {
            localHandler.getChangedFilesLocalBranch(mergeBase, branch, localRepoPath)
        }
    }

    @Test
    fun `test getChangedFilesLocalBranch empty output`() {
        val mergeBase = "abc123"
        val branch = "main"
        val localRepoPath = "/path/to/repo"
        val gitOutput = ""

        every { mockProcessRunner.runCommand(
            listOf("git", "diff", "--name-only", mergeBase, branch),
            File(localRepoPath)
        ) } returns gitOutput

        val result = localHandler.getChangedFilesLocalBranch(mergeBase, branch, localRepoPath)
        assertTrue(result.isEmpty())
    }
}