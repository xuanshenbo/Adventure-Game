package main;

/**
 * Different states for before game starts. Used to distinguish between different GUI behaviours and responses for
 * easier refactoring
 * @author flanagdonn
 *
 */
public enum InitialisationState {
	SHOW_CLIENT_SERVER_OPTION, SHOW_LOAD_OR_NEW_OPTION, CONNECT_TO_SERVER,
		MAIN, LOAD_GAME, SHOW_AVATAR_OPTIONS, CHOOSE_SLIDER_OPTIONS
	}
