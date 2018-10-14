export class DateUtils{
  public static formatDateFromTimestamp(dateTimestamp){

    const date = new Date(dateTimestamp);

    const day = date.getDate();
    const monthIndex = date.getMonth()+1;
    const year = date.getFullYear();

    return year + '-' + monthIndex + '-' + day;
  }
}
