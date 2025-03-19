package org.example.gitlocal

import org.example.exceptions.GitCommandException
import org.example.utils.ProcessRunner
import java.io.File

class LocalHandler(private val processRunner: ProcessRunner = ProcessRunner()) {

    internal fun findMergeBaseCommit(branchA: String, branchB: String, localRepoPath: String): String {
        return try {
            processRunner.runCommand(
                listOf("git", "merge-base", "origin/$branchA", branchB),
                File(localRepoPath)
            )
        } catch (e: Exception) {
            throw GitCommandException("Failed to find merge base between '$branchA' and '$branchB': ${e.message}")
        }
    }

    internal fun getChangedFilesLocalBranch(mergeBaseCommit: String, branch: String, localRepoPath: String): List<String> {
        return try {
            val result = processRunner.runCommand(
                listOf("git", "diff", "--name-only", mergeBaseCommit, branch),
                File(localRepoPath)
            )
            result.lines().filter { it.isNotBlank() }
        } catch (e: Exception) {
            throw GitCommandException("Failed to get changed files for branch '$branch': ${e.message}")
        }
    }
}