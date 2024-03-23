import { Component, OnInit } from '@angular/core';
import { KeyboardDataService } from '../services/keyboard-data.service';
import { MoneyService } from '../services/money.service';

@Component({
  selector: 'display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.scss']
})
export class DisplayComponent implements OnInit {
  displayedSum = 0;
  displayData: string = '';

  constructor(private keyboardDataService: KeyboardDataService,
    private moneyService: MoneyService) { }

  ngOnInit(): void {
    this.keyboardDataService.getData().subscribe(data => {
      this.displayData = data;
    });
    this.moneyService.getOverallSumObservable().subscribe(sum => {
      this.displayedSum = this.moneyService.getSumFromItems(sum);
      this.moneyService.shouldRefetchSessionCoins.next(false);
    });
    this.moneyService.shouldRefetchSessionCoins.subscribe(shouldRefresh => {
      if (shouldRefresh) {
        this.moneyService.getOverallSumObservable().subscribe(sum => {
          this.displayedSum = this.moneyService.getSumFromItems(sum);
          this.moneyService.shouldRefetchSessionCoins.next(false);
        });
      }
    })
  }
  
}