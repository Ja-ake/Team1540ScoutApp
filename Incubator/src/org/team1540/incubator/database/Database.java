package org.team1540.incubator.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.team1540.incubator.utils.DispatchSchema;
import org.team1540.incubator.utils.Utilities;

public class Database {

	protected static final String verifyExistance="SELECT * FROM ?";
	protected static final String dropTable="DROP TABLE ?";

	private Connection databaseConnect;

	public Database(String databaseName) throws SQLException{
		databaseConnect=DriverManager.getConnection("jdbc:sqlite:./resources/"+databaseName+".db");

	}
	public boolean verifyTableExistance(String tableName)throws SQLException{
		final String table="'"+tableName+"'";
		try{
			exec(new WithStatment(){
				@Override
				public void with(Statement s) throws SQLException {
					s.execute("SELECT * FROM ?".replace("?", table));
				}
			});
			return true;
		}catch(SQLException e){
			e.printStackTrace();
			return false;

		}
	}
	public void createTable(final String tableName,String[] names,String[] types) throws SQLException{
		if (names.length!=types.length) throw new IllegalArgumentException("names and types must be the same legnth!");
		final String[] values=Utilities.merge(" ",names,types);
		exec(new WithStatment(){
			@Override
			public void with(Statement s) throws SQLException {
				s.execute("CREATE TABLE "+"'"+tableName+"'"+"\n"+"("+Utilities.join(",",Arrays.asList(values))+")");
			}
		});

	}

	public void createTableOfStrings(DispatchSchema s) throws SQLException{
		String[] names=new String[s.getRequirments().size()];
		s.getRequirments().toArray(names);
		createTableOfStrings(s.getType(),names);
	}

	public void createTableOfStrings(String tableName,String[] names) throws SQLException{
		String[] dataTypes=new String[names.length];
		Arrays.fill(dataTypes, "Varchar");
		createTable(tableName,names,dataTypes);
	}

	public Table getTable(String tableName) throws SQLException{
		if(!verifyTableExistance(tableName)){
			throw new SQLException("Table does not exist!");
		}
		return new Table(tableName,this);
	}

	public void deleteTable(final String tableName) throws SQLException{
		exec(new WithPreparedStatment(dropTable){
			@Override
			public void with(PreparedStatement s) throws SQLException {
				s.setString(1, "'"+tableName+"'");
				s.execute();
			}
		});
	}

	public Table createTableIfNotExist(DispatchSchema s) throws SQLException{
		if(!verifyTableExistance(s.getType())){
			createTableOfStrings(s);
		}
		return getTable(s.getType());
	}

	public synchronized void exec(WithStatment s) throws SQLException{
		SQLException exception=null;
		try{
			Statement state=databaseConnect.createStatement();
			try{
				s.with(state);
			}catch(SQLException e){
				exception=e;
			}
			state.close();
		}catch(SQLException e){
			throw new IllegalStateException(e);
		}
		if(exception!=null){
			throw exception;
		}
	}

	public synchronized void exec(WithPreparedStatment s) throws SQLException{
		SQLException exception=null;
		try{
			PreparedStatement state=databaseConnect.prepareStatement(s.statment);
			try{
				s.with(state);
			}catch(SQLException e){
				exception=e;
			}
			state.close();
		}catch(SQLException e){
			throw new IllegalStateException(e);
		}
		if(exception!=null){
			throw exception;
		}
	}

	public abstract class WithPreparedStatment{
		public final String statment;

		public WithPreparedStatment(String statment){
			this.statment=statment;
		}

		public abstract void with(PreparedStatement s) throws SQLException;
	}

	public interface WithStatment{
		public void with(Statement s) throws SQLException;
	}
}
