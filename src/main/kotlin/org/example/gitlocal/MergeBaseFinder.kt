package org.example.org.example.gitlocal

import org.example.org.example.exceptions.GitCommandException
import org.example.org.example.utils.ProcessRunner
import java.io.File

fun findMergeBaseCommit(branchA: String, branchB: String, localRepoPath: String, processRunner: ProcessRunner = ProcessRunner()): String {
    return try {
        processRunner.runCommand(
            listOf("git", "merge-base", "origin/$branchA", branchB),
            File(localRepoPath)
        )
    } catch (e: Exception) {
        throw GitCommandException("Failed to find merge base between '$branchA' and '$branchB': ${e.message}")
    }
}