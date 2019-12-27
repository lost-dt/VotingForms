import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HighchartsChartModule } from 'highcharts-angular';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Material libs
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  MatButtonModule, MatRadioModule, MatInputModule, MatIconModule, MatSelectModule, MatTableModule,
  MatCheckboxModule, MatCardModule, MatDividerModule, MatSnackBarModule, MatExpansionModule } from '@angular/material';

import { AuthGuard } from './guards/auth.guard';
import { LoginRedirectGuard } from './guards/login-redirect.guard';
import { SurveyRedirectGuard } from './guards/survey-redirect.guard';

import { AppComponent } from './app.component';
import { AdminLoginComponent } from './admin-login/admin-login.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { SurveyComponent } from './survey/survey.component';
import { QuestionComponent } from './question/question.component';
import { FormListComponent } from './form-list/form-list.component';
import { QuestionFormComponent } from './question-form/question-form.component';
import { PlotComponent } from './plot/plot.component';
import { FormListItemComponent } from './form-list-item/form-list-item.component';
import { MainPageComponent } from './main-page/main-page.component';

const appRoutes: Routes = [
  {
    path: 'admin',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'login',
    component: AdminLoginComponent,
    canActivate: [LoginRedirectGuard]
  },
  {
    path: 'stats/:formHash',
    component: StatisticsComponent
  },
  {
    path: ':formHash',
    component: SurveyComponent,
    canActivate: [SurveyRedirectGuard]
  },
  {
    path: '',
    component: MainPageComponent
  },
  {
    path: '**',
    component: PageNotFoundComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    AdminLoginComponent,
    AdminDashboardComponent,
    PageNotFoundComponent,
    StatisticsComponent,
    SurveyComponent,
    QuestionComponent,
    FormListComponent,
    QuestionFormComponent,
    PlotComponent,
    FormListItemComponent,
    MainPageComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HighchartsChartModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }
    ),
    BrowserAnimationsModule,
    MatButtonModule,
    MatRadioModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatTableModule,
    MatCheckboxModule,
    MatCardModule,
    MatDividerModule,
    MatSnackBarModule,
    MatExpansionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
