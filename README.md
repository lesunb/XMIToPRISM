# Astah-XMI-UnB-DALi
Astah UML XMI files transformed to DTMC via UnB-DALi

This project is to attempt to extract the information of an UML diagram in the form of an XMI file so that it can be used as input to the UnB-Dependability Analisys Library, that conducts the graph transformation of a UML diagram to a Discrete Time Markov Chain (DTMC) in the language of the PRISM model checker tool.

# How to insert probabilities in your Astah Diagram
We assume that the user is familiar with the Astah UML tool.

The real number of the probability, **p**, is a number where 0.0 &#8804; **p** &#8804; 1.0

## Activity Diagrams
To annotate the probability in your transitions:
1. Select an edge.
2. Select the tab "TaggedValue" and create a new TaggedValue by clicking on the "Add" button.
3. Give a name ("p", or "PTS") and insert the probability real number, **p**,  in the "Value" field.

## Sequence Diagrams
To annotate the probability in your transitions:
1. Select a lifeline.
2. Select the tab "TaggedValue" and create a new TaggedValue by clicking on the "Add" button.
3. Give a name ("p", or "BCompRel") and insert the probability real number, **p**,  in the "Value" field.

# How to use the transformation tool
To conduct the transformation, we assume that you have your UML model/project ready on the Astah Professional tool, properly annotated with the probabilities.

Follow these steps:

1- Inside Astah Professional, with your opened project, click on Tools > XML Input & Output > Save as XML Project

2- Go to the folder where the project is and copy the generated XML file

3- Open the astah-xmi-unbdali project on Eclipse

4- Paste your XML file inside the "xmi-files" folder

5- Open the MainClass on the br.unb.xmiimport package

6- Execute the program

7- Insert the name of your file on the console (without the .xml extension) and press enter

8- The result in PRISM language is in text form on the console, starting with "dtmc" and ending with "endmodule". A file with the same name as the XML input file is generated with a ".pm" extension inside the "output" folder. If the original diagram is well-constructed, the &#60;filename&#62;.pm is a PRISM compilable file.
In case you don't see the output file, refresh the project (hotkey: F5).

