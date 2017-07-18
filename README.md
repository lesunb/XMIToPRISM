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

java -jar xpconverter.jar <UML_MODELING_TOOL> [all] <name_of_file1> <name_of_file2>

Where <UML_MODELING_TOOL> is the UML Tool in which the file was created (currently the options are only "astah" and "papyrus") and <name_of_the_file> is the name of the XMI file.

So, for example, if you want to run an XMI file, exported by Astah, with the name "ad01.xml", you have to enter:

**java -jar xpconverter.jar astah ad01.xml**

If you want to run 3 files, for example, named "sd01.xml", "sd02.xml" and "sd03.xml" you have to enter:

**java -jar xpconverter.jar astah sd01.xml sd02.xml sd03.xml**

And if you want you can run the conversion of ALL the XMI/XML files inside the current folder, you have to enter:

**java -jar xpconverter.jar astah all**

5- The result of the conversion will be out on the prompt console as "SUCCESS" or "FAIL" by the side of the file name. An output file will be created in the same folder, with the extension ".pm" (a PRISM file). Be aware that the output file will **only be created if the conversion is successful**, that is, that the diagram is well constructed according to the rules of the UnB-DALi Library.

**Important: Make sure you have Java installed and added to your PATH environment variable.**
