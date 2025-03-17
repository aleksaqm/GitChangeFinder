package org.example

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.example.org.example.githubapi.RemoteHandler
import org.example.org.example.gitlocal.LocalHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GitChangeFinderTest {

    private val localHandler = mockk<LocalHandler>()
    private val remoteHandler = mockk<RemoteHandler>()
    private val gitChangeFinder = GitChangeFinder(localHandler, remoteHandler)

    @Test
    fun `test findChangedFiles success`() {
        val owner = "owner"
        val repo = "repo"
        val accessToken = "test_token"
        val localRepoPath = "/path/to/repo"
        val branchA = "main"
        val branchB = "feature"

        val mergeBase = "abc123"
        val localFiles = listOf("file1.txt", "file2.txt", "common_file.txt")
        val remoteFiles = listOf("common_file.txt", "file3.txt")

        every { localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath) } returns mergeBase
        every { localHandler.getChangedFilesLocalBranch(mergeBase, branchB, localRepoPath) } returns localFiles
        every { remoteHandler.getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken) } returns remoteFiles

        val result = gitChangeFinder.findChangedFiles(owner, repo, accessToken, localRepoPath, branchA, branchB)

        assertEquals(listOf("common_file.txt"), result)

        verify {
            localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath)
            localHandler.getChangedFilesLocalBranch(mergeBase, branchB, localRepoPath)
            remoteHandler.getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken)
        }
    }

    @Test
    fun `test findChangedFiles with no common files`() {
        val owner = "owner"
        val repo = "repo"
        val accessToken = "test_token"
        val localRepoPath = "/path/to/repo"
        val branchA = "main"
        val branchB = "feature"

        val mergeBase = "abc123"
        val localFiles = listOf("file1.txt", "file2.txt")
        val remoteFiles = listOf("file3.txt", "file4.txt")

        every { localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath) } returns mergeBase
        every { localHandler.getChangedFilesLocalBranch(mergeBase, branchB, localRepoPath) } returns localFiles
        every { remoteHandler.getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken) } returns remoteFiles

        val result = gitChangeFinder.findChangedFiles(owner, repo, accessToken, localRepoPath, branchA, branchB)

        assertEquals(emptyList<String>(), result)

        verify {
            localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath)
            localHandler.getChangedFilesLocalBranch(mergeBase, branchB, localRepoPath)
            remoteHandler.getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken)
        }
    }
}