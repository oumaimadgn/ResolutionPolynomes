import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { InputPageComponent } from './app/input-page/input-page.component';

bootstrapApplication(InputPageComponent, {
  providers: [
    provideHttpClient(withInterceptorsFromDi()),
    { provide: FormsModule }
  ]
}).catch(err => console.error(err));
