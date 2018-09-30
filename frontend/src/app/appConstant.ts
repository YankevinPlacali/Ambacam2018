import {Server} from './server';

export class AppConstant {

  // Role urls
  public static ROLE_COLLECTION_PATH = Server.URL + '/roles';
  public static ROLE_ITEM_PATH = AppConstant.ROLE_COLLECTION_PATH + '/{0}';

  // Type Requetes urls
  public static TYPE_REQUETES_COLLECTION_PATH = Server.URL + '/type-requetes';
  public static TYPE_REQUETES_ITEM_PATH = AppConstant.TYPE_REQUETES_COLLECTION_PATH + '/{0}';

  // Operateur urls
  public static OPERATEUR_COLLECTION = Server.URL + '/operateurs';
  public static OPERATEUR_ITEM_PATH = AppConstant.OPERATEUR_COLLECTION + '/{0}';
  public static OPERATEUR_ITEM_BY_USERNAME = AppConstant.OPERATEUR_COLLECTION + '/by_username';

  // Auth urls
  public static AUTH_AS_OPERATEUR_PATH = Server.URL + '/oauth/token?grant_type=password&username={0}&password={1}';
  public static AUTH_REVOKE_PATH = Server.URL + '/tokens/revoke';

  // Auth credentials
  public static AUTH_USERNAME = 'ambacam2018';
  public static AUTH_SECRET = 'ambacam-2018-secret';
}
