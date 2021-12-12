This program checks if you have been affected by the log4j RCE vulnerability, given a common directory for all logs to recursively search.  

# Usage  
1. Either compile it with the steps below, or download from the actions tab (recommended) 
2. Make sure you have java 8 or higher installed and it is the highest in your PATH  
3. Run the jar with `java -Xmx1G -jar amisafe-1.0.0.jar`, you may replace "1G" with the amount of memory you want to be available to the program  

# Building
1. Install java 8 and set it as your JAVA_HOME if you don't have it
2. Run the correct command for your platform:  

Windows powershell: .\gradlew.bat build  
Windows cmd: gradlew.bat build  
Linux or macOS: ./gradlew build  
