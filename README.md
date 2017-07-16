# XMI-PRISM-Converter
UML diagrams as XMI files transformed to Discrete-Time Markov Chains (DTMCs) via the UnB-Dependability Analysis Library (UnB-DALi).

This project is to attempt to extract the information of an UML diagram in the form of an XMI file so that it can be used as input to the UnB-Dependability Analisys Library, that conducts the graph transformation of a UML diagram to a Discrete Time Markov Chain (DTMC) in the language of the PRISM model checker tool.

# How to insert probabilities in your Astah Diagram
We assume that the user is familiar with the Astah UML tool.

The real number of the probability, **p**, is a number where 0.0 &#8804; **p** &#8804; 1.0

### Activity Diagrams
To annotate the probability in your **transitions**:
1. Select an edge.
2. Select the tab "TaggedValue" and create a new TaggedValue by clicking on the "Add" button.
3. Give a name ("p", or "PTS") and insert the probability real number, **p**,  in the "Value" field.

### Sequence Diagrams
To annotate the probability in your **components**:
1. Select a lifeline.
2. Select the tab "TaggedValue" and create a new TaggedValue by clicking on the "Add" button.
3. Give a name ("p", or "BCompRel") and insert the probability real number, **p**,  in the "Value" field.

# How to use the transformation tool
To conduct the transformation, we assume that you have your UML model/project ready on the Astah Professional tool, properly annotated with the probabilities.

### Get the XMI file

1- Inside Astah Professional, with your opened project, click on Tools > XML Input & Output > Save as XML Project.

2- Go to the folder where the project is and copy the generated XML file.

### Run the JAR via terminal

1- Open the XMI-PRISM-Converter project on Eclipse.

2- Go to the "JAR-File-And-Scripts" folder. Copy the contents of this folder. The contents are the folder "configuration-scripts" and the latest version of the JAR named "xpconverter.jar" (short for XMI-To-PRISM Converter).

3- Paste these contents in the folder where the .XMI file is.

4- Through the terminal/prompt, go to this folder and enter:

java -jar xpconverter.jar <UML_MODELING_TOOL> <name_of_the_file>

Where <UML_MODELING_TOOL> is the UML Tool in which the file was created (currently the options are only "astah" and "papyrus") and <name_of_the_file> is the name of the XMI file.

So, for example, if you want to run an XMI file, exported by Astah, with the name "ad01.xml", you have to enter:

**java -jar xpconverter.jar astah ad01.xml**

and press enter.

Observation: Make sure you have Java installed and added to your PATH environment variable.

5- The result will be out on the prompt console but also an output file will be created in the same folder, with the extension ".pm". Be aware that the output file will **only be created if the diagram is well constructed**, according to the rules of the UnB-DALi Library.

