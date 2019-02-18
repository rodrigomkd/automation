import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { CategoriesComponent } from './components/logs-categories/categories.component';

const routes: Routes = [
	{ path: 'home', component: AppComponent },
	{ path: 'categories', component: CategoriesComponent },
	{ path: '**', pathMatch: 'full', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
