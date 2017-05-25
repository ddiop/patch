
package fr.ibm.com;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Jackson {
	public final static String path = "G:/stackbddf/patch/src/ressources/";
	public final static String fReader = path + "CustomerRead.json";
	public final static String fPatch = path + "CustomerPatch.json";
	public final static String fPatch2 = path + "CustomerPatch2.json";
	public final static String fPatch3 = path + "CustomerPatch3.json";
	public final static String fWriter = path + "CustomerWrite.json";

	public static void main(String[] args) {
		try {
			// Instanciation ObjectMapper
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(Feature.ALLOW_MISSING_VALUES, true);
			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);

			// Génération d'un Object à partir de dataBind
			Customer c2 = mapper.readValue(new File(fReader), Customer.class);
			System.out.println("C2 reader : " + c2.toString());

			// Patch c2 DataBind 2
			// System.out.println("C2 Patch avant: " + c2.toString());
			ObjectReader reader = mapper.readerForUpdating(c2);
			c2 = reader.readValue(new File(fPatch));
			System.out.println("C2 Patch apres Patch: " + c2.toString());
			c2 = reader.readValue(new File(fPatch2));
			System.out.println("C2 Patch apres Patch2: " + c2.toString());
			c2 = reader.readValue(new File(fPatch3));
			System.out.println("C2 Patch apres Patch3: " + c2.toString());

			// Patch c3 en passant par un merge
			Customer c3 = mapper.readValue(new File(fReader), Customer.class);
			// System.out.println("C3 reader : " + c3.toString());
			ObjectReader reader2 = mapper.readerForUpdating(c3);
			c3 = reader2.readValue(new File(fPatch));
			// System.out.println("C3 Patch apres Patch: " + c3.toString());
			c3 = reader2.readValue(new File(fPatch2));
			System.out.println("C3 Patch avant Patch Tree: " + c3.toString());
			JsonNode myNode = mapper.valueToTree(c3);
			JsonNode updateNode = mapper.readTree(new File(fPatch3));
			myNode = patch(myNode, updateNode);
			c3 = mapper.convertValue(myNode, Customer.class);
			System.out.println("C3 Patch apres Patch Tree: " + c3.toString());

			// writer
			// Object to JSON in file
			mapper.writeValue(new File(fWriter), c3);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
// test 
	public static JsonNode patch(JsonNode node, JsonNode update) {

		Iterator<String> fieldNames = update.fieldNames();

		while (fieldNames.hasNext()) {
			String updatedFieldName = fieldNames.next();
			JsonNode valueToBeUpdated = node.get(updatedFieldName);
			JsonNode updatedValue = update.get(updatedFieldName);

			// If the node is an @ArrayNode
			if (valueToBeUpdated != null && valueToBeUpdated.isArray() && updatedValue.isArray()) {
				// running a loop for all elements of the updated ArrayNode
				for (int i = 0; i < updatedValue.size(); i++) {
					JsonNode updatedChildNode = updatedValue.get(i);
					// Create a new Node in the node that should be updated, if there was no corresponding node in it
					// Use-case - where the updateNode will have a new element in its Array
					if (valueToBeUpdated.size() <= i) {
						((ArrayNode) valueToBeUpdated).add(updatedChildNode);
					}
					// getting reference for the node to be updated
					JsonNode childNodeToBeUpdated = valueToBeUpdated.get(i);
					patch(childNodeToBeUpdated, updatedChildNode);
				}
				// if the Node is an @ObjectNode
			} else if (valueToBeUpdated != null && valueToBeUpdated.isObject()) {
				patch(valueToBeUpdated, updatedValue);
			} else {
				if (node instanceof ObjectNode) {
					((ObjectNode) node).replace(updatedFieldName, updatedValue);
				}
			}
		}
		return node;
	}
}