package org.example

import org.example.org.example.githubapi.getChangedFilesRemoteBranch
import org.example.org.example.gitlocal.findMergeBaseCommit
import org.example.org.example.gitlocal.getChangedFilesLocalBranch
import org.example.org.example.utils.getCommonItems

fun findChangedFiles(owner: String, repo: String, accessToken: String, localRepoPath: String, branchA: String, branchB: String): List<String> {
    val mergeBase = findMergeBaseCommit(branchA, branchB, localRepoPath)
    val filesListRemote = getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken)
    val filesListLocal = getChangedFilesLocalBranch(mergeBase, branchA, localRepoPath)
    val finalList = getCommonItems(filesListLocal, filesListRemote)
    return finalList
}