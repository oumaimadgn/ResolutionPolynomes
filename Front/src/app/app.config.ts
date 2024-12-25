import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withFetch } from '@angular/common/http'; // Import withFetch
import { provideClientHydration } from '@angular/platform-browser';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }), // Optimize zone-based change detection
    provideRouter(routes), // Provide routing configuration
    provideClientHydration(), // Support client hydration for SSR
    provideHttpClient(withFetch()), // Configure HttpClient to use fetch API
  ],
};
