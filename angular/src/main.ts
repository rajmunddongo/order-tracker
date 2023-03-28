import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { IndexComponent } from './app/components/indexpage/index.component';


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
