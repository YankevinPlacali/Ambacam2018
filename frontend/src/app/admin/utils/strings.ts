export class Strings {
  public static format(str: string, ...parameters: any[]): string {

    if (str === null) {
      return str;
    } else {
      let strCpy = str;
      const nbParam = parameters.length;

      for (let i = 0; i < nbParam; i++) {
        strCpy = strCpy.replace('{' + i + '}', parameters[i]);
      }

      return strCpy;
    }
  }
}
