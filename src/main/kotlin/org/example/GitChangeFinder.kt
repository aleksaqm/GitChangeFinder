package org.example

import org.example.org.example.githubapi.getChangedFilesRemoteBranch
import org.example.org.example.gitlocal.findMergeBaseCommit
import org.example.org.example.gitlocal.getChangedFilesLocalBranch
import org.example.org.example.utils.ProcessRunner
import org.example.org.example.utils.getCommonItems

fun findChangedFiles(owner: String, repo: String, accessToken: String, localRepoPath: String, branchA: String, branchB: String): List<String> {
    val processRunner = ProcessRunner()
    val mergeBase = findMergeBaseCommit(branchA, branchB, localRepoPath, processRunner)
    val filesListRemote = getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken)
    val filesListLocal = getChangedFilesLocalBranch(mergeBase, branchA, localRepoPath, processRunner)
    val finalList = getCommonItems(filesListLocal, filesListRemote)
    return finalList
}