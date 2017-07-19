# XMI-PRISM-Converter
UML diagrams as XMI files transformed to Discrete-Time Markov Chains (DTMCs) via the UnB-Dependability Analysis Library (UnB-DALi).

This project is to attempt to extract the information of an UML diagram in the form of an XMI file so that it can be used as input to the UnB-Dependability Analisys Library, that conducts the graph transformation of a UML diagram to a Discrete Time Markov Chain (DTMC) in the language of the PRISM model checker tool.

# How to use the XMI-PRISM-Converter tool

## Annotate the probabilities and Export the XMI file of your diagram

Follow the instructions for your tool inside the folder "docs/UML Tools Instructions".


## Run the JAR file via terminal

1- Open the XMI-PRISM-Converter project on Eclipse.

2- Go to the "build" folder. Copy the JAR File named "xpconverter.jar", short for XMI-To-PRISM Converter.

3- Paste the JAR in the folder where the .XMI file(s) is.

4- Through the terminal/prompt, go to this folder and enter:

**java -jar xpconverter.jar**

In this example you will convert all the XMI files inside the folder. Be sure that they were exported by the same UML tool.

If you want to convert just one, or two, or any arbitrary number of files, just type them as arguments for the program. Example

**java -jar xpconverter.jar diagram1.xml diagram2.xml ... diagramN.xml**

5- At the start of the program you will be asked to choose an option, a number that corresponds to the tool that generated the XMI files.

6- The result of the conversion(s) will be out on the prompt console as "SUCCESS" or "FAIL" by the side of the file name. An output file will be created in the same folder, with the extension ".pm" (a PRISM file) for each successfully converted file.

**Important: Make sure you have Java installed and added to your PATH environment variable.**
