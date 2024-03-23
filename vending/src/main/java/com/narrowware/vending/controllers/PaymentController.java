package com.narrowware.vending.controllers;


import com.narrowware.vending.annotations.ClearSessionCoins;
import com.narrowware.vending.dto.PaymentRequest;
import com.narrowware.vending.models.money.Coin;
import com.narrowware.vending.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    @CrossOrigin(origins = "*")
    public List<Coin> addMoney(@RequestBody PaymentRequest paymentRequest) {
        return this.paymentService.addToSessionBalance(paymentRequest);
    }

    /**
     * Whenever the user presses the "return" button, we return the session coins that's in the machine
     */
    @PostMapping("/refund")
    @CrossOrigin(origins = "*")
    @ClearSessionCoins
    public ResponseEntity<List<Coin>> returnMoney() {
        final List<Coin> refundableCoins = this.paymentService.getRefundCoins();
        if (refundableCoins != null && refundableCoins.size() > 0) {
            return new ResponseEntity<>(refundableCoins, HttpStatus.OK);
        } else {
            // The user has not inserted any coins
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/getSessionCoins")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<Coin>> getAvailableSessionMoney() {
        return new ResponseEntity<>(this.paymentService.getRefundCoins(), HttpStatus.OK);
    }

    @GetMapping("/getAllCoins")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Map<Coin, Integer>> getAllAvailableMoney() {
        return new ResponseEntity<>(this.paymentService.getAllCoins(), HttpStatus.OK);
    }
}
