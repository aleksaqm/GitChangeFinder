package org.example.gitlocal

import io.mockk.*
import org.example.org.example.exceptions.GitCommandException
import org.example.org.example.gitlocal.findMergeBaseCommit
import org.example.org.example.gitlocal.getChangedFilesLocalBranch
import org.example.org.example.utils.ProcessRunner
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalHandlerTest{
    @Test
    fun `test getChangedFilesLocalBranch successful`() {
        val processRunner = mockk<ProcessRunner>()
        val repoPath = "path/to/repo"
        val mergeBaseCommit = "123abc"
        val branch = "feature"

        every { processRunner.runCommand(any(), File(repoPath)) } returns "file1.txt\nfile2.txt\nfile3.txt"

        val result = getChangedFilesLocalBranch(mergeBaseCommit, branch, repoPath, processRunner)

        assertEquals(listOf("file1.txt", "file2.txt", "file3.txt"), result)
        verify { processRunner.runCommand(listOf("git", "diff", "--name-only", mergeBaseCommit, branch), File(repoPath)) }
    }

    @Test
    fun `test getChangedFilesLocalBranch failure`() {
        val processRunner = mockk<ProcessRunner>()
        val repoPath = "path/to/repo"
        val mergeBaseCommit = "123abc"
        val branch = "feature"

        every { processRunner.runCommand(any(), File(repoPath)) } throws RuntimeException("Command failed")

        val exception = assertFailsWith<GitCommandException> {
            getChangedFilesLocalBranch(mergeBaseCommit, branch, repoPath, processRunner)
        }

        assertEquals("Failed to get changed files for branch '$branch': Command failed", exception.message)

    }
}