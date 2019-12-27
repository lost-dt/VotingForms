import { Injectable } from '@angular/core';
import { LocalStorageService } from './local-storage.service';

@Injectable({providedIn: 'root'})
export class UserService {

  public initUser(formHash: string): void {
    if (!this.isPresentUser()) {
      const id = this.generateUserId();
      LocalStorageService.setUserInfo(id, [formHash]);
    } else {
      LocalStorageService.addSubmittedForm(formHash);
    }
  }

  public isPresentUser(): boolean {
    return !!LocalStorageService.getUserId();
  }

  public haveSubmitted(formHash: string): boolean {
    const submittedForms = LocalStorageService.getSubmittedForms();
    if (!submittedForms) {
      return false;
    }
    return submittedForms.includes(formHash);
  }

  private generateUserId(): number {
    return Math.floor(100000000 + Math.random() * 900000000);
  }
}
