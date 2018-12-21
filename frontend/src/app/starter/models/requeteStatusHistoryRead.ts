import {Requete} from '../../admin/models/requete/requete';
import {StatusHistory} from './statusHistory';

export class RequeteStatusHistoryRead {
  public requete: Requete;
  public history: StatusHistory[];
}
