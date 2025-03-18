# GitChangeFinder

GitChangeFinder is a Kotlin library designed to identify files modified independently in both a remote branch and a local branch since their common merge base. It is particularly useful for detecting diverging changes before merging or rebasing code.

---

## Features
- **Detects files modified in both local and remote branches** since their common ancestor (merge base).
- **Supports authentication with GitHub's REST API** via Personal Access Tokens (PAT).
- **Simple API** for integration into existing projects.
- **Fully tested with JUnit 5 and MockK** for robustness.

---

## Installation
### Building the JAR
1. Clone the repository.
2. Build the JAR using Gradle:
   ```bash
   ./gradlew clean build
   ```
3. The JAR file will be located in:
   ```
   build/libs/git-change-finder-1.0.0.jar
   ```

### Adding the JAR to Your Project
Add the JAR as a dependency in your Gradle project:

**Gradle (Kotlin DSL):**
```kotlin
dependencies {
    implementation(files("libs/git-change-finder-1.0.0.jar"))
}
```

**Gradle (Groovy DSL):**
```groovy
dependencies {
    implementation files('libs/git-change-finder-1.0.0.jar')
}
```

---

## Usage Example

### Importing & Using the Library
```kotlin
import my.package.GitChangeFinder

fun main() {
    val finder = GitChangeFinder()

    val changedFiles = finder.findChangedFiles(
        owner = "your-github-username",
        repo = "your-repository",
        accessToken = "your-github-token",
        localRepoPath = "/path/to/local/repo",
        branchA = "main",
        branchB = "feature-branch"
    )

    println("Files changed in both branches: $changedFiles")
}
```

### Example Output
```
Files changed in both branches: [src/main/App.kt, src/utils/Helper.kt]
```

---

## Parameters Explained
| Parameter     | Description                                                   |
| ------------- | ------------------------------------------------------------- |
| `owner`       | GitHub repository owner (username or organization)           |
| `repo`        | GitHub repository name                                        |
| `accessToken` | GitHub Personal Access Token (PAT) for authentication         |
| `localRepoPath`| Absolute path to the local repository                        |
| `branchA`     | Remote branch name (e.g., `main`)                             |
| `branchB`     | Local branch name (e.g., `feature-branch`)                    |

---

## API Design
### `GitChangeFinder` Class
```kotlin
fun findChangedFiles(
    owner: String,
    repo: String,
    accessToken: String,
    localRepoPath: String,
    branchA: String,
    branchB: String
): List<String>
```
- This is the **main entry point** of the library.
- It uses `LocalHandler` for executing git commands locally and `RemoteHandler` for fetching data from the GitHub API.
- Returns a list of files modified in both the local and remote branches.


### Architecture Overview
The library internally uses two components:
- **LocalHandler**: Handles local git operations like finding the merge base and listing changed files in a branch.
- **RemoteHandler**: Handles remote operations by making requests to the GitHub API to identify changed files in a remote branch.
  
These components are encapsulated within the GitChangeFinder class to simplify the user experience.

---

## Running Tests
The project includes unit tests for all components. To run the tests, execute:
```bash
./gradlew test
```

---

## Error Handling
- If a local git command fails, a `GitCommandException` is thrown.
- If the GitHub API call fails, a `GitHubApiException` is thrown.

---

## Troubleshooting
- **Authentication Error (401):** Make sure your `accessToken` is valid and has the necessary scopes.
- **File Not Found Error:** Ensure the provided `localRepoPath` is correct and points to a valid git repository.
- **Network Issues:** Make sure your network connection is stable when accessing the GitHub API.


