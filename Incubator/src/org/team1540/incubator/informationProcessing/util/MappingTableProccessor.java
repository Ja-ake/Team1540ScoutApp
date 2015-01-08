package org.team1540.incubator.informationProcessing.util;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.team1540.incubator.database.Table;
import org.team1540.incubator.informationProcessing.InformationProcessor.NotProcessableException;
import org.team1540.incubator.utils.DispatchSchema;

public class MappingTableProccessor extends MappingTypeProcessor {

	public final Table target;
	public final DispatchSchema scheme;

	public MappingTableProccessor(DispatchSchema s, Table t, boolean fill) {
		super(s.getType(), fill);
		target = t;
		scheme = s;
	}

	@Override
	public void processMap(Map<String, String> output) throws NotProcessableException {
		List<String> req = scheme.getRequirments();
		String[] toPlace = new String[req.size()];
		for (int index = 0; index < req.size(); index++) {
			toPlace[index] = output.get(req.get(index));
		}
		try {
			System.out.println(this.processesWhat + " Recieved:\n" + output);
			target.addRowToTable(toPlace);
		} catch (SQLException e) {
			throw new NotProcessableException(e);
		}
	}

}
