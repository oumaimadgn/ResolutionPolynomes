import { Routes } from '@angular/router';
import { InputPageComponent } from './input-page/input-page.component';

export const routes: Routes = [
    { path: 'input-page', component: InputPageComponent },
    { path: '', redirectTo: 'input-page', pathMatch: 'full' },
];
