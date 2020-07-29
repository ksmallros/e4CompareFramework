package de.tu_bs.cs.isf.e4cf.featuremodel.core.string_table;

public class FDEventTable {
	
	/**
	 * ERROR LISTENER MANAGEMENT
	 */
	public static final String REGISTER_ERR_LISTENER = "regErrorListener";
	public static final String DEREGISTER_ERR_LISTENER = "deregErrorListener";
	
	/**
	 * VIEW EVENTS
	 */
	public static final String RENAMED_FEATURE_EVENT = "RENAMED_FEATURE_EVENT";
	public static final String ADD_FEATURE_EVENT = "ADD_FEATURE_EVENT";
	public static final String ADD_FEATURE_BELOW = "ADD_FEATURE_BELOW_EVENT";
	public static final String ADD_FEATURE_ABOVE= "ADD_FEATURE_ABOVE_EVENT";
	public static final String REMOVE_FEATURE = "REMOVE_FEATURE";
	public static final String REMOVE_FEATURE_TRUNK = "REMOVE_FEATURE_TRUNK";
	public static final String FORMAT_DIAGRAM_EVENT = "FORMAT_DIAGRAM_EVENT";
	public static final String SET_FEATURE_ABSTRACT = "MAKE_FEATURE_ABSTRACT";
	public static final String CHANGE_SUBFEATURES_VISIBILITY = "CHANGE_SUBFEATURES_VISIBILITY";
	public static final String SET_FEATURE_MANDATORY = "SET_FEATURE_MANDATORY";
	public static final String SET_FEATURE_OPTIONAL = "SET_FEATURE_OPTIONAL";
	public static final String SET_FEATURE_NAME = "SET_FEATURE_NAME";
	public static final String GROUP_SELECTED_FEATURES_IN_FEATURE = "GROUP_SELECTED_FEATURES_IN_FEATURE";
	public static final String MOVE_SELECTED_FEATURES_UNDER_FEATURE = "MOVE_SELECTED_FEATURES_UNDER_FEATURE";
	public static final String SPLIT_FEATURE = "SPLIT_FEATURE";
	public static final String SET_REFERNECED_ARTIFACT_ID_MAP = "SET_REFERNECED_ARTIFACT_ID_MAP";
	public static final String SET_DESCRIPTION = "SET_DESCRIPTION";
	
	/**
	 * Constraint Events
	 */
	public static final String SHOW_CONSTRAINT_EVENT = "SHOW_CONSTRAINT_EVENT";
	
	public static final String SAVE_FEATURE_DIAGRAM ="SAVE_FEATURE_DIAGRAM";
	public static final String LOAD_FEATURE_DIAGRAM_FROM_FILE ="LOAD_FEATURE_DIAGRAM_FILE";
	public static final String LOAD_FEATURE_DIAGRAM ="LOAD_FEATURE_DIAGRAM";
	public static final String NEW_FEATURE_DIAGRAM = "NEW_FEATURE_DIAGRAM";
	
	public static final String START_LOGGER_DIAGRAM_EVENT = "START_LOGGER_DIAGRAM_EVENT";
	public static final String REPLAY_FD_MODIFICATION_SET = "REPLAY_FD_MODIFICATION_SET_FROM_FILE";
	public static final String REPLAY_FEATURE_DIAGRAM_CHANGED = "FEATURE_DIAGRAM_CHANGED";
	
	public static final String SET_FDE_THEME = "SET_FDE_THEME";
	
	public static final String SET_CURRENT_FEATURE = "SET_CURRENT_FEATURE";
	public static final String SET_SELECTED_FEATURE = "SET_SINGLE_SELECTED_FEATURE";
	public static final String RESET_SELECTED_FEATURES = "RESET_SELECTED_FEATURES";	
	
	
	/**
	 * LOGGER EVENTS
	 */
	public static final String LOGGER_ADD_FEATURE = "LOGGER_ADD_FEATURE";
	public static final String LOGGER_ADD_FEATURE_BELOW = "LOGGER_ADD_FEATURE_BELOW";
	public static final String LOGGER_ADD_FEATURE_ABOVE = "LOGGER_ADD_FEATURE_ABOVE";
	public static final String LOGGER_REMOVE_FEATURE = "LOGGER_REMOVE_FEATURE";
	public static final String LOGGER_CHANGED_FEATURE_ABSTRACTION = "LOGGER_CHANGED_FEATURE_ABSTRACTION";
	public static final String LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY = "LOGGER_SELECTED_FEATURE_TO_CHANGE_SUBFEATURES_VISIBILITY";
	public static final String LOGGER_CHANGED_FEATURE_VISIBILITY = "LOGGER_CHANGED_FEATURE_VISIBILITY";
	public static final String LOGGER_CHANGED_FEATURE_VARIABILITY = "LOGGER_CHANGED_FEATURE_VARIABILITY";
	public static final String LOGGER_SELECTED_FEATURE_TO_RENAME = "LOGGER_SELECTED_FEATURE_TO_RENAME";
	public static final String LOGGER_REMOVED_LINE_TO_PARENT_FEATURE = "LOGGER_REMOVED_LINE_TO_FEATURE";
	public static final String LOGGER_RESET_LINE_TO_PARENT_FEATURE = "LOGGER_RESET_LINE_TO_PARENT_FEATURE";
	public static final String LOGGER_RENAMED_FEATURE = "LOGGER_RENAMED_FEATURE";
	public static final String LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP = "LOGGER_SELECTED_FEATURE_TO_CHANGE_VARIABILITY_GROUP";
	public static final String LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP = "LOGGER_CHANGED_FEATURE_VARIABILITY_GROUP";
	public static final String LOGGER_SELECTED_FEATURE_TO_SPLIT = "LOGGER_SELECTED_FEATURE_TO_SPLIT";
	public static final String LOGGER_ADD_FEATURE_TO_SELECTION = "LOGGER_ADD_FEATURE_TO_SELECTION";
	public static final String LOGGER_REMOVE_FEATURE_FROM_SELECTION = "LOGGER_REMOVE_FEATURE_FROM_SELECTION";
	public static final String LOGGER_RESET_SELECTED_FEATURES = "LOGGER_RESET_SELECTED_FEATURES";
	public static final String LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE = "LOGGER_GROUP_SELECTED_FEATURES_IN_FEATURE";
	public static final String LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE = "LOGGER_MOVE_SELECTED_FEATURES_UNDER_FEATURE";
	public static final String LOGS = "LOGS";
	
	public static final String SET_FEATURE_AND_GROUP_BY_LOGGER = "SET_FEATURE_AND_GROUP_BY_LOGGER";
	public static final String SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER = "SET_FEATURE_ALTERNATIVE_GROUP_BY_LOGGER";
	public static final String SET_FEATURE_OR_GROUP_BY_LOGGER = "SET_FEATURE_OR_GROUP_BY_LOGGER";
	public static final String REMOVE_FEATURE_BY_LOGGER = "REMOVE_FEATURE_BY_LOGGER";
	
	/**
	 * Constraint Editor
	 */
	public static final String RECEIVE_ALL_FEATURE = "RECEIVE_ALL_FEATURE";
	public static final String SEND_ALL_FEATURE = "SEND_ALL_FEATURE";
	
	/**
	 * Feature Configuration View 
	 */
	public static final String EVENT_CREATE_CONFIGURATION = "_createConfiguration";
	public static final String EVENT_FEATURE_CONFIGURATION_ELEMENT_SELECTED = "_featureConfigurationElementSelected";

}
