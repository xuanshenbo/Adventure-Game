package main;

/**
 * Different states for before game starts. Used to distinguish between different GUI behaviours and responses for
 * easier refactoring
 * @author flanagdonn
 *
 */
public enum InitialisationState {
	SHOW_CLIENT_SERVER_OPTION, SHOW_LOAD_OR_NEW_OPTION, CONNECT_TO_SERVER,
		START_GAME, LOAD_GAME, CHOOSE_SLIDER_OPTIONS, LOAD_SAVED_PLAYER,
		CREATE_NEW_PLAYER, LOAD_PLAYER_OR_CREATE_NEW_PLAYER
	}
