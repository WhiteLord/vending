# Vending Software

![Screenshot] (https://github.com/WhiteLord/vending/blob/28b000955242c8ff301d955b4a6b8d4e85078643/demo-image.png)

## What does it do?

Vending machines are super popular in Japan. You can get basically everything off of them.
Our target is to offer a brand new solution for ~5-6 hours.

The software contains a screen (top part), available coins to use (bottom part).
It also has a backend implementation and a tiny-winy database.

---

## How to run the application?
```bash
   git clone https://github.com/WhiteLord/vending
   docker compose up
```
All parts of the application are dockerized, so you should not worry about configuring different stuff. The application comes bundled with a sample dataset, so you don't have to wrorry about populating it.

<b>MAKE SURE TO ENABLE PORT 80 and 8080 AS THE APP MAPS THEM</b>

---

## What does it do?

As you have a fully fledged vending machime, you can:
- Insert coins
- Get your coins back
- Select items using the keyboard buttons
- Buy items using the "Enter" button, and receive change
- Check the curently available products

### Technical Decisions

#### Database

- I decided to put all the inventory items in a relational database, and provide a dump that will be loaded on application startup, so you don't have to worry about data.

#### Backend

- Vending machines usually work the following way: When you type in a specific number (eg A10), the vending machine requires you to have a specific ammount of money available as "session" coins.

- If the session coins available are less than what the item costs, the item would not be given to the user.

- Change, unlike the "coins" functional proramming challenge, is handled by iterating over a backwards sorted list of all available coins and deducting the needed ammount. This approach has a few negative sides - it exhausts the coins with the biggest denomination firstly. If that is a problem, and this poses a challenge of some sort, the change algorithm should be fine-tuned in another manner.

- After change is calculated, the remaining coins will be put in an object that lives in memory.

- There are several methods marked with the `@Transactional` annotation - outcomes are either committed or rolled back depending on function completion.

- A somewhat global exception handler

- A new annotation - `@ClearSessionCoins` - the aspect intercepts the execution of methods annotated with `@ClearSessionCoins` and executes the logic to clear session coins by invoking the `emptySessionCoins()` method of the `PaymentService` bean

#### Frontend

- The UI is composed of a few Angular components and services.
- I incorporated a data-driven "reactive" approach to displaying data - nothing should be stale.
- `Service <-- --> Component` communication
- `rem` instead of `px`
- No lazy-loaded modules, no modules at all
- No standalone components (Angualr 15)


#### Orchestration
- Docker, docker-compose
- Most Dockerfiles contain a multi stage build, as it's cool and should result in a lighter image.
- Everything is in a bridged network, so no problems using "localhost", we could also use the DNS for "service discovery".
- No swarm or k8s
- Images are built "on the fly" when starting the composition.

---

## API Reference

### Vending

#### Get all avaialable vending items
```http
GET /vending/getAll
```

#### Add a vending item
```http
POST /vending/vendingItem
```
```javascript
{
   "identifier": string
   "quantity": number
   "price": number
   "product": {
       "name": string,
       "description": string
   } 
}
```

#### Update a vending item
```http
PUT /vending/vendingItem/{id}
```
```javascript
   "id": number
{
   "identifier": string
   "quantity": number
   "price": number
   "product": {
       "name": string,
       "description": string
   } 
}
```
| Parameter | Type     | 
| --------- | -------- |
| `id`      | `number` |

#### Delete a vending item
```http
DELETE /vending/vendingItem/{id}
```
```javascript
{
   "identifier": string
   "quantity": number
   "price": number
   "product": {
       "name": string,
       "description": string
   } 
}
```
| Parameter | Type     | 
| --------- | -------- |
| `id`      | `number` |

#### Add a product
```http
POST /vending/addProduct
```
```javascript
{
    "name": string,
    "description": string
}
```

#### Purchase a vending item
```http
DELETE /vending/purchase
```
| Parameter | Type     | 
| --------- | -------- |
| `id`      | `number` |

---

### Payment

#### Add coins
```http
POST /payment/add
```
```javascript
{
   "coin": string
}
```

#### Return coins (without buying something)
```http
POST /payment/refund
```
```javascript
{} - empty object
```

#### Get currently inserted coins
```http
GET /payment/getSessionCoins
```
```javascript
{
   "coin": string
}
```

#### Get currently inserted coins
```http
GET /payment/getSessionCoins
```

## Tech Stack
**Server:** Nginx
<br>
**Frontend:** Angular
<br>
**API:** Spring, Postgres