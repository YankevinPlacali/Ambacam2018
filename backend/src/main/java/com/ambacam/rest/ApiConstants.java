package com.ambacam.rest;

public class ApiConstants {

	// revoke token
	public static final String REVOKE_TOKEN = "/tokens/revoke";

	// access token suffix
	public static final String ACCESS_TOKEN_SUFFIX = "access_token=";

	// roles
	public static final String ROLE_COLLECTION = "/roles";

	public static final String ROLE_ITEM = ROLE_COLLECTION + "/{roleId}";

	// status-requetes
	public static final String STATUS_REQUETE_COLLECTION = "/status-requetes";

	public static final String STATUS_REQUETE_ITEM = STATUS_REQUETE_COLLECTION + "/{statusRequeteId}";

	// motif-suppressions
	public static final String MOTIF_SUPPRESSION_COLLECTION = "/motif-suppressions";

	public static final String MOTIF_SUPPRESSION_ITEM = MOTIF_SUPPRESSION_COLLECTION + "/{motifSuppressionId}";

	// actions
	public static final String ACTION_COLLECTION = "/actions";

	public static final String ACTION_ITEM = ACTION_COLLECTION + "/{actionId}";

	// logs
	public static final String LOG_COLLECTION = "/logs/search";

	// autorites
	public static final String AUTORITE_COLLECTION = "/autorites";

	public static final String AUTORITE_ITEM = AUTORITE_COLLECTION + "/{autoriteId}";

	// requerants
	public static final String REQUERANT_COLLECTION = "/requerants";

	public static final String REQUERANT_SEARCH_COLLECTION = REQUERANT_COLLECTION + "/search";

	public static final String REQUERANT_ITEM = REQUERANT_COLLECTION + "/{requerantId}";

	// operateurs
	public static final String OPERATEUR_COLLECTION = "/operateurs";

	public static final String OPERATEUR_SEARCH_COLLECTION = OPERATEUR_COLLECTION + "/search";

	public static final String OPERATEUR_ITEM = OPERATEUR_COLLECTION + "/{operateurId}";

	// operateur requete-groupes
	public static final String OPERATEUR_REQUETE_GROUPE_COLLECTION = OPERATEUR_ITEM + "/requete-groupes";

	public static final String OPERATEUR_REQUETE_GROUPE_ITEM = OPERATEUR_REQUETE_GROUPE_COLLECTION
			+ "/{requeteGroupeId}";
	public static final String OPERATEUR_REQUETE_GROUPE_ITEM_STATUS = OPERATEUR_REQUETE_GROUPE_ITEM + "/status";

	// operateur requetes
	public static final String OPERATEUR_REQUETE_COLLECTION = OPERATEUR_ITEM + "/requetes";

	// operateur requerant requetes
	public static final String OPERATEUR_REQUERANT_REQUETE_COLLECTION = OPERATEUR_ITEM + REQUERANT_ITEM + "/requetes";

	public static final String OPERATEUR_REQUERANT_REQUETE_ITEM = OPERATEUR_REQUERANT_REQUETE_COLLECTION
			+ "/{requeteId}";

	public static final String OPERATEUR_REQUERANT_REQUETE_ITEM_STATUS = OPERATEUR_REQUERANT_REQUETE_ITEM + "/status";

	// pays
	public static final String PAYS_COLLECTION = "/pays";

	public static final String PAYS_ITEM = PAYS_COLLECTION + "/{paysId}";

	// type-requetes
	public static final String TYPE_REQUETE_COLLECTION = "/type-requetes";

	public static final String TYPE_REQUETE_ITEM = TYPE_REQUETE_COLLECTION + "/{typeRequeteId}";

	// requetes
	public static final String REQUETE_BATCH_COLLECTION = "/requetes";

	public static final String REQUETE_ITEM = REQUETE_BATCH_COLLECTION + "/{requeteId}";

	// requerant requetes
	public static final String REQUERANT_REQUETE_COLLECTION = REQUERANT_ITEM + REQUETE_BATCH_COLLECTION;

	public static final String REQUERANT_REQUETE_ITEM = REQUERANT_ITEM + REQUETE_ITEM;

	// recepisses
	public static final String RECEPISSE_COLLECTION = REQUERANT_ITEM + REQUETE_ITEM + "/recepisses";

	public static final String RECEPISSE_ITEM = RECEPISSE_COLLECTION + "/{recepisseId}";

	public static final String REQUETE_COLLECTION = "/requetes";

	// Passports
	public static final String PASSPORT_COLLECTION = OPERATEUR_REQUERANT_REQUETE_ITEM + "/passports";

	public static final String PASSPORT_ITEM = PASSPORT_COLLECTION + "/{passportId}";
}
