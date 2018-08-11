import {Server} from './server';

export class AppConstant {
  public static ROLE_COLLECTION_PATH = Server.URL + '/roles';
  public static ROLE_ITEM_PATH = AppConstant.ROLE_COLLECTION_PATH + '/{0}';
}
