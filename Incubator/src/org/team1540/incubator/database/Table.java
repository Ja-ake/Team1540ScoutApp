package org.team1540.incubator.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.team1540.incubator.database.Database.WithStatment;
import org.team1540.incubator.utils.DispatchSchema;
import org.team1540.incubator.utils.Utilities;

public class Table {

	protected final String tableName;
	protected final Database superDatabase;

	public Table(String tableName, Database superDatabase) {
		this.tableName = tableName;
		this.superDatabase = superDatabase;
	}

	public void addRowToTable(final String[] rowStrings) throws SQLException {
		superDatabase.exec(new WithStatment() {
			@Override
			public void with(Statement s) throws SQLException {
				String rows = Utilities.join(",", Arrays.asList(rowStrings));
				s.execute("INSERT INTO \'" + tableName + "\'\nVALUES (" + rows + ")");
			}
		});
	}

	public void onAllRows(final ResultSetExecuteor r) throws SQLException {
		superDatabase.exec(new WithStatment() {
			@Override
			public void with(Statement s) throws SQLException {
				s.execute("SELECT * FROM " + tableName);
				r.execute(s.getResultSet());
			}
		});
	}

	public String encode(final DispatchSchema year2014pittablescheme) throws SQLException {
		final StringBuilder finalString = new StringBuilder();
		this.onAllRows(new ResultSetExecuteor() {
			@Override
			public void execute(ResultSet standInfo) throws SQLException {
				do {
					StringBuilder out = new StringBuilder();
					for (int index = 1; index < year2014pittablescheme.getRequirments().size() + 1; index++) {
						out.append(standInfo.getString(index) + ",");
					}
					out.substring(0, out.length() - 1);
					finalString.append(out + "\n");
					System.out.println(finalString);
				} while (standInfo.next());
			}
		});
		return finalString.toString();
	}

	public interface ResultSetExecuteor {
		public void execute(ResultSet r) throws SQLException;
	}
}