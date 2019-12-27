import { AuthenticationService } from '../services/authentication.service';
import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

import { FormService } from '../services/form.service';


@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent {
  public title: string;
  public formGroup: FormGroup = new FormGroup({ title: new FormControl()});

  constructor(private formService: FormService,
              private router: Router,
              private authService: AuthenticationService) {}

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
  
  createForm() {
    this.formService.createForm(this.title).subscribe(() => this.formService.getAllForms());
  }
}
