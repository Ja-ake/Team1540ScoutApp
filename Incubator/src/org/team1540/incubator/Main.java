package org.team1540.incubator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.team1540.incubator.bluetooth.BluetoothServer;
import org.team1540.incubator.console.CommandListener;
import org.team1540.incubator.console.ConsoleManager;
import org.team1540.incubator.database.Database;
import org.team1540.incubator.database.Table;
import org.team1540.incubator.database.Table.ResultSetExecuteor;
import org.team1540.incubator.informationProcessing.Information;
import org.team1540.incubator.informationProcessing.InformationProcessor;
import org.team1540.incubator.informationProcessing.InformationProcessor.NotProcessableException;
import org.team1540.incubator.informationProcessing.TypeProcessor;
import org.team1540.incubator.informationProcessing.util.MappingTableProccessor;
import org.team1540.incubator.utils.DispatchSchema;

public class Main {

	public static final DispatchSchema year2014PitTableScheme= new DispatchSchema("pitScout").require(
		"event","scouter","numWheels","wheels","shooter",
		"range","collector","catch","start","playStyle",
		"autoScore","driveForwardAuto","hotGoalDetection","canShift",
		"robotHeight","disabledPlan","notes");

	public static final DispatchSchema year2014StandTableScheme=new DispatchSchema("standScout").require(
		"competition","match","scouter","scoutTarget","team","noShow",
		"brokeDown","movedInAuto","isGoalie","autoGoals","missedAutoGoals",
		"highGoals","lowGoals","missedHighGoals","missedLowGoals",
		"trussThrows","passes","trussCatches","receptions","humanPlayerAssist",
		"humanPlayerFouls","fouls","technical","shootingRating","defenseRating",
		"passingRating","recievingRating","drivingRating","teamworkRating","notes");

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, IOException {
		final Database events;
		final Table pits;
		final Table stands;

		Class.forName("org.sqlite.JDBC");
		try {
			events=new Database("eventsDatabase");
			pits=events.createTableIfNotExist(year2014PitTableScheme);
			stands=events.createTableIfNotExist(year2014StandTableScheme);
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		//Prepare listeners
		MappingTableProccessor year2014pitInterp=new MappingTableProccessor(year2014PitTableScheme, pits, true);
		MappingTableProccessor year2014standInterp=new MappingTableProccessor(year2014StandTableScheme,stands,true);
		TypeProcessor logInterp=new TypeProcessor("<log>") {
			@Override
			protected void proccessInformation(Information i) throws NotProcessableException {
				System.out.println("Logging message:"+Arrays.toString(i.informationContent));
			}
		};
		//Setup server and Information Processor with listeners
		final BluetoothServer blueTooth=new BluetoothServer("513A4666E63443E0A2FADC74058D74A2","Competative Analysis");
		final InformationProcessor info=new InformationProcessor(new TypeProcessor[]{
				year2014pitInterp,
				year2014standInterp,
				logInterp
		});
		info.autoProcess=true;
		//Setup connection generator
		blueTooth.connectionListeners.add(info);
		//Start server when setup is done
		blueTooth.start();
		ConsoleManager consoleManager=new ConsoleManager(System.in);
		consoleManager.addCommand(new CommandListener("INFO") {
			@Override
			public void runCommand(String[] args) {
				try {
					System.out.println(pits.encode(year2014PitTableScheme));
					System.out.println(pits.encode(year2014StandTableScheme));
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		});
		consoleManager.addCommand(new CommandListener("CSV") {
			@Override
			public void runCommand(String[] args) {
				try {
					File pitIntFile=new File("./output/pits.csv");
					pitIntFile.delete();
					BufferedWriter pitFile=new BufferedWriter(new FileWriter(pitIntFile));
					pitFile.append(pits.encode(year2014PitTableScheme));
					pitFile.close();

					File standIntFile=new File("./output/stands.csv");
					standIntFile.delete();
					BufferedWriter standFile=new BufferedWriter(new FileWriter(standIntFile));
					standFile.append(pits.encode(year2014StandTableScheme));
					standFile.close();
				}catch(SQLException e){
					throw new RuntimeException(e);
				}catch(IOException e){
					throw new RuntimeException(e);
				}
			}
		});
		consoleManager.addCommand(new CommandListener("TABLES"){
			@Override
			public void runCommand(String[] args)  {
				System.out.println("Testing...");
				try{
					pits.onAllRows(new ResultSetExecuteor(){
						@Override
						public void execute(ResultSet r) throws SQLException {
							while(r.next()){
								System.out.println(r.getString(1));
							}
						}
					});
				}catch(SQLException e){
					e.printStackTrace();
				};
			}
		});
	}
}
