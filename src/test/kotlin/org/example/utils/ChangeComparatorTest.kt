package org.example.utils

import org.example.org.example.utils.getCommonItems
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ChangeComparatorTest {

    @Test
    fun testGetCommonItems_BothListsHaveCommonItems() {
        val localChanges = listOf("file1.txt", "file2.txt", "file3.txt")
        val remoteChanges = listOf("file2.txt", "file3.txt", "file4.txt")

        val result = getCommonItems(localChanges, remoteChanges)

        assertEquals(listOf("file2.txt", "file3.txt"), result)
    }

    @Test
    fun testGetCommonItems_NoCommonItems() {
        val localChanges = listOf("file1.txt", "file2.txt")
        val remoteChanges = listOf("file3.txt", "file4.txt")

        val result = getCommonItems(localChanges, remoteChanges)

        assertTrue(result.isEmpty())
    }

    @Test
    fun testGetCommonItems_EmptyLocalList() {
        val localChanges = emptyList<String>()
        val remoteChanges = listOf("file1.txt", "file2.txt")

        val result = getCommonItems(localChanges, remoteChanges)

        assertTrue(result.isEmpty())
    }

    @Test
    fun testGetCommonItems_EmptyRemoteList() {
        val localChanges = listOf("file1.txt", "file2.txt")
        val remoteChanges = emptyList<String>()

        val result = getCommonItems(localChanges, remoteChanges)

        assertTrue(result.isEmpty())
    }

    @Test
    fun testGetCommonItems_BothListsAreEmpty() {
        val localChanges = emptyList<String>()
        val remoteChanges = emptyList<String>()

        val result = getCommonItems(localChanges, remoteChanges)

        assertTrue(result.isEmpty())
    }
}