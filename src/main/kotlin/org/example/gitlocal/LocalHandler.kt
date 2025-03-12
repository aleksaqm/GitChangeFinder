package org.example.org.example.gitlocal

import org.example.org.example.exceptions.GitCommandException
import org.example.org.example.utils.ProcessRunner
import java.io.File

fun getChangedFilesLocalBranch(mergeBaseCommit : String, branch: String, localRepoPath: String, processRunner: ProcessRunner = ProcessRunner()): List<String>{
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