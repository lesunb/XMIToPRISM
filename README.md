# XMI-PRISM-Converter
UML diagrams as XMI files transformed to Discrete-Time Markov Chains (DTMCs) via the UnB-Dependability Analysis Library (UnB-DALi).

This project is to attempt to extract the information of an UML diagram in the form of an XMI file so that it can be used as input to the UnB-Dependability Analisys Library, that conducts the graph transformation of a UML diagram to a Discrete Time Markov Chain (DTMC) in the language of the PRISM model checker tool.

# How to use the XMI-PRISM-Converter tool

## Annotate the probabilities and Export the XMI file of your diagram

Follow the instructions for your tool inside the folder "docs/UML Tools Instructions".


## Run the JAR file via terminal

1- Open the XMI-PRISM-Converter project on Eclipse.

2- Go to the "jar-and-scripts" folder. Copy the JAR File and the configuration-scripts folder. The JAR named "xpconverter.jar", short for XMI-To-PRISM Converter.

3- Paste these contents in the folder where the .XMI file is.

4- Through the terminal/prompt, go to this folder and enter:

java -jar xpconverter.jar <UML_MODELING_TOOL> <name_of_the_file>

Where <UML_MODELING_TOOL> is the UML Tool in which the file was created (currently the options are only "astah" and "papyrus") and <name_of_the_file> is the name of the XMI file.

So, for example, if you want to run an XMI file, exported by Astah, with the name "ad01.xml", you have to enter:

**java -jar xpconverter.jar astah ad01.xml**

and press enter.

Observation: Make sure you have Java installed and added to your PATH environment variable.

5- The result will be out on the prompt console but also an output file will be created in the same folder, with the extension ".pm". Be aware that the output file will **only be created if the diagram is well constructed**, according to the rules of the UnB-DALi Library.

