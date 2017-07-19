# XMI-PRISM-Converter
UML diagrams as XMI files transformed to Discrete-Time Markov Chains (DTMCs) via the UnB-Dependability Analysis Library (UnB-DALi).

This project is to attempt to extract the information of an UML diagram in the form of an XMI file so that it can be used as input to the UnB-Dependability Analisys Library, that conducts the graph transformation of a UML diagram to a Discrete Time Markov Chain (DTMC) in the language of the PRISM model checker tool.

# How to use the XMI-PRISM-Converter tool

## Annotate the probabilities and Export the XMI file of your diagram

Follow the instructions for your tool inside the folder "docs/UML Tools Instructions".


## Run the JAR file via terminal
**Important: Make sure you have Java installed and added to your PATH environment variable.**

1) Get the JAR file in the "build" folder. Copy and paste inside the folder where your XMI files are.
*Be sure that these files come from the same tool.

2) Go to this folder via terminal and enter a command according to what you want to do.
	
- If you want to convert all the XMI files inside the folder, run the command:
	
	'java -jar xpconverter.jar'

- If you want to convert just one or any arbitrary number of files, just type their names as arguments. Example

	java -jar xpconverter.jar diagram1.xml diagram2.xml ... diagramN.xml

3) Choose a number according to the tool that generated the XMI files.

4) The result of the conversion(s) will be out on the prompt console as "SUCCESS" or "FAIL" by the side of the file name.

An output file will be created in the same folder, with the extension ".pm" (a PRISM file) for each **successfully** converted file.
