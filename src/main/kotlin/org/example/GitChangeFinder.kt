package org.example

import org.example.org.example.githubapi.RemoteHandler
import org.example.org.example.gitlocal.LocalHandler
import org.example.org.example.utils.getCommonItems

class GitChangeFinder(
    private val localHandler: LocalHandler = LocalHandler(),
    private val remoteHandler: RemoteHandler = RemoteHandler()
) {

    fun findChangedFiles(
        owner: String,
        repo: String,
        accessToken: String,
        localRepoPath: String,
        branchA: String,
        branchB: String
    ): List<String> {
        val mergeBase = localHandler.findMergeBaseCommit(branchA, branchB, localRepoPath)
        val filesListLocal = localHandler.getChangedFilesLocalBranch(mergeBase, branchA, localRepoPath)
        val filesListRemote = remoteHandler.getChangedFilesRemoteBranch(owner, repo, branchA, mergeBase, accessToken)

        return getCommonItems(filesListLocal, filesListRemote)
    }
}