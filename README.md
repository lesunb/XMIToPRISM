# XMIToPRISM
UML diagrams as XMI files transformed to Discrete-Time Markov Chains (DTMCs) in PRISM language via the UnB-Dependability Analysis Library (UnB-DALi). Check the [UnB-DALi project](https://github.com/lesunb/UnB-DALi).

The project's goal is to extract the information of an UML diagram, in the form of an XMI file, and use it as input to UnB-DALi, that conducts the graph transformation of an UML diagram to a DTMC in the language of the PRISM model checker tool.

# How to use

## Annotate the probabilities and Export the XMI file of your diagram

Follow the instructions for the corresponding tool inside the folder "docs/UML Modeling Tools Instructions". Each tool has it's own way of inserting the probabilities.
Currently supported UML Modeling Tools: Astah, Magic Draw, Modelio and Papyrus.

## Build the project and create JAR file
**Important: Make sure you have Java installed and added to your PATH environment variable.**

1. Install Gradle [(https://gradle.org/)](https://gradle.org/) in your IDE (Eclipse/IntelliJ).
2. Let's use Eclipse as the example. Clone the project to Eclipse.
3. Open the project folder via terminal and run:
   `./gradlew eclipse clean build`
4. Import the project to the workspace using the `Gradle Project` option and then browse to the folder -> click the Build Model button -> Finish!

**To build** the project, open the project folder via terminal and type:
```
  ./gradlew clean build
```

**To make the JAR file** and use it as a program, type:
```
  ./gradlew clean fatJar
```

The JAR file will be created inside the `/build/libs/` folder.

## Run the JAR file via terminal

1. Put the JAR file in the folder where your XMI files are.

2. Go to this folder via terminal and enter a command:
	
- If you want to convert all the XMI files inside the folder, run the command:
	
`java -jar xtp.jar`

- If you want to convert one or any number of files, type their names as arguments, like this:

`java -jar xtp.jar diagram1.xml diagram2.xml ... diagramN.xml`

The result of the conversion(s) will show the files names alongside "[SUCCESS]" or "[FAIL]" messages.

For each **successfully** converted XMI file, a PRISM file output (.pm) will be created in the same folder, with the same name as the original file.
