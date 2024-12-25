import { mergeApplicationConfig, ApplicationConfig } from '@angular/core';
import { provideServerRendering } from '@angular/platform-server';
import { appConfig } from './app.config'; // Import client-side app config

const serverConfig: ApplicationConfig = {
  providers: [
    provideServerRendering(), // Add server-side rendering capabilities
  ],
};

// Merge server-specific config with the client app config
export const config = mergeApplicationConfig(appConfig, serverConfig);
