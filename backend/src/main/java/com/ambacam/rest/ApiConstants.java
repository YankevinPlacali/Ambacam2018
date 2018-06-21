package com.ambacam.rest;

public class ApiConstants {

	public static final String ROLE_COLLECTION = "/roles";

	public static final String ROLE_ITEM = ROLE_COLLECTION + "/{roleId}";

	public static final String STATUS_REQUETE_COLLECTION = "/status-requetes";

	public static final String STATUS_REQUETE_ITEM = STATUS_REQUETE_COLLECTION + "/{statusRequeteId}";

	public static final String MOTIF_SUPPRESSION_COLLECTION = "/motif-suppressions";

	public static final String MOTIF_SUPPRESSION_ITEM = MOTIF_SUPPRESSION_COLLECTION + "/{motifSuppressionId}";

	public static final String ACTION_COLLECTION = "/actions";

	public static final String ACTION_ITEM = ACTION_COLLECTION + "/{actionId}";

	public static final String LOG_COLLECTION = "/logs/search";

}
