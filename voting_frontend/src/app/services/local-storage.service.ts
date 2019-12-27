export class LocalStorageService {

  private static userId = 'userId';

  public static setUserInfo(userId: number, submittedForms: string[]): void {
    this.setValue({ userId, submittedForms }, this.userId);
  }

  public static addSubmittedForm(formHash: string): void {
    const userInfo = this.getUserInfo();
    userInfo.submittedForms.push(formHash);
    this.setUserInfo(this.getUserId(), userInfo.submittedForms);
  }

  public static getUserId(): number {
    const userInfo = this.getUserInfo();
    return userInfo ? Number.parseInt(userInfo.userId, 10) : null;
  }

  public static getSubmittedForms(): string[] {
    const userInfo = this.getUserInfo();
    return userInfo ? userInfo.submittedForms : null;
  }

  public static getUserInfo(): { userId: string, submittedForms: string[]} {
    const userJSON = localStorage.getItem(this.userId);
    if (!userJSON) {
      return null;
    }
    let userData;
    try {
      userData = JSON.parse(userJSON);
    } catch (err) {
      throw err;
    }
    return userData;
  }

  public static setValue(value: any, key: string): void {
    if (value) {
      if (typeof value === 'object') {
        localStorage.setItem(key, JSON.stringify(value));
      } else {
        localStorage.setItem(key, value.toString());
      }
    } else {
      localStorage.removeItem(key);
    }
  }

  public static clearSubmittedForm(formHash: string): void {
    const userInfo = this.getUserInfo();
    if (!userInfo) {
      return;
    }
    this.setUserInfo(this.getUserId(), userInfo.submittedForms.filter(elem => elem !== formHash));
  }

}
