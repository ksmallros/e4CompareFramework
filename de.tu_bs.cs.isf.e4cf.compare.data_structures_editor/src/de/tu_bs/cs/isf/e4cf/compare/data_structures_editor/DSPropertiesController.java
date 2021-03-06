package de.tu_bs.cs.isf.e4cf.compare.data_structures_editor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.StringValueImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.AbstractAttribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Attribute;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Value;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.CommandStack;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.DeleteAttributeAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.ModifyValuesAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.NodeAttributePair;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.manager.actions.RenamePropertyAction;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.stringtable.DSEditorST;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.PropertiesViewUtilities;
import de.tu_bs.cs.isf.e4cf.compare.data_structures_editor.utilities.TreeViewUtilities;
import de.tu_bs.cs.isf.e4cf.core.util.ServiceContainer;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableView;

/**
 * 
 * @author Team05
 *
 */
public class DSPropertiesController {

	@Inject
	private ServiceContainer services;

	@FXML
	private ContextMenu contextMenu;

	@FXML
	private TableView<Attribute> propertiesTable;

	private Node selectedNode;

	private CommandStack propertiesManager = new CommandStack();

	/**
	 * refreshes views
	 */
	private void refreshGUI() {
		services.eventBroker.send(DSEditorST.REFRESH_TREEVIEW_EVENT, true);
		services.eventBroker.send(DSEditorST.REOPEN_ITEM_EVENT, true);
	}

	/**
	 * @return the current attributes values before changes are made
	 */
	List<Value> getCurrentAttributeValues() {
		List<Value> oldAttributeValues = new ArrayList<Value>();
		// save the old attribute values
		for (Value value : getSelectedItem().getAttributeValues()) {
			oldAttributeValues.add(value);
		}
		return oldAttributeValues;
	}

	/**
	 * @return the currently selected item
	 */
	private Attribute getSelectedItem() {
		return propertiesTable.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method to edit the name of a property
	 */
	@FXML
	public void editNodeProperty() {
		String s = PropertiesViewUtilities.getInput("Please enter new property designation");
		String oldName = "";
		if (s != null) {
			AbstractAttribute attr = (AbstractAttribute) getSelectedItem();
			oldName = getSelectedItem().getAttributeKey();
			attr.setAttributeKey(s);
			propertiesManager.execute(
					new RenamePropertyAction("renameProperty", oldName, (AbstractAttribute) getSelectedItem()));
			refreshGUI();
		}
	}

	/**
	 * edits the selected Nodes Value
	 */
	@FXML
	public void editNodeValue() {
		List<Value> oldAttributeValues = getCurrentAttributeValues();
		String newValue = PropertiesViewUtilities.getInput("Please enter new Value");
		List<Value> newAttributeValues;

		if (newValue != null) {
			getSelectedItem().getAttributeValues().clear();
			getSelectedItem().getAttributeValues().add(new StringValueImpl(newValue));
			newAttributeValues = getSelectedItem().getAttributeValues();
			propertiesManager.execute(new ModifyValuesAction("Modify Values", oldAttributeValues, newAttributeValues));
			refreshGUI();
		}
	}

	/**
	 * removes the selected attribute
	 */
	@FXML
	public void removeNodeAttribute() {
		if (TreeViewUtilities.confirmationAlert("Are you sure you want to do this?") == true) {
			Attribute deletedAttribute = getSelectedItem();
			services.eventBroker.send(DSEditorST.DELETE_ATTRIBUTE_EVENT, getSelectedItem());
			services.eventBroker.send(DSEditorST.ASK_FOR_SELECTED_ITEM_EVENT, true);
			NodeAttributePair pair = new NodeAttributePair(selectedNode, deletedAttribute);
			propertiesManager.execute(new DeleteAttributeAction("removeAction", pair, services));
			refreshGUI();
		}

	}

	/**
	 * adds a new Value to the selected attribute
	 */
	@FXML
	public void addNodeValue() {
		List<Value> oldAttributeValues = getCurrentAttributeValues();
		List<Value> newAttributeValues;
		String s = PropertiesViewUtilities.getInput("Please enter another Value");
		if (s != null) {
			getSelectedItem().getAttributeValues().add(new StringValueImpl(s));
			newAttributeValues = getSelectedItem().getAttributeValues();
			propertiesManager.execute(new ModifyValuesAction("Modify Values", oldAttributeValues, newAttributeValues));
			refreshGUI();
		}
	}

	/**
	 * undoes the last action made in the properties view
	 */
	@FXML
	void undoProperties() {
		propertiesManager.undo();
		refreshGUI();
	}

	/**
	 * Event to receive the node which is currently selected in the treeView
	 * 
	 * @param node
	 */
	@Optional
	@Inject
	public void receiveSelectedNode(@UIEventTopic(DSEditorST.RECEIVE_SELECTED_NODE_EVENT) Node node) {
		selectedNode = node;
	}

	/**
	 * empties the properties table
	 * 
	 * @param bool
	 */
	@Optional
	@Inject
	public void emptyPropertiesTable(@UIEventTopic(DSEditorST.EMPTY_PROPERTIES_TABLE_EVENT) boolean bool) {
		propertiesTable.getItems().clear();
	}

	/**
	 * 
	 * @param node to be shown in the properties table
	 */
	@Optional
	@Inject
	public void showProperties(@UIEventTopic(DSEditorST.NODE_PROPERTIES_EVENT) Node node) {
		propertiesTable.getColumns().clear();
		propertiesTable = PropertiesViewUtilities.getAttributeTable(node, propertiesTable);
		propertiesTable.setOnMouseEntered(e -> contextMenu.hide());
	}

}
