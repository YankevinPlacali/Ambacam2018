import {Server} from './server';

export class AppConstant {

  // Role urls
  public static ROLE_COLLECTION_PATH = Server.URL + '/roles';
  public static ROLE_ITEM_PATH = AppConstant.ROLE_COLLECTION_PATH + '/{0}';

  // Pays urls
  public static PAYS_COLLECTION_PATH = Server.URL + '/pays';
  public static PAYS_ITEM_PATH = AppConstant.PAYS_COLLECTION_PATH + '/{0}';

  // Action urls
  public static ACTION_COLLECTION_PATH = Server.URL + '/roles';
  public static ACTION_ITEM_PATH = AppConstant.ACTION_COLLECTION_PATH + '/{0}';

  // Type Requetes urls
  public static TYPE_REQUETES_COLLECTION_PATH = Server.URL + '/type-requetes';
  public static TYPE_REQUETES_ITEM_PATH = AppConstant.TYPE_REQUETES_COLLECTION_PATH + '/{0}';

  // Autorites urls
  public static AUTORITE_COLLECTION_PATH = Server.URL + '/autorites';
  public static AUTORITE_ITEM_PATH = AppConstant.AUTORITE_COLLECTION_PATH + '/{0}';

  // Operateur urls
  public static OPERATEUR_COLLECTION = Server.URL + '/operateurs';
  public static OPERATEUR_ITEM_PATH = AppConstant.OPERATEUR_COLLECTION + '/{0}';
  public static OPERATEUR_ITEM_BY_USERNAME = AppConstant.OPERATEUR_COLLECTION + '/by_username';

  // Auth urls
  public static AUTH_AS_OPERATEUR_PATH = Server.URL + '/oauth/token?grant_type=password&username={0}&password={1}';
  public static AUTH_REVOKE_PATH = Server.URL + '/tokens/revoke';

  // Motifs urls
  public static MOTIF_COLLECTION_PATH = Server.URL + '/motif-suppressions';
  public static MOTIF_ITEM_PATH = AppConstant.MOTIF_COLLECTION_PATH + '/{0}';
  
  // Statut urls
  public static STATUT_COLLECTION_PATH = Server.URL + '/status-requetes';
  public static STATUT_ITEM_PATH = AppConstant.STATUT_COLLECTION_PATH + '/{0}';

  // Auth credentials
  public static AUTH_USERNAME = 'ambacam2018';
  public static AUTH_SECRET = 'ambacam-2018-secret';

  // Requerant urls
  public static REQUERANT_COLLECTION_PATH = Server.URL + '/requerants';
  public static REQUERANT_ITEM_PATH = AppConstant.REQUERANT_COLLECTION_PATH + '/{0}';
}
