export interface Product {
    id: number;
    name: string;
    description: string;
  }
  
  export interface VendingItem {
    identifier: string;
    product: Product;
    quantity: number;
    price: number;
  }