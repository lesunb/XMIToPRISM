package br.unb.xmiimport;


import br.unb.dali.models.agg.markovchains.DTMC;
import br.unb.dali.models.agg.uml.ActivityDiagram;
import br.unb.dali.models.agg.uml.ad.edges.ControlFlow;
import br.unb.dali.models.agg.uml.ad.nodes.control.FinalNode;
import br.unb.dali.models.agg.uml.ad.nodes.control.InitialNode;
import br.unb.dali.models.agg.uml.ad.nodes.executable.ExecutableNode;
import br.unb.dali.util.io.IOHelper;

public class TesteGenerico {
	
	public static void main(String[] args) {
		
		try {
			String nodeType = null;
			String[] nodeIDs = null;
			String[][] edgeIDs = null;
			
			// estrutura que guarda os diagramas. construida com o XMI Reader
			
			// new XMI diagram (AD or SD). for example, AD
			ADXMI adxmi = new ADXMI("getID", "getName", "getType");
			ActivityDiagram ad = new ActivityDiagram(IOHelper.getRandomString(), adxmi.getName());
			
			// iterate over the nodes of the diagram and instantiate objects
			// nodeIDs receives the ids from XMI
			for (String id : nodeIDs) {
				switch(nodeType) {
					case "UML:Pseudostate":	ad.addInitialNode(new InitialNode(id, ad));
											break;
					case "UML:ActionState": ad.addExecutableNode(new ExecutableNode(id, ad));
											break;
					case "UML:FinalState": ad.addFinalNode(new FinalNode(id, ad));
											break;
					default: System.out.println("Invalid node type");
							 break;
				}
			}
			
			// iterate over edges of diagram and instantiate objects
			// edgeIDs receives from source and target from XMI
			for (String[] id : edgeIDs) {
				String identification = null;
				String source = null;
				String target = null;
				double probability = 0.0;
				ad.addControlFlow(new ControlFlow(identification, source, target, ad)
						.setPTS(probability));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// ids representing: an initial node, an executable node and a final node
			String[] nodeIDs = new String[] {IOHelper.getRandomString(), IOHelper.getRandomString(),IOHelper.getRandomString()};
			String[][] cfs = new String[][] {
					{IOHelper.getRandomString(), nodeIDs[0], nodeIDs[1] },
					{IOHelper.getRandomString(), nodeIDs[1], nodeIDs[2]}
			};
			
			ActivityDiagram ad = new ActivityDiagram(IOHelper.getRandomString(), "Teste");
			
			ad.addInitialNode(new InitialNode(nodeIDs[0], ad));
			ad.addExecutableNode(new ExecutableNode(nodeIDs[1], ad));
			ad.addFinalNode(new FinalNode(nodeIDs[2], ad));
			
			
			ad.addControlFlow(new ControlFlow(cfs[0][0], cfs[0][1], cfs[0][2], ad)
				.setPTS(0.7));
			ad.addControlFlow(new ControlFlow(cfs[1][0], cfs[1][1], cfs[1][2], ad)
				.setPTS(0.3));
			
			DTMC dtmc = ad.toDTMC();
			
			System.out.println(dtmc.toPRISM());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
