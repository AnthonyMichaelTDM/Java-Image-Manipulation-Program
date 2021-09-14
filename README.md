# Java-Image-Manipulation-Program
an image manipulation program coded primarily in java

based off of stuff developed for a school assignment

writen using BlueJ

I'd like to add support for multiple layers, and some other functionality in the future

current limitation is that this is (currently) a one layer editor, meaning you can't have multiple layers like you can in gimp or many other editing softwares

## to use this
you need Java installed for this to work

either download the latest release 

or clone the repo, then:
 -create a file called `gradle.properties`, and set your java home by putting the following line in the file:
    `org.gradle.java.home=` followed by the path to the java JDK on your system (make sure to escape backslashed '\', for example
    `org.gradle.java.home=C:\\Users\\6021915\\jdk-16.0.2`
 -open your terminal, navigate to where you cloned the repo, and run `./gradlew run` on windows, or `./gradlew.sh run` on linux/mac
