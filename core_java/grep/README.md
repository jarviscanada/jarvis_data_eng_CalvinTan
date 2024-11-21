# Introduction
This app is a Java implementation of `grep` command used in Linux systems. It takes in three arguments: <br>
`regex` a regex pattern to search for <br>
`rootDir` a root directory to search the search for files containing the regex pattern <br>
`outFile` the file to write matched lines to <br>
By utilizing Java's Stream and Lambda implementations, the app is implemented in a way that minimizes memory usage as data does not need to be stored during processing.
The app is packaged in a uber-jar with the help of maven's build tools and is a dockerized and stored in DockerHub.


# Quick Start
```bash
# Compile the maven project and package the program into an uber-jar 
mvn clean compile project

#Run the Jar file with the three arguments [regex], [rootDirectory], [outputFile]
java -cp target/grep-1.0-SNAPSHOT.jar [regex] [rootDir] [outFile]

#View the output file of the matched lines
cat [outFile]
```

# Implemenation
## Pseudocode
This app's process method defines the high level structure for the entire app. Note that the actual implementation uses streams and stream options instead of the listed for loops shown below.
```
def process()
    matchedLines[]
    for file in listFilesRecursively(rootDir)
        for line in readFile(file)
            if containsPattern(line)
        matchedLines.add(line)
    writeToFile(matchedLines)
```

## Performance Issue
The original implementation of the app which stored files and lines in an ArrayList between intermediate operations consumed more memory than was necessary. To have the app be more lightweight, it was reimplemented using Stream which had lazy evaluation meaning it did not have the same problems with memory. 

# Test
Testing for the app was done with Java's JUnit framework. Each method in the app had a test case written for it to ensure it performed its task without errors.

# Deployment
The app was dockerized and deployed to DockerHub. This allows it to be easily accessed by anyone through Docker. <br>
```
docker pull CalvinTan445/grep
docker run --rm \ -v `pwd`/data:/data -v `pwd`/log:/log \ CalvinTan445/grep .*Romeo.*Juliet.* /data /log/grep.out
```

# Improvement
1. Allow for the user to see the line numbers of lines that matched 
2. Display in real time the memory consumption of the app as it runs 
3. Allow the app to scan just the root directory instead of performing a scan recursively 