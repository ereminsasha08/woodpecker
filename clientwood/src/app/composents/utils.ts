export class Utils {
  public static locale = {
    firstDayOfWeek: 1,
    dayNames: [
      'Воскресенье',
      'Понедельник',
      'Вторник',
      'Среда',
      'Четверг',
      'Пятница',
      'Суббота',
    ],
    dayNamesShort: ['Вск', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
    dayNamesMin: ['Вск', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
    monthNames: [
      'Январь',
      'Февраль',
      'Март',
      'Апрель',
      'Май',
      'Июнь',
      'Июль',
      'Август',
      'Сентябрь',
      'Октябрь',
      'Ноябрь',
      'Декабрь',
    ],
    monthNamesShort: [
      'Янв',
      'Фев',
      'Мар',
      'Апр',
      'Май',
      'Июн',
      'Июл',
      'Авг',
      'Сен',
      'Окт',
      'Ноя',
      'Дек',
    ],
    today: 'Сегодня',
    clear: 'Очистить',
    yearRange: '1990:2050',
  };

  private static readonly REST = '/budget/rest/';
  private static readonly REST_API = '/budget/rest/api/';

  // public static getRestPath(isAuth?: boolean): string {
  //   return window.location.origin + (isAuth ? this.REST : this.REST_API);
  // }
  //
  // public static clearStorageToken() {
  //   localStorage.setItem('token', null);
  // }
  //
  // public static removeStorageExistToken() {
  //   const stored = localStorage.getItem('token');
  //   if (stored && stored !== 'null') {
  //     Utils.clearStorageToken();
  //   }
  // }
  //
  // public static deepCopyItem<T>(item: T): T {
  //   return JSON.parse(JSON.stringify(item));
  // }
  //
  // public static deepEquals<T>(first: T, second: T): boolean {
  //   return JSON.stringify(first) === JSON.stringify(second);
  // }
  //
  // public static extractData(res: Response) {
  //   if (res.json) {
  //     return res.json();
  //   }
  //   return res;
  // }
  //
  // public static getItemName(obj: any): string {
  //   if (obj) {
  //     return obj.itemName ? obj.itemName : obj;
  //   }
  //   return '';
  // }
  //
  // public static getDate(obj: any): string {
  //   if (obj) {
  //     const date = new Date(obj);
  //     const datePipe = new DatePipe('en-US');
  //     return datePipe.transform(date, 'dd.MM.yyyy');
  //   }
  //   return '';
  // }
  //
  // public static getMSKDate(obj: string | number | Date): string {
  //   if (obj) {
  //     return formatDate(obj, 'yyyy-MM-ddTHH:mm:ssZ', 'en-US', 'GMT+3');
  //   }
  //   return '';
  // }
  //
  // public static getDateTime(obj: any): string {
  //   if (obj) {
  //     const date = new Date(obj);
  //     const datePipe = new DatePipe('en-US');
  //     return datePipe.transform(date, 'dd.MM.yyyy HH:mm');
  //   }
  //   return '';
  // }
  //
  // public static buildSortPriorityList(multiSortMeta: SortMeta[]) {
  //   if (!multiSortMeta) {
  //     return [];
  //   }
  //
  //   const result: FieldSortDirection[] = [];
  //   multiSortMeta.forEach((s) => {
  //     const direction = s.order > 0 ? 'ASC' : 'DESC';
  //     result.push(new FieldSortDirection(s.field, direction));
  //   });
  //   result.push(new FieldSortDirection('id', 'ASC'));
  //   return result;
  // }
  //
  // public static formatSum(sum: number): string {
  //   if (sum) {
  //     return sum.toLocaleString(undefined, { maximumFractionDigits: 2 });
  //   } else {
  //     return null;
  //   }
  // }
  //
  // public static getUserName(
  //   user: SecurityUser,
  //   defaultEmptyValue?: string
  // ): string {
  //   return user ? user.login : defaultEmptyValue;
  // }
  //
  // public static _getEnumValues(
  //   type: any,
  //   excludedNamedConstants?: string[]
  // ): any[] {
  //   const values = [];
  //   Object.keys(type).forEach((p) => {
  //     const value = type[p];
  //     const isInclude =
  //       !excludedNamedConstants || !excludedNamedConstants.find((v) => v === p);
  //     if (isInclude) {
  //       values.push(value);
  //     }
  //   });
  //   return values;
  // }
  //
  // public static _getNamedConstantByValueMap(type: any): Map<any, string> {
  //   const namedConstantByValue = new Map<any, string>();
  //   Object.keys(type).forEach((p) => {
  //     const value = type[p];
  //     namedConstantByValue.set(value, p);
  //   });
  //   return namedConstantByValue;
  // }
  //
  // public static _transformValueToNamedConstant(
  //   target: any,
  //   namedConstantByValue: Map<any, string>
  // ): any {
  //   const namedConstant = namedConstantByValue.get(target);
  //   if (namedConstant) {
  //     target = namedConstant;
  //   }
  //   return target;
  // }
  //
  // public static _convertStringSum(sum: string): number {
  //   if (sum && sum.constructor === String) {
  //     return parseFloat(sum.replace(/[^,0-9-]/gim, '').replace(',', '.'));
  //   } else {
  //     return 0;
  //   }
  // }
  //
  // public static downloadFile(file: Blob, fileName: string) {
  //   const date = new Date().toLocaleString('ru').replace(/\D/g, '_');
  //   const fileNameWithTimeStamp = `${fileName} ${date}.xls`;
  //   const blob = new Blob([file]);
  //   FileSaver.saveAs(blob, fileNameWithTimeStamp);
  // }
  //
  // public static checkTableVersion(
  //   version: number,
  //   tableStateKey: string,
  //   tableVersionStateKey: string
  // ): void {
  //   if (version && tableVersionStateKey) {
  //     const tableVersion: number = JSON.parse(
  //       localStorage.getItem(tableVersionStateKey)
  //     );
  //     if (tableStateKey && tableVersion && version !== tableVersion) {
  //       localStorage.removeItem(tableStateKey);
  //     }
  //     localStorage.setItem(tableVersionStateKey, JSON.stringify(version));
  //   }
  // }
  //
  // public static findUserRoleAfter(signRole: RoleType): RoleType {
  //   switch (signRole) {
  //     case RoleType.SIGN_CONTROLLER:
  //       return RoleType.SIGN_BUDGET_MANAGER;
  //     case RoleType.SIGN_BUDGET_MANAGER:
  //       return RoleType.SIGN_BUDGET_MASTER;
  //     case RoleType.SIGN_BUDGET_MASTER:
  //       return RoleType.SIGN_CONTROL;
  //   }
  // }
  //
  // public static getQuarter(date): number {
  //   return Math.floor(date.getMonth() / 3);
  // }
  //
  // public static getFirstMonthOfNextQuarter(date): number {
  //   return (this.getQuarter(date) * 3 + 3 + 12) % 12;
  // }
  //
  // public static getYearOfNextQuarter(date): number {
  //   const firstMonthOfNextQuarter = this.getFirstMonthOfNextQuarter(date);
  //   const year: number = date.getFullYear();
  //   return firstMonthOfNextQuarter === 0 ? year + 1 : year;
  // }
}
