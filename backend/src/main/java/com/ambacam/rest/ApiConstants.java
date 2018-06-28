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

	public static final String AUTORITE_COLLECTION = "/autorites";

	public static final String AUTORITE_ITEM = AUTORITE_COLLECTION + "/{autoriteId}";

	public static final String OPERATEUR_COLLECTION = "/operateurs";

	public static final String OPERATEUR_SEARCH_COLLECTION = OPERATEUR_COLLECTION + "/search";

	public static final String OPERATEUR_ITEM = OPERATEUR_COLLECTION + "/{operateurId}";

	public static final String PAYS_COLLECTION = "/pays";

	public static final String PAYS_ITEM = PAYS_COLLECTION + "/{paysId}";

	public static final String TYPE_REQUETE_COLLECTION = "/type-requetes";

	public static final String TYPE_REQUETE_ITEM = TYPE_REQUETE_COLLECTION + "/{typeRequeteId}";

	public static final String REQUERANT_COLLECTION = "/requerants";

	public static final String REQUERANT_SEARCH_COLLECTION = REQUERANT_COLLECTION + "/search";

	public static final String REQUERANT_ITEM = REQUERANT_COLLECTION + "/{requerantId}";

}
