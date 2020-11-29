package de.tu_bs.cs.isf.e4cf.core.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableServiceImp extends TableUtilities implements ITableService {

	/**
	 * Method to create a table from a given String tableName and the attributes
	 * from the class Column.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class which contains the attributes and their data types
	 * @throws SQLException
	 */
	@Override
	public void createTable(final String pPath, final String pDbName, final String tableName,
			final Column... attributes) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stmt = con.createStatement();
		if (attributes.length > 0) {
			if (!tableExists(pPath, pDbName, tableName)) {
				String sqlStatement = "CREATE TABLE " + tableName + "(";
				String sqlStatementPkey = "CONSTRAINT " + tableName + "_pl PRIMARY KEY (";
				boolean primaryKey = false;
				for (final Column c : attributes) {
					sqlStatement += c.getName() + " " + c.getType() + ", ";
					if (c.isUnique()) {
						sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
						sqlStatement += " UNIQUE, ";
					}
					if (c.isPrimaryKey()) {
						sqlStatementPkey += c.getName() + ", ";
						primaryKey = true;
					}
					if (c.isAutoIncrement()) {
						sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
						sqlStatement += "AUTOINCREMENT, ";
					}
					if (c.isNotNull()) {
						sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
						sqlStatement += "NOTNULL, ";
					}
				}
				if (primaryKey == true) {
					sqlStatementPkey = sqlStatementPkey.substring(0, sqlStatementPkey.length() - 2);
					sqlStatementPkey += ")";
					sqlStatement += sqlStatementPkey;
				} else {
					sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
				}
				sqlStatement += ");";
				// System.out.println("SQLSTATEMENT: " + sqlStatement);
				stmt.execute(sqlStatement);
				System.out.println("Table " + tableName + " created.");
			} else {
				System.out.println("Table " + tableName + " already exists.");
			}
		} else {
			System.out.println("Can not create table without column(s).");
		}
		con.close();
	}

	/**
	 * Method to create a table from a java-class in a generic way.
	 * 
	 * @param pPath   String the path of the database
	 * @param pDbName String the name of the database
	 * @param cls     the class from which the table will be created
	 * @throws SQLException
	 */
	@Override
	public void createTable(final String pPath, final String pDbName, final Class<?> cls) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		final String tableName = cls.getSimpleName();
		if (!tableExists(pPath, pDbName, cls.getSimpleName())) {
			String sqlStatement = "CREATE TABLE " + tableName + " (";
			// get all attribute objects from the java-class
			final Field[] fieldlist = cls.getDeclaredFields();
			for (final Field aFieldlist : fieldlist) {
				sqlStatement += aFieldlist.getName() + " " + aFieldlist.getType().getSimpleName() + ", ";
			}
			// remove the last comma
			sqlStatement = sqlStatement.substring(0, sqlStatement.length() - 2);
			sqlStatement += ");";
			s.execute(sqlStatement);
			System.out.println("Table " + tableName + " created.");
		} else {
			System.out.println("Table " + tableName + " already exists.");
		}
		con.close();
	}

	/**
	 * Method to delete a table.
	 * 
	 * @param pPath     pPath String the path of the database
	 * @param pDbName   String the name of the database
	 * @param tableName String the name of the table
	 * @throws SQLException
	 */
	@Override
	public void deleteTable(final String pPath, final String pDbName, final String tableName) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			String sqlStatement = "DROP TABLE " + tableName + ";";
			s.execute(sqlStatement);
			System.out.println("Table " + tableName + " deleted.");
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to rename a table.
	 * 
	 * @param pPath        String the path of the database
	 * @param pDbName      String the name of the database
	 * @param tableName    String the old name of the table
	 * @param NewtableName String the new name of the table
	 * @throws SQLException
	 */
	@Override
	public void renameTable(final String pPath, final String pDbName, final String tableName, final String newTableName)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement s = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			if (!tableExists(pPath, pDbName, newTableName)) {
				if (!tableName.equals(newTableName)) {
					String sqlStatement = "ALTER TABLE " + tableName + " " + "RENAME TO " + newTableName + ";";
					s.execute(sqlStatement);
					System.out.println("Renaming tablename " + tableName + " to " + newTableName);
				}
			} else {
				System.out.println("Con not Renaming tablename " + tableName + " to existing name " + newTableName);
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to add columns to an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class with the name and data type of the columns which
	 *                   should be added
	 * @throws SQLException
	 */
	@Override
	public void addColumn(final String pPath, final String pDbName, final String tableName, final Column... attributes)
			throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		final Statement stmt = con.createStatement();
		if (tableExists(pPath, pDbName, tableName)) {
			for (Column c : attributes) {
				if (!columnExists(pPath, pDbName, tableName, c.getName())) {
					String sqlStatement = "ALTER TABLE " + tableName + " ADD " + c.getName() + " " + c.getType() + ";";
					stmt.execute(sqlStatement);
					System.out.println("Column " + c.getName() + " added to table: " + tableName);
				} else {
					System.out.println("Column " + c.getName() + " already exists.");
				}
			}
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to delete column form an existing table.
	 * 
	 * @param pPath      String the path of the database
	 * @param pDbName    String the name of the database
	 * @param tableName  String the name of the table
	 * @param attributes Class with the name and data type of the columns which
	 *                   should be deleted
	 * @throws SQLException
	 */
	@Override
	public void deleteColumn(String pPath, String pDbName, String tableName, Column... attributes) throws SQLException {
		System.out.println("Delete column form table: " + tableName);
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		final List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		List<Column> removeList = new ArrayList<Column>();
		if (tableExists(pPath, pDbName, "old_" + tableName)) {
			for (Column c : columns) {
				for (int i = 0; i < attributes.length; i++) {
					if (c.getName().equals(attributes[i])) {
						removeList.add(c);
					}
				}
			}
			if (removeList.isEmpty()) {
				for (Column c : attributes) {
					System.out.print("Column" + c.getName() + " does not exist.");
				}
			}
			columns.removeAll(removeList);
			Column[] col = new Column[columns.size()];
			col = columns.toArray(col);
			createTable(pPath, pDbName, tableName, col);
			deleteTable(pPath, pDbName, "old_" + tableName);
			System.out.print("Column(s) deleted.");
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();

	}

	@Override
	/**
	 * Method to add primary key constraints to an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns to which the primary key
	 *                    will be added
	 * @throws SQLException
	 */
	public void makeColumnPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			renameTable(pPath, pDbName, tableName, "old_" + tableName);
			final List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
			List<Column> removeList = new ArrayList<Column>();
			List<Column> addList = new ArrayList<Column>();
			for (final Column c : columns) {
				for (int i = 0; i < columnNames.length; i++) {
					if (c.getName().equals(columnNames[i])) {
						removeList.add(c);
						c.setPrimaryKey(true);
						addList.add(c);
					}
				}
			}
			columns.removeAll(removeList);
			columns.addAll(addList);
			Column[] col = new Column[columns.size()];
			col = columns.toArray(col);
			createTable(pPath, pDbName, tableName, col);
			deleteTable(pPath, pDbName, "old_" + tableName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to drop primary key constraints of an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	@Override
	public void dropPrimaryKey(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			renameTable(pPath, pDbName, tableName, "old_" + tableName);
			final List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
			List<Column> removeList = new ArrayList<Column>();
			List<Column> addList = new ArrayList<Column>();
			for (Column c : columns) {
				for (int i = 0; i < columnNames.length; i++) {
					if (c.getName().equals(columnNames[i])) {
						removeList.add(c);
						c.setPrimaryKey(false);
						addList.add(c);
					}
				}
			}
			columns.removeAll(removeList);
			columns.addAll(addList);
			Column[] col = new Column[columns.size()];
			col = columns.toArray(col);
			createTable(pPath, pDbName, tableName, col);
			deleteTable(pPath, pDbName, "old_" + tableName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to make a Column Autoincrement of an existing table
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the columns of which the primary key
	 *                    will be dropped
	 * @throws SQLException
	 */
	@Override
	public void makeColumnAutoIncrement(final String pPath, final String pDbName, final String tableName,
			final String columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		final List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		List<Column> removeList = new ArrayList<Column>();
		List<Column> addList = new ArrayList<Column>();
		if (tableExists(pPath, pDbName, "old_" + tableName)) {
			for (Column c : columns) {
				if (c.getName().equals(columnNames)) {
					removeList.add(c);
					c.setAutoIncrement(true);
					addList.add(c);
				}
			}
			columns.removeAll(removeList);
			columns.addAll(addList);
			Column[] col = new Column[columns.size()];
			col = columns.toArray(col);
			createTable(pPath, pDbName, tableName, col);
			deleteTable(pPath, pDbName, "old_" + tableName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	@Override
	/**
	 * Method to add unique constraints to an existing table.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames String the name of the column to which the unique
	 *                    constraints will be added
	 * @throws SQLException
	 */
	public void makeColumnUnique(final String pPath, final String pDbName, final String tableName,
			final String columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		renameTable(pPath, pDbName, tableName, "old_" + tableName);
		final List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
		List<Column> removeList = new ArrayList<Column>();
		List<Column> addList = new ArrayList<Column>();
		if (tableExists(pPath, pDbName, "old_" + tableName)) {
			for (Column c : columns) {
				if (c.getName().equals(columnNames)) {
					removeList.add(c);
					c.setUnique(true);
					addList.add(c);
				}
			}
			columns.removeAll(removeList);
			columns.addAll(addList);
			Column[] col = new Column[columns.size()];
			col = columns.toArray(col);
			createTable(pPath, pDbName, tableName, col);
			deleteTable(pPath, pDbName, "old_" + tableName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

	/**
	 * Method to make a column not nullable.
	 * 
	 * @param pPath       String the path of the database
	 * @param pDbName     String the name of the database
	 * @param tableName   String the name of the table
	 * @param columnNames the name of the column to which the NOTNULL will be added
	 * @throws SQLException 
	 */
	@Override
	public void makeColumnNotNull(final String pPath, final String pDbName, final String tableName,
			final String... columnNames) throws SQLException {
		final Connection con = DatabaseFactory.getInstance().getDatabase(pPath, pDbName);
		if (tableExists(pPath, pDbName, tableName)) {
			renameTable(pPath, pDbName, tableName, "old_" + tableName);
			final List<Column> columns = getColumnsTable(pPath, pDbName, "old_" + tableName);
			List<Column> removeList = new ArrayList<Column>();
			List<Column> addList = new ArrayList<Column>();
			for (final Column c : columns) {
				for (int i = 0; i < columnNames.length; i++) {
					if (c.getName().equals(columnNames[i])) {
						removeList.add(c);
						c.setNotNull(true);
						addList.add(c);
					}
				}
			}
			columns.removeAll(removeList);
			columns.addAll(addList);
			Column[] col = new Column[columns.size()];
			col = columns.toArray(col);
			createTable(pPath, pDbName, tableName, col);
			deleteTable(pPath, pDbName, "old_" + tableName);
		} else {
			System.out.println("Table " + tableName + " does not exist.");
		}
		con.close();
	}

}
