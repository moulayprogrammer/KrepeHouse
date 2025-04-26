package com.moulay.krepehouse.Server;

import com.moulay.krepehouse.BddPackage.VendorOperation;
import com.moulay.krepehouse.Models.Vendor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class MySpringController {

    VendorOperation vendorOperation = new VendorOperation();

    @GetMapping("/greet")
    public ResponseEntity<String> greetUser(@RequestParam String name) {
        return ResponseEntity.ok("Hello, " + name + " from Java backend!");
    }

    @PostMapping("/users")
    public ResponseEntity<Vendor> createUser(@RequestBody Vendor user) {
        // Process and save user
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Vendor> getUser(@PathVariable Integer id) {
        // Retrieve user logic
        Vendor vendor = vendorOperation.get(id);
        return ResponseEntity.ok(vendor);
    }


}
