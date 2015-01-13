package edu.team1540.rabbit.schema;

import edu.team1540.egg.util.coms.dispatch.DispatchSchema;

public enum RabbitSchemas {
	PIT_SCOUT("<rabbitPitScouting>", "scout", "robot", "numWheels",
			"mainWheelType", "coolnessOfWidgets", "chillness"), STAND_SCOUT(
			"<rabbitStandScouting>", "scout", "robot", "match#",
			"widgetsAtEnd", "gotDescored", "descoredRobot", "fouls",
			"obscured", "defensiveSkill", "offensiveSkill");
	public final DispatchSchema SCHEME;

	RabbitSchemas(final String type, final String... requirments) {
		SCHEME = new DispatchSchema(type);
		SCHEME.require(requirments);
		SCHEME.finalize();
	}
}
