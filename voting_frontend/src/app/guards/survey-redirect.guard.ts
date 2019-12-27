import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material';

import { UserService } from '../services/user.service';

@Injectable({ providedIn: 'root' })
export class SurveyRedirectGuard implements CanActivate {

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private userService: UserService,
    private snackBar: MatSnackBar) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const formHash = route.paramMap.get('formHash');
      if (!this.userService.haveSubmitted(formHash)) {
        // new user so return true
        return true;
      }
      // user that already submitted current form
      this.snackBar.open('You have already submitted this survey!', 'OK', { duration: 7000 });
      this.router.navigate(['stats', formHash], { queryParams: { disable: true }});
      return false;
  }
}
