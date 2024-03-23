import { Component, OnInit } from '@angular/core';
import { VendingItem } from '../models/global';
import { HttpClient } from '@angular/common/http';
import { MoneyService } from '../services/money.service';
import { env } from 'process';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.scss']
})
export class OrderListComponent implements OnInit {
  vendingItems: VendingItem[] = []; // Define an array to hold the orders

  constructor(private httpClient: HttpClient, private moneyService: MoneyService) {}

  ngOnInit(): void {
    this.fetchProducts();
    this.moneyService.shouldRefetchSessionCoins.subscribe(value => {
      if (value) this.fetchProducts();
    })
  }

  private fetchProducts(): void {
    const endpoint = environment.apiEndpoint;
    this.httpClient.get<VendingItem[]>(endpoint + '/vending/getAll').subscribe((result: VendingItem[]) => {
      this.vendingItems = result;
    });
  }
}
