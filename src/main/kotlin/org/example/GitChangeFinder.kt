package org.example

fun findChangedFiles(owner: String, repo: String, accessToken: String, localRepoPath: String, branchA: String, branchB: String): List<String> {
    val mergeBase = findMergeBaseCommit(branchA, branchB, localRepoPath)
    val filesListRemote = getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken)
    val filesListLocal = getChangedFilesLocalBranch(mergeBase, branchA, localRepoPath)
    val finalList = compareChanges(filesListLocal, filesListRemote)
    return finalList
}