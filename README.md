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

1- Inside Astah Professional, with your opened project, click on Tools > XML Input & Output > Save as XML Project.

2- Go to the folder where the project is and copy the generated XML file.

3- Open the astah-xmi-unbdali project on Eclipse. ***(this solution is temporary for now)***

4- Paste your XML file inside the "tests" folder. 

5- Open the MainClass on the br.unb.xmiconverter package.

6- Go on menu Run > Run Configurations...

7- Click on the tab "Arguments" and in the box "Program arguments" insert 2 lines:
	1) Name of the UML modeling tool that generated the XMI file. Currently there is only "astah" and "papyrus" options. More options will
	be available in the future.
	2) Name of the path of your file, relative to the root folder of the project.
		The name will probably be "tests/<nameofyourfile>.xml". To get this name you can right click on your file on Eclipse and select "Copy Qualified Name", paste and adjust the name accordingly.

8- Finally, click on "Apply" and "Run".

9- The result in PRISM language is in text form on the console, starting with "dtmc" and ending with "endmodule". A file with the same name and a ".pm" extension is generated inside the same folder. If the original diagram is well-constructed, the &#60;filename&#62;.pm is a PRISM compilable file.
In case you don't see the output file, refresh the project (hotkey: F5).

