import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators/';

import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent {
  private username = '';
  private password = '';
  public error = '';

  constructor(private router: Router, private authService: AuthenticationService) { }

  onSubmit() {
    this.authService.login(this.username, this.password).pipe(first()).subscribe(adminData => {
        if (adminData instanceof Error) {
          console.log(adminData);
          this.error = adminData.message;
        } else {
          this.router.navigate(['admin']);
          this.error = '';
        }
      }
    );
  }

}
