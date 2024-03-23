import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  tokenValues: number[] = [10, 20, 50, 100, 200, 999];

  constructor() {}
}
