import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { MoneyService } from '../services/money.service';

@Component({
  selector: 'app-token',
  templateUrl: './token.component.html',
  styleUrls: ['./token.component.scss']
})
export class TokenComponent {
  @Input() 
  tokenValue: number = 0;
  
 //  @Output() tokenClick: EventEmitter<number> = new EventEmitter<number>();

  constructor(private moneyService: MoneyService) {}

  getTokenStyle(value: number) {
    if (value === 100 || value === 200) {
      return { 'background-color': 'gold'}
    }
    if (value === 999 || value === 0) {
      return { 'background-color': 'red'}
    } else {
      return { 'background-color': 'silver' };
    }
  }

  getTokenValueStyle(value: number) {
    if (value === 999) {
      return {'visibility': 'hidden'};
    }
    return {'visibility': 'visible' };
  }

  onTokenSelected() {
    this.moneyService.addToOverallSum(this.tokenValue);
  }
}
